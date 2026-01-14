package inventory;

import java.io.Serializable;


/**
 /**
 * To be used as an interface between product object and JSON representation
 */
public class ProductData implements Serializable {
    public String id;
    public String name;
    public String category;
    public int branchId;
    public double price;
    public int quantity;
}