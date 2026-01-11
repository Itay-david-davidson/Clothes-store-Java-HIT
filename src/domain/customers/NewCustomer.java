package domain.customers;

public class NewCustomer extends Customer {
    public NewCustomer(String id, String name, String phone, double discount) {
        super(id, name, phone, discount);
    }
    @Override
    public String getCustomerType() { return "NEW"; }
}