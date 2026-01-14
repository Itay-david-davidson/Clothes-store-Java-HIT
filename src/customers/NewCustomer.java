package customers;


/**
 * NewCustomer subclass of Customer, has the worst sales
 */
public class NewCustomer extends Customer
{
    protected final String TYPE = "NewCustomer";

    public NewCustomer(String name, String id, String phoneNumber)
    {
        super(name,id,phoneNumber);
    }

    
}