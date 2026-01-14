package employees;


/**
 * Highest level of employee. Can join chat and manage users
 */
public class RegisterEmployee extends Employee
{
    protected final String TYPE = "RegisterEmployee";

    public RegisterEmployee(String name, String id, String phoneNumber, String accountNumber, String storeID, String workerID,String username, String password)
    {
        super(name,id,phoneNumber,accountNumber,storeID,workerID,username,password);
    }

    
}