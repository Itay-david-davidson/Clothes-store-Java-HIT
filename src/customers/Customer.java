package customers;
public abstract class Customer {
    protected String name;
    protected String id;
    protected String phoneNumber;

    public Customer (String name, String id, String phoneNumber)
    {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }
    
    public final String getName()
    {
        return this.name;
    }

    public final String getID()
    {
        return this.id;
    }

    public final String phoneNumber()
    {
        return this.phoneNumber;
    }

    public abstract String getType();

}

