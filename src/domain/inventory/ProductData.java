package domain.inventory;

import java.io.Serializable;

public class ProductData implements Serializable {
    public String id;
    public String name;
    public String category;
    public int branchId;
    public double price;
    public int quantity;
}