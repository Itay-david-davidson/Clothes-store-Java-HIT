//TODO:
//Add Sales JSON
public class NewCustomer extends Customer
{
    public static final String TYPE = "NewCustomer";
    public NewCustomer(String name, String ID, String phoneNumber)
    {
        super(name,ID,phoneNumber);
    }

    @Override
    public String getType()
    {
        return NewCustomer.TYPE;
    }
}