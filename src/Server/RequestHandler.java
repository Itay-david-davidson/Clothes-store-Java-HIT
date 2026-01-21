package Server;

import Chat.ChatActions;
import Services.EmployeeService;
import Services.CustomerService;
import Services.SimpleLogManager;
import Services.SimpleStatsManager;
import Services.ProductService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import customers.Customer;
import customers.NewCustomer;
import customers.ReturningCustomer;
import customers.VIPCustomer;
import employees.Employee;
import employees.ManagerEmployee;
import employees.RegisterEmployee;
import employees.SellerEmployee;


public class RequestHandler {
    private static final Gson gson = new Gson();
    private static Employee employee;
    private static Customer customer;
    private static ClientHandler client;
    public static String Handle(JsonObject request)
    {
        Employee employee;
        String action = request.get("action").getAsString();
        JsonObject data = request.getAsJsonObject("Employees"); 
        String response =  "";

        switch(action) {
            case "ViewStock": {
                String StoreID = data.get("StoreID").getAsString(); // which Json file?

                break;
            }
            case "login": {
                String roleType = data.get("type").getAsString();
                String username = data.get("username").getAsString();
                String password = data.get("password").getAsString();

                employee = EmployeeService.Login(username, password);
                break;
            }
            case "startChat": {
                String username = data.get("username").getAsString();
                
                client = Server.findClientById(username);
                break;
            }
            case "addManagerEmployee":
            case "addRegisterEmployee":
            case "addSellerEmployee": {
                Employee newEmployee;
                String name = data.get("name").getAsString();
                String id = data.get("id").getAsString();
                String phoneNumber = data.get("phoneNumber").getAsString();
                String accountNumber = data.get("accountNumber").getAsString();
                String storeId = data.get("StoreID").getAsString();
                String workerID = data.get("WorkerID").getAsString();
                String username = data.get("username").getAsString();
                String password = data.get("password").getAsString();

                if (action.equals("addManagerEmployee")) {
                     newEmployee = new ManagerEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                }
                else if (action.equals("addRegisterEmployee")) {
                     newEmployee = new RegisterEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                }
                else {
                     newEmployee = new SellerEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                }
                // אורי כוכבי בדיקה שמירה ותיעוד למנהל הלוגים
                boolean success = EmployeeService.addEmployee(newEmployee); // שמירה ל employees.jason הקיים
                if (success) {
                    // קריאה למנהל הלוגים:
                    SimpleLogManager.writeToLog("System", "ADD_EMPLOYEE",
                            "New worker: " + newEmployee.getName() + " (ID: " + newEmployee.getID() + ")");
                }
                break;
            }
            case "addNewCustomer":
            case "addReturningCustomer":
            case "addVIPCustomer": {

                String id = data.get("id").getAsString();
                String name = data.get("name").getAsString();
                String phoneNumber = data.get("phoneNumber").getAsString();

                if (action.equals("addNewCustomer")) {
                    customer = new NewCustomer(name, id, phoneNumber);
                } else if (action.equals("addReturningCustomer")) {
                    customer = new ReturningCustomer(name, id, phoneNumber);
                } else {
                    customer = new VIPCustomer(name, id, phoneNumber);
                }
                // שמירת לקוח חדש לcustomers.json  הקיים
                boolean success = CustomerService.addCustomer(customer);
                // אם נוצר לקוח חדש, מפעיל את מנהל הלוגים וכותב שורה (LOG)
                if (success) {
                    SimpleLogManager.writeToLog("System", "ADD_CUSTOMER",
                            "Customer: " + customer.getName() + " type: " + action);
                }
                break;

            }
            case "purchaseProduct": {
                // שולף שם מוצר מהגייסון
                String productName = data.get("productName").getAsString();
                String storeId = data.get("StoreID").getAsString();

                // 2. עדכון המלאי הפיזי (דרך ה-ProductService הקיים שלך)
                boolean reduced = ProductService.reduceItemsFromCategory(Integer.parseInt(storeId), productName, 1);

                if (reduced) {
                    // הוספת ערך ללוג סטטיסטיקה על מכירה
                    SimpleStatsManager.updateProductSale(productName);

                    // 4. רישום לוג של המכירה עם חותמת זמן
                    SimpleLogManager.writeToLog("System", "SALE", "Sold: " + productName + " at Branch: " + storeId);
                }
                break;
            }


        }
        return response;
    }
}
