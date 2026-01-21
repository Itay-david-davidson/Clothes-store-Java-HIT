package Server;

import Chat.ChatActions;
import Services.EmployeeService;
import Services.CustomerService;
import Services.SimpleLogManager;
import Services.SimpleStatsManager;
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
    private static ChatActions chat;
    public static String Handle(JsonObject request) // return String but has inside objects
    {
        Employee employee;
        String action = request.get("action").getAsString();
        JsonObject data = request.getAsJsonObject("Employees"); //
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
                String branchId = data.get("StoreId").getAsString();
                //TODO: add chat intergration
                chat = Chat.startChat(userIdRequesting, branchId); // Connect the chat here.
                break;
            }
            case "addManagerEmployee":
            case "addRegisterEmployee":
            case "addSellerEmployee": {
                String name = data.get("name").getAsString();
                String id = data.get("id").getAsString();
                String phoneNumber = data.get("phoneNumber").getAsString();
                String accountNumber = data.get("accountNumber").getAsString();
                String storeId = data.get("StoreID").getAsString();
                String workerID = data.get("WorkerID").getAsString();
                String username = data.get("username").getAsString();
                String password = data.get("password").getAsString();

                if (action.equals("addManagerEmployee")) {
                    employee = new ManagerEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                }
                else if (action.equals("addRegisterEmployee")) {
                    employee = new RegisterEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                }
                else {
                    employee = new SellerEmployee(name, id, phoneNumber, accountNumber, storeId, workerID, username, password);
                }
                // אורי כוכבי בדיקה שמירה ותיעוד למנהל הלוגים
                boolean success = EmployeeService.addEmployee(employee); // שמירה ל employees.jason הקיים
                if (success) {
                    // קריאה למנהל הלוגים:
                    SimpleLogManager.writeToLog("System", "ADD_EMPLOYEE",
                            "New worker: " + employee.getName() + " (ID: " + employee.getID() + ")");
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

        }
        return response;
    }
}
