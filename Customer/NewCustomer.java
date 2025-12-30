//TODO:
//Add Sales JSON
public class NewCustomer extends Customer
{
    protected static final String TYPE = "NewCustomer";

    public NewCustomer(String name, String id, String phoneNumber)
    {
        super(name,id,phoneNumber);
    }

    @Override
    public final String getType()
    {
        return NewCustomer.TYPE;
    }
    
}