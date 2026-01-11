package domain.customers;

public class VIPCustomer extends Customer {
    public VIPCustomer(String id, String name, String phone, double discount) {
        super(id, name, phone, discount);
    }
    @Override
    public String getCustomerType() { return "VIP"; }
}