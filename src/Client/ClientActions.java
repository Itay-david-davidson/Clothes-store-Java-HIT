package Client;

import Server.Server;
import Services.EmployeeService;
import employees.Employee;
import employees.EmployeeData;

public class ClientActions {

    private final ClientHandler context;
    private final Server server;

    public ClientActions(ClientHandler context, Server server ) {
        this.context = context;
        this.server = server;
    }

    private void addToChat(ClientHandler p) { if(!context.getCurrentChatPartners().contains(p)) context.getCurrentChatPartners().add(p); }

    // --- פונקציות עזר לצ'אט (ללא שינוי) ---
    private void startChatWith(String username) {
        ClientHandler partner = server.findConnectedClientByUsername(username);
        if (partner == null) {
            context.sendMessage("SYSTEM: User not found online.");
            return;
        }
        addToChat(partner);
        partner.actions.addToChat(context);
        context.getEmployee().setBusy(true);
        context.getEmployee().setCurrentChatId(username);
        partner.getEmployee().setBusy(true);
        partner.getEmployee().setCurrentChatId(context.getEmployee().getID());
        context.sendMessage("SYSTEM: Chat started.");
        partner.sendMessage("SYSTEM: Chat started with " + context.getEmployee().getName());
    }

    // שאר פונקציות הצ'אט (joinExistingChat, addToChat, broadcastMessage) נשארות אותו דבר...
    private void joinExistingChat(String username) {
        if (!context.getEmployee().getType().equals("ShiftManager")) {
            context.sendMessage("You are not a shift manager! Only shift managers can join existing chats!");
            return;
        }
        ClientHandler user = server.findConnectedClientByUsername(username);
        if (user != null && user.getEmployee().isBusy()) {
            ClientHandler partner = server.getChatPartner(username);
            if (partner != null) {
                addToChat(user);
                addToChat(partner);
                user.actions.addToChat(context);
                partner.actions.addToChat(context);
                context.sendMessage("SYSTEM: Joined chat.");
                context.broadcastMessage("Manager joined.");
            }
        }
    }

    public boolean parseAction(String actionId, String line) {
        return switch(actionId) {
            case "LOGIN":
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String user = parts[0];
                    String pass = parts[1];

                    // שימוש בסרוויס שלך לבדיקת המשתמש
                    Employee foundEmp = EmployeeService.Login(user, pass);

                    if (foundEmp != null) {
                        // מצב 1: נמצא עובד והסיסמה נכונה
                        context.setEmployee(foundEmp);
                        server.addConnectedClient(context); // מוסיפים לרשימת ה"אונליין" בשרת
                        context.sendMessage("SYSTEM: Login Successful. Welcome " + foundEmp.getName());
                        System.out.println(foundEmp.getName() + " logged in.");
                    } else {
                        // מצב 2: לא נמצא או סיסמה שגויה -> מציעים ליצור חשבון
                        context.sendMessage("SYSTEM: User not found or wrong password. Do you want to create an account?");
                    }
                }
                yield true;
            case "CREATE_EMPLOYEE":
                try {
                    String json = line.substring(16);
                    EmployeeData data = ClientHandler.gson.fromJson(json, EmployeeData.class);
                    Employee newEmp = Employee.fromData(data);

                    // שימוש בסרוויס שלך להוספת העובד
                    // הפונקציה שלך מחזירה true/false ומדפיסה לקונסול
                    boolean success = EmployeeService.addEmployee(newEmp);

                    if (success) {
                        context.sendMessage("SYSTEM: Account created successfully. Please Login.");
                    } else {
                        context.sendMessage("SYSTEM: Error - User already exists or invalid data.");
                    }
                } catch (Exception e) {
                    context.sendMessage("SYSTEM: Error creating account (Invalid JSON).");
                }
                yield true;
            case "START_CHAT":
                startChatWith(line);
                yield true;
            case "JOIN":
                joinExistingChat(line);
                yield true;
            default:
                yield false;
        };

    }
}
