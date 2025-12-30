public class SellerEmployee extends Employee
{
    protected static final String TYPE = "SellerEmployee";

    public SellerEmployee(String name, String id, String phoneNumber, String accountNumber, String storeName, String workerID)
    {
        super(name,id,phoneNumber,accountNumber,storeName,workerID);
    }

    @Override
    public final String getType()
    {
        return  SellerEmployee.TYPE;
    }
    
}