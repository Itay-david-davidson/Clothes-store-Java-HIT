package customers;

/**
 * Subclass of Customer, has ok sales
 */
public class ReturningCustomer extends Customer {
    public final String TYPE = "ReturningCustomer";

    public ReturningCustomer(String name, String id, String phoneNumber) {
        super(name, id, phoneNumber);
    }

}