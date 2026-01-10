package employees;

public abstract  class Employee {
    protected String name;
    protected String id;
    protected String phoneNumber;
    protected String accountNumber;
    protected String storeID;
    protected String workerID;
    protected String username;
    protected String password;

    public Employee(String name,String id, String phoneNumber, String accountNumber, String storeID, String workerID, String username, String password)
    {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.storeID = storeID;
        this.workerID = workerID;
        this.username = username;
        if (validatePassword(password))
            this.password = password;
        else
            //TODO: throw error
            System.out.println("Password does not meet requirements");

    }

    public final String getName()
    {
        return this.name;
    }

    public final String getID() {
        return this.id;
    }

    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    public final String getAccountNumber() {
        return this.accountNumber;
    }

    public final String getStoreID() {
        return this.storeID;
    }

    public final String getWorkerID() {
        return this.workerID;
    }


    //TODO: get password policy from JSON and check if true
    protected boolean validatePassword(String password)
    {
        return true;
    }

    public abstract String getType();
}
