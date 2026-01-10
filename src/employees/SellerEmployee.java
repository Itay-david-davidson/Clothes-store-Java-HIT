package employees;

public class SellerEmployee extends Employee
{
    protected static final String TYPE = "SellerEmployee";

    public SellerEmployee(String name, String id, String phoneNumber, String accountNumber, String storeName, String workerID,String username, String password)
    {
        super(name,id,phoneNumber,accountNumber,storeName,workerID,username,password);
    }

    @Override
    public final String getType()
    {
        return  SellerEmployee.TYPE;
    }
    
}