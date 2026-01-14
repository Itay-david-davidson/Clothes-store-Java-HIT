package employees;

import java.io.Serializable;

/**
 * To be used as an interface between employee object and JSON representation
 */
public class EmployeeData implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id;
    public String name;
    public String phoneNumber;
    public String accountNumber;
    public String storeID;
    public String workerID;
    public String username;
    public String password;
    public String type;
}