package employees;

public abstract  class Employee {
    protected String name;
    protected String id;
    protected String phoneNumber;
    protected String accountNumber;
    protected String storeName;
    protected String workerID;

    public Employee(String name,String id, String phoneNumber, String accountNumber, String storeName, String workerID)
    {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.storeName = storeName;
        this.workerID = workerID;
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

    public final String getStoreName() {
        return this.storeName;
    }

    public final String getWorkerID() {
        return this.workerID;
    }

    public abstract String getType();
}
