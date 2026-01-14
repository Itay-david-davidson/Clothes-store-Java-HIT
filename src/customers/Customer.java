package customers;


/**
 * Basic customer archetype for subclasses.
 * Is abstract.
 * Receives name, id and phone number. type is decided by which subclass it is
 */
public abstract class Customer {
    protected String name;
    protected String id;
    protected String phoneNumber;
    protected final String TYPE = "";


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

    public final String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public final String getType()
    {
        return this.TYPE;
    }


    /**
     * @return CustomerData type, to use in conversion to JSON
     */
    public CustomerData toData() {
        CustomerData data = new CustomerData();
        data.id = this.getID();
        data.name = this.getName();
        data.phone = this.getPhoneNumber();
        data.type = this.getType();
        return data;
    }

    /**
     * @param data CustomerData to convert from JSON to customer
     * @return Subclass of customer depends on type field in data
     */
        public static Customer fromData(CustomerData data) {
            return switch (data.type) {
                case "VIP" -> new VIPCustomer(data.id, data.name, data.phone);
                case "RETURNING" -> new ReturningCustomer(data.id, data.name, data.phone);
                case "NEW" -> new NewCustomer(data.id, data.name, data.phone);
                default -> throw new IllegalArgumentException("Unknown customer type: " + data.type);
            };
        }

    @Override
    public String toString() {
        return getType() + " [ID=" + this.getID() + ", Name=" + this.getName();
    }


    /**
     * @param id id of another customer
     * @return comparison results of both customers id
     */
    public boolean compare(String id) {
        return this.id.equals(id);
    }
}

