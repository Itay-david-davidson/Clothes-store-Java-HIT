package employees;

public class ManagerEmployee extends Employee
{
    protected static final String TYPE = "ManagerEmployee";

    public ManagerEmployee(String name, String id, String phoneNumber, String accountNumber, String storeName, String workerID)
    {
        super(name,id,phoneNumber,accountNumber,storeName,workerID);
    }

    @Override
    public final String getType()
    {
        return  ManagerEmployee.TYPE;
    }
    
}