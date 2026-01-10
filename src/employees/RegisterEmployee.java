package employees;

public class RegisterEmployee extends Employee
{
    protected static final String TYPE = "RegisterEmployee";

    public RegisterEmployee(String name, String id, String phoneNumber, String accountNumber, String storeName, String workerID,String username, String password)
    {
        super(name,id,phoneNumber,accountNumber,storeName,workerID,username,password);
    }

    @Override
    public final String getType()
    {
        return  RegisterEmployee.TYPE;
    }
    
}