package employees;

/**
 * Basic employee archetype for subclasses.
 * Is abstract.
 * Receives all parameters except type, type is decided by which subclass it is
 */
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


    /**
     * @param name name of employee,
     * @param id id of employee
     * @param phoneNumber phone number of employee
     * @param accountNumber account number of employee
     * @param storeID the ID of the store / branch the employee belongs to
     * @param workerID worker ID of employee
     * @param username username of employee
     * @param password password of employee. must pass checks detailed in password policy
     */
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


    /**
     * @param password password of employee
     * @return will return true if password passed policy checks
     */
    //TODO: get password policy from JSON and check if true
    protected boolean validatePassword(String password)
    {
        return true;
    }

    public final String getType()
    {
        return this.TYPE;
    }


    /**
     * @return return EmployeeData, used in conversion to JSON
     */
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

    /**
     * @param id id of a different employee
     * @return comparison of both employees id
     */
    public boolean compare(String id) {
        return this.id.equals(id);
    }

    /**
     * @param data EmployeeData used as an interface between JSON
     * @return Subclass of employee depends on the type in data
     */
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

    /**
     * @param username username you want to check
     * @param password password you want to check
     * @return true if combination of username and password is the same as this employee's
     */
    public boolean CheckLogin(String username, String password)
    {
        return (this.getUsername().equals(username)  && this.getPassword().equals(password));
    }
}
