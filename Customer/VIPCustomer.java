//TODO:
//Add Sales JSON
public class VIPCustomer extends Customer
{
    public static final String TYPE = "VIPCustomer";
    public VIPCustomer(String name, String ID, String phoneNumber)
    {
        super(name,ID,phoneNumber);
    }

    @Override
    public String getType()
    {
        return VIPCustomer.TYPE;
    }
}