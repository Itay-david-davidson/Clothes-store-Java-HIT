import Sell.Sell;
import customers.Customer;
import customers.NewCustomer;
import customers.VIPCustomer;
import inventory.Product;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Product cloth = new Product("Green", "T-shirt", "Clothes", 1, 38.5, 6);
        Product jeans = new Product("Blue", "Jeans", "Pents", 2, 52.3, 13);
        Product socks = new Product("White", "Socks", "Socks", 3, 10,3);

        Customer c1 = new NewCustomer("Nadav", "1", "05");
        Customer c2 = new VIPCustomer("David", "7", "03");

        Sell s = new Sell(c1);
        s.addProduct(cloth, 2);
        s.addProduct(jeans, 1);
        s.deleteProduct(jeans);

        s.showCart();
    }
}