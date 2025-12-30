package customers;
//TODO: Add Sales JSON
public class ReturningCustomer extends Customer
{
    public static final String TYPE = "ReturningCustomer";
    public ReturningCustomer(String name, String id, String phoneNumber)
    {
        super(name,id,phoneNumber);
    }

    @Override
    public final String getType()
    {
        return ReturningCustomer.TYPE;
    }
}