package domain.customers;

import java.io.Serializable;

public class CustomerData implements Serializable {
    public String id;
    public String name;
    public String phone;
    public String type;
    public double discountValue;
}