package customers;
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

    // המרה לאובייקט נתונים פשוט עבור ה-JSON
    public CustomerData toData() {
        CustomerData data = new CustomerData();
        data.id = this.getID();
        data.name = this.getName();
        data.phone = this.getPhoneNumber();
        data.type = this.getType();
        return data;
    }

        // הפונקציה החכמה שלך: יוצרת אובייקט לוגי מתוך נתוני הקובץ
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

    // הדרכים השונות לזהות את הלקוח
    public boolean compare(String id) {
        return this.id.equals(id);
    }
}

