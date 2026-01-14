package employees;

/**
 * lowest level of employee. Can't join existing chats or manage employees.
 */
public class SellerEmployee extends Employee
{
    protected final String TYPE = "SellerEmployee";

    public SellerEmployee(String name, String id, String phoneNumber, String accountNumber, String storeID, String workerID,String username, String password)
    {
        super(name,id,phoneNumber,accountNumber,storeID,workerID,username,password);
    }


}