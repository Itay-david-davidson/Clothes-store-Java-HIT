package customers;

import java.io.Serializable;

/**
 * To be used as an interface between customer object and JSON representation
 */
public class CustomerData implements Serializable {
    public String id;
    public String name;
    public String phone;
    public String type;
}
