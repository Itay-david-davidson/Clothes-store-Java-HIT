package customers;
//TODO: Add Sales JSON

public class VIPCustomer extends Customer
{
    public final String TYPE = "VIPCustomer";
    public VIPCustomer(String name, String id, String phoneNumber)
    {
        super(name,id,phoneNumber);
    }

}