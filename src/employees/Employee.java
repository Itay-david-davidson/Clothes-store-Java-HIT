package employees;

public abstract  class Employee {
    protected String name;
    protected String id;
    protected String phoneNumber;
    protected String accountNumber;
    protected String storeID;
    protected String workerID;

    public final String getUsername() {
        return username;
    }

    public final String getPassword() {
        return password;
    }

    protected String username;
    protected String password;
    protected final String TYPE = "";

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

    public final String getType()
    {
        return this.TYPE;
    }

    public EmployeeData toData() {
        EmployeeData data = new EmployeeData();
        data.id = this.getID();
        data.name = this.getName();
        data.phoneNumber = this.getPhoneNumber();
        data.type = this.getType();
        data.accountNumber = this.getAccountNumber();
        data.storeID = this.getStoreID();
        data.workerID = this.getWorkerID();
        data.username = this.getUsername();
        data.password = this.getPassword();
        return data;
    }

    // הדרכים השונות לזהות את העובד
    public boolean compare(String id) {
        return this.id.equals(id);
    }

    public static Employee fromData(EmployeeData data) {
        return switch (data.type) {
            case "RegisterEmployee" -> new RegisterEmployee(data.name, data.id, data.phoneNumber, data.accountNumber,data.storeID, data.workerID, data.username,data.password);
            case "Seller" -> new SellerEmployee(data.name, data.id, data.phoneNumber, data.accountNumber,data.storeID, data.workerID, data.username,data.password);
            case "ShiftManager" -> new ManagerEmployee(data.name, data.id, data.phoneNumber, data.accountNumber,data.storeID, data.workerID, data.username,data.password);
            default -> throw new IllegalArgumentException("Unknown role: " + data.type);
        };
    }

    @Override
    public String toString() {
        return this.getType() + " [ID=" + this.getID() + ", Name=" + this.getName() + ", Phone=" + this.getPhoneNumber();
    }
}
