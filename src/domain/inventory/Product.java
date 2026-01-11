package domain.inventory;

public class Product {

    private final String id;
    private final String name;
    private final String category;
    private final int branchId;
    private double price;
    private int quantity;

    public Product(String id, String name, String category, int branchId, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.branchId = branchId;
        this.price = price;
        this.quantity = quantity;
    }

    // לוגיקה לעדכון מלאי (שימושי במכירה/קנייה)
    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public boolean reduceQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            return true;
        }
        return false; // אין מספיק במלאי
    }

    // המרה ל-Data (לשמירה ב-JSON)
    public ProductData toData() {
        ProductData data = new ProductData();
        data.id = this.id;
        data.name = this.name;
        data.category = this.category;
        data.price = this.price;
        data.quantity = this.quantity;
        return data;
    }

    // יצירה מ-Data (טעינה מה-JSON)
    public static Product fromData(ProductData data) {
        return new Product(data.id, data.name, data.category, data.branchId, data.price, data.quantity);
    }

    // הדרכים השונות לזהות את המוצר
    public boolean compare(String value) {
        return getCategory().equals(value) || getName().equals(value) || getId().equals(value);
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getBranchId() { return branchId; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return String.format("Product [ID: %s, Name: %s, Category: %s, BranchId: %d, Price: %.2f, Stock: %d]",
                id, name, category, branchId, price, quantity);
    }
}