package domain.customers;

public class ReturningCustomer extends Customer {
    public ReturningCustomer(String id, String name, String phone, double discount) {
        super(id, name, phone, discount);
    }
    @Override
    public String getCustomerType() { return "RETURNING"; }
}