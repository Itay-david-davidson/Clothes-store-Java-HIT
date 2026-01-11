package domain.users;

public class Seller extends Employee {

    private String department;

    public Seller(String id, String name, String phone, String department) {
        super(id, name, phone);
        this.department = department;
    }

    @Override
    public String getRole() {
        return "Seller";
    }

    @Override
    protected String getExtra() {
        return department;
    }


    protected void loadExtra(String extra) {
        this.department = extra;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
