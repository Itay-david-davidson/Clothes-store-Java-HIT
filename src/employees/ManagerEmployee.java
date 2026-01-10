package employees;

public class ManagerEmployee extends Employee
{
    protected static final String TYPE = "ManagerEmployee";

    public ManagerEmployee(String name, String id, String phoneNumber, String accountNumber, String storeName, String workerID,String username, String password)
    {
        super(name,id,phoneNumber,accountNumber,storeName,workerID,username,password);
    }

    @Override
    public final String getType()
    {
        return  ManagerEmployee.TYPE;
    }
    
}