package domain.users;

public class Cashier extends Employee {

    private String branchId;

    public Cashier(String id, String name, String phone, String branchId) {
        super(id, name, phone);
        this.branchId = branchId;
    }

    @Override
    public String getRole() {
        return "Cashier";
    }

    @Override
    protected String getExtra() {
        return branchId;
    }
    @Override
    public String toString() {
        return "Cashier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", branchId='" + branchId + '\'' +
                '}';
    }

}
