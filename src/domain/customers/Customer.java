package domain.customers;

// מחלקת הבסיס - כל סוגי הלקוחות ירשו ממנה
public abstract class Customer {

    protected String id;
    protected String name;
    protected String phone;
    protected double discountRate;

    protected Customer(String id, String name, String phone, double discountRate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.discountRate = discountRate;
    }

    // מתודות מופשטות שכל בן חייב לממש
    public abstract String getCustomerType();

    // לוגיקה משותפת - חישוב מחיר אחרי הנחה
    public double applyDiscount(double price) {
        return price * (1 - discountRate);
    }

    // המרה לאובייקט נתונים פשוט עבור ה-JSON
    public CustomerData toData() {
        CustomerData data = new CustomerData();
        data.id = id;
        data.name = name;
        data.phone = phone;
        data.type = getCustomerType();
        data.discountValue = discountRate;
        return data;
    }

    // הפונקציה החכמה שלך: יוצרת אובייקט לוגי מתוך נתוני הקובץ
    public static Customer fromData(CustomerData data) {
        return switch (data.type) {
            case "VIP" -> new VIPCustomer(data.id, data.name, data.phone, data.discountValue);
            case "RETURNING" -> new ReturningCustomer(data.id, data.name, data.phone, data.discountValue);
            case "NEW" -> new NewCustomer(data.id, data.name, data.phone, data.discountValue);
            default -> throw new IllegalArgumentException("Unknown customer type: " + data.type);
        };
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    // הדרכים השונות לזהות את הלקוח
    public boolean compare(String id) {
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return getCustomerType() + " [ID=" + id + ", Name=" + name + ", Discount=" + (discountRate * 100) + "%]";
    }
}