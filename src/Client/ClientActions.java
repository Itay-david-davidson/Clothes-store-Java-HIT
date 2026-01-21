package Client;

import Server.Server;
import Services.EmployeeService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import customers.*; // וודא שיש לך את המחלקות האלו
import employees.*; // וודא שיש לך את המחלקות האלו
import java.util.ArrayList;
import java.util.List;

public class ClientActions {

    public enum ClientState {
        NO_AUTH, IDLE, WAITING_FOR_BRANCH, IN_CHAT
    }

    private ClientState state = ClientState.NO_AUTH;
    private final ClientHandler context;
    private final Server server;
    private static final Gson gson = new Gson();

    public ClientActions(ClientHandler context, Server server ) {
        this.context = context;
        this.server = server;
    }

    public boolean isInChat() { return state == ClientState.IN_CHAT; }

    // --- תפריט ראשי ---
    private void showMainMenu() {
        this.state = ClientState.IDLE;
        context.sendMessage("=========================================");
        context.sendMessage("           MAIN SYSTEM MENU              ");
        context.sendMessage("=========================================");
        context.sendMessage("1. Start Chat (Talk to other branches)");
        context.sendMessage("2. Add New Employee");
        context.sendMessage("3. Add New Customer");
        context.sendMessage("4. View Stock"); // החזרנו את המלאי!
        context.sendMessage("5. Logout");
        context.sendMessage("-----------------------------------------");
        context.sendMessage("Select an option (1-5):");
    }

    // =================================================================
    // החלק של הצ'אט והסניפים (כבר עובד מצוין)
    // =================================================================

    private void askForBranch() {
        this.state = ClientState.WAITING_FOR_BRANCH;
        context.sendMessage("SYSTEM: Select a branch:");
        context.sendMessage("1 - Tel Aviv");
        context.sendMessage("2 - Petah Tikva");
        context.sendMessage("3 - Eilat");
        context.sendMessage("Type 'BRANCH:<number>' to select.");
    }

    private void showEmployeesInBranch(String branchId) {
        String branchName = switch(branchId) {
            case "1" -> "Tel Aviv";
            case "2" -> "Petah Tikva";
            case "3" -> "Eilat";
            default -> "Unknown Branch";
        };

        context.sendMessage("SYSTEM: Searching in " + branchName + "...");
        List<String> available = new ArrayList<>();

        for (ClientHandler client : server.getConnectedClients()) {
            Employee emp = client.getEmployee();
            if (emp != null) {
                boolean sameBranch = emp.getStoreID().equals(branchId);
                boolean notMe = !emp.getUsername().equals(context.getEmployee().getUsername());
                boolean notBusy = !emp.isBusy();

                if (sameBranch && notMe && notBusy) {
                    available.add(emp.getName() + " (User: " + emp.getUsername() + ")");
                }
            }
        }

        if (available.isEmpty()) {
            context.sendMessage("SYSTEM: No employees found.");
            showMainMenu();
        } else {
            for (String s : available) context.sendMessage(s);
            context.sendMessage("SYSTEM: Type 'START_CHAT:<username>' to connect.");
            this.state = ClientState.IDLE;
        }
    }

    private void startChatWith(String username) {
        ClientHandler partner = server.findConnectedClientByUsername(username);
        if (partner == null) {
            context.sendMessage("SYSTEM: User not found online.");
            showMainMenu();
            return;
        }
        if (partner.getEmployee().isBusy()) {
            context.sendMessage("SYSTEM: User is busy.");
            showMainMenu();
            return;
        }
        if (partner == context) {
            context.sendMessage("SYSTEM: Cannot chat with yourself.");
            showMainMenu();
            return;
        }

        if(!context.getCurrentChatPartners().contains(partner))
            context.getCurrentChatPartners().add(partner);

        partner.actions.addToChat(context);

        context.getEmployee().setBusy(true);
        context.getEmployee().setCurrentChatId(username);
        partner.getEmployee().setBusy(true);
        partner.getEmployee().setCurrentChatId(context.getEmployee().getUsername());

        this.state = ClientState.IN_CHAT;
        partner.actions.forceStateChat();

        context.sendMessage("SYSTEM: Chat started with " + partner.getEmployee().getName());
        context.sendMessage("SYSTEM: Type 'EXIT' to end the chat.");
        partner.sendMessage("SYSTEM: Chat started with " + context.getEmployee().getName());
    }

    public void addToChat(ClientHandler other) {
        if(!context.getCurrentChatPartners().contains(other)) {
            context.getCurrentChatPartners().add(other);
        }
    }

    public void forceStateChat() { this.state = ClientState.IN_CHAT; }

    private void exitChat() {
        context.getCurrentChatPartners().clear();
        context.getEmployee().setBusy(false);
        context.getEmployee().setCurrentChatId(null);
        context.sendMessage("SYSTEM: You left the chat.");
        showMainMenu();
    }


    // =================================================================
    // החלק ששחזרנו מתוך RequestHandler (עובדים, לקוחות, מלאי)
    // =================================================================

    // שחזור הלוגיקה של הוספת עובד לפי סוגים
    private void addNewEmployee(String jsonLine) {
        try {
            JsonObject data = gson.fromJson(jsonLine, JsonObject.class);

            // בדיקה איזה סוג עובד זה (לפי השדה type ב-JSON או לפי הנתונים)
            // נניח שהמשתמש שולח JSON עם שדה "type" כמו: "type": "Manager"
            String type = data.has("type") ? data.get("type").getAsString() : "Seller";

            Employee employee = null;

            // שליפת השדות כמו שעשית במקור
            String name = data.get("name").getAsString();
            String id = data.get("id").getAsString();
            String phoneNumber = data.get("phoneNumber").getAsString();
            String accountNumber = data.get("accountNumber").getAsString();
            String storeId = data.get("storeID").getAsString(); // שים לב לאותיות גדולות/קטנות ב-JSON שלך
            String workerID = data.get("workerID").getAsString();
            String username = data.get("username").getAsString();
            String password = data.get("password").getAsString();

            // יצירת האובייקט המתאים (כמו ב-RequestHandler המקורי)
            switch (type) {
                case "Manager":
                case "ShiftManager": // תלוי איך קראת לזה ב-JSON
                    employee = new ManagerEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                    break;
                case "Register":
                case "RegisterEmployee":
                    employee = new RegisterEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                    break;
                case "Seller":
                case "SellerEmployee":
                default:
                    employee = new SellerEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                    break;
            }

            // שמירה באמצעות הסרוויס
            boolean success = EmployeeService.addEmployee(employee);

            if (success) {
                context.sendMessage("SYSTEM: Employee (" + type + ") added successfully!");
            } else {
                context.sendMessage("SYSTEM: Failed. Employee ID/User already exists.");
            }

        } catch (JsonSyntaxException e) {
            context.sendMessage("SYSTEM: Invalid JSON format.");
        } catch (Exception e) {
            context.sendMessage("SYSTEM: Error adding employee: " + e.getMessage());
            e.printStackTrace();
        }
        showMainMenu();
    }

    // שחזור הלוגיקה של הוספת לקוח
    private void addNewCustomer(String jsonLine) {
        try {
            JsonObject data = gson.fromJson(jsonLine, JsonObject.class);

            // בודקים איזה סוג לקוח
            String type = data.has("customerType") ? data.get("customerType").getAsString() : "New";

            Customer customer = null;
            String id = data.get("id").getAsString();
            String name = data.get("name").getAsString();
            String phoneNumber = data.get("phoneNumber").getAsString();

            // הלוגיקה המקורית שלך
            if (type.equalsIgnoreCase("New")) {
                customer = new NewCustomer(name, id, phoneNumber);
            } else if (type.equalsIgnoreCase("Returning")) {
                customer = new ReturningCustomer(name, id, phoneNumber);
            } else if (type.equalsIgnoreCase("VIP")) {
                customer = new VIPCustomer(name, id, phoneNumber);
            } else {
                customer = new NewCustomer(name, id, phoneNumber); // ברירת מחדל
            }

            // כאן חסרה לי פונקציה לשמירת לקוח בסרוויס שלך, אז שמתי רק הודעה
            // CustomerService.addCustomer(customer); <--- תוסיף את זה אם יש לך
            context.sendMessage("SYSTEM: Customer (" + type + ") created successfully (Simulation).");

        } catch (Exception e) {
            context.sendMessage("SYSTEM: Error adding customer: " + e.getMessage());
        }
        showMainMenu();
    }

    // שחזור הלוגיקה של צפייה במלאי
    private void handleViewStock(String jsonLine) {
        try {
            // הלקוח שולח רק מספר סניף או JSON עם StoreID
            String storeID = jsonLine.trim();

            // בדיקה אם זה JSON או סתם מספר
            if (jsonLine.startsWith("{")) {
                JsonObject data = gson.fromJson(jsonLine, JsonObject.class);
                storeID = data.get("StoreID").getAsString();
            }

            // כאן אתה צריך לקרוא למחלקת המלאי שלך
            // String stockReport = InventoryService.getStockReport(storeID);
            // context.sendMessage(stockReport);

            context.sendMessage("SYSTEM: Displaying stock for Store ID: " + storeID);
            context.sendMessage("SYSTEM: [Item 1: Shirt, Qty: 50] (Mock Data)"); // כרגע זה דמה כי אין לי את InventoryService

        } catch (Exception e) {
            context.sendMessage("SYSTEM: Error viewing stock.");
        }
        showMainMenu();
    }

    // =================================================================
    // המוח המרכזי (Parse Action)
    // =================================================================
    public boolean parseAction(String actionId, String dataLine) {
        try {
            // יציאה מצ'אט תמיד אפשרית
            if (actionId.equalsIgnoreCase("EXIT") && state == ClientState.IN_CHAT) {
                exitChat();
                return true;
            }

            switch (state) {
                case NO_AUTH:
                    if (actionId.equals("LOGIN")) {
                        String[] parts = dataLine.split(":");
                        if (parts.length == 2) {
                            Employee found = EmployeeService.Login(parts[0], parts[1]);
                            if (found != null) {
                                context.setEmployee(found);
                                server.addConnectedClient(context);
                                context.sendMessage("SYSTEM: Login Successful. Welcome " + found.getName());
                                showMainMenu(); // הצגת התפריט
                            } else {
                                context.sendMessage("SYSTEM: Login Failed.");
                            }
                        }
                        return true;
                    }
                    return false;

                case IDLE:
                    // בחירות מהתפריט
                    if (actionId.equals("1")) { // צ'אט
                        askForBranch();
                        return true;
                    }
                    if (actionId.equals("2")) { // עובד
                        context.sendMessage("SYSTEM: Paste Employee JSON (fields: name, id, phoneNumber, accountNumber, storeID, workerID, username, password, type):");
                        context.sendMessage("Format: ADD_EMPLOYEE:{...json...}");
                        return true;
                    }
                    if (actionId.equals("3")) { // לקוח
                        context.sendMessage("SYSTEM: Paste Customer JSON (fields: name, id, phoneNumber, customerType):");
                        context.sendMessage("Format: ADD_CUSTOMER:{...json...}");
                        return true;
                    }
                    if (actionId.equals("4")) { // מלאי
                        context.sendMessage("SYSTEM: Enter Store ID or JSON:");
                        context.sendMessage("Format: VIEW_STOCK:1");
                        return true;
                    }
                    if (actionId.equals("5")) { // יציאה
                        context.sendMessage("SYSTEM: Goodbye!");
                        return true;
                    }

                    // ביצוע הפקודות בפועל (אחרי שהלקוח הקליד את הפורמט)
                    if (actionId.equals("ADD_EMPLOYEE")) {
                        addNewEmployee(dataLine);
                        return true;
                    }
                    if (actionId.equals("ADD_CUSTOMER")) {
                        addNewCustomer(dataLine);
                        return true;
                    }
                    if (actionId.equals("VIEW_STOCK")) {
                        handleViewStock(dataLine);
                        return true;
                    }
                    if (actionId.equals("START_CHAT")) {
                        startChatWith(dataLine);
                        return true;
                    }
                    if (actionId.equals("BRANCH")) {
                        showEmployeesInBranch(dataLine);
                        return true;
                    }

                    context.sendMessage("SYSTEM: Invalid option. Please select 1-5.");
                    showMainMenu();
                    return true;

                case WAITING_FOR_BRANCH:
                    if (actionId.equals("BRANCH")) {
                        showEmployeesInBranch(dataLine);
                        return true;
                    }
                    context.sendMessage("SYSTEM: Please select a branch first (BRANCH:1/2/3).");
                    return true;

                case IN_CHAT:
                    return false;
            }
            return false;
        } catch (Exception e) {
            context.sendMessage("SYSTEM: Error processing command.");
            showMainMenu();
            return true;
        }
    }
}