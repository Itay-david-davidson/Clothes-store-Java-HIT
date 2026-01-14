package customers;

/**
 * Subclass of customer, has the best sales
 */
public class VIPCustomer extends Customer
{
    public final String TYPE = "VIPCustomer";
    public VIPCustomer(String name, String id, String phoneNumber)
    {
        super(name,id,phoneNumber);
    }

}