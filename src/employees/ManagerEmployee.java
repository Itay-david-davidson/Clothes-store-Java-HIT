package employees;


/**
 * medium level of employee. Can join chats
 */
public class ManagerEmployee extends Employee
{
    protected final String TYPE = "ManagerEmployee";

    public ManagerEmployee(String name, String id, String phoneNumber, String accountNumber, String storeID, String workerID,String username, String password)
    {
        super(name,id,phoneNumber,accountNumber,storeID,workerID,username,password);
    }

    
}