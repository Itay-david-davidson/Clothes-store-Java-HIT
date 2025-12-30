//TODO:
//Add Sales JSON
public class ReturningCustomer extends Customer
{
    public static final String TYPE = "ReturningCustomer";
    public ReturningCustomer(String name, String ID, String phoneNumber)
    {
        super(name,ID,phoneNumber);
    }

    @Override
    public String getType()
    {
        return ReturningCustomer.TYPE;
    }
}