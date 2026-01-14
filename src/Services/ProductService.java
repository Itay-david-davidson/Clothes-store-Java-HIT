package Services;

import inventory.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {


    private static boolean modifyItemsInCategory(int branchId, String value, int amount) {
        List<Product> data = ProductRepository.load();
        Product p  = findProduct(data, branchId, value);
        if (p == null) {
            System.out.println("Error: Could not find " + value + " in inventory!\nPlease create a category for this product.");
            return false;
        }
        int x = p.getQuantity();
        p.addQuantity(amount);
        if (p.getQuantity() < 0) {
            System.out.println("Error: Not enough items in " + value + "(" + x + " items) to remove " + amount + " item/s.");
            return false;
        }
        ProductRepository.save(data);
        System.out.println("Info: Successfully modified " + amount + " items in product " + p + " from " + x + " item/s.");
        return true;
    }

    public static boolean addProduct(String id, String name, String category, int branchId, double price, int quantity) {
        List<Product> data = ProductRepository.load();
        if (findProduct(branchId, id) != null) {
            System.out.println("Error: Product by " + id + " already exists in inventory!");
            return false;
        }
        Product p = new Product(id, name, category, branchId, price, quantity);
        data.add(p);
        ProductRepository.save(data);
        System.out.println("Info: Successfully added " + p + " to inventory.");
        return true;
    }

    public static boolean addProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot add \"null\" product to inventory!");
            return false;
        }
        List<Product> data = ProductRepository.load();
        data.add(product);
        ProductRepository.save(data);
        System.out.println("Info: Successfully added " + product + " to inventory.");
        return true;
    }

    public static Product findProduct(int branchId, String value) {
        List<Product> data = ProductRepository.load();
        return findProduct(data, branchId, value);
    }

    public static Product findProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot search for \"null\" value in inventory!");
            return null;
        }
        List<Product> data = ProductRepository.load();
        return findProduct(data, product.getBranchId(), product.getId());
    }

    private static Product findProduct(List<Product> data, int branchId, String value) {
        System.out.println("Info: Searching for " + value + " from " + data.size() + " categories.");
        for (Product p : data) {
            if (p.compare(value) && p.getBranchId() == branchId) {
                System.out.println("Info: Successfully found " + p + ".");
                return p;
            }
        }
        System.out.println("Info: Could not find " + value + " in inventory!");
        return null;
    }

    public static boolean removeProduct(int branchId, String value) {
        Product p = findProduct(branchId, value);
        if (p == null) {
            System.out.println("Error: Cannot remove \"null\" value from inventory!");
            return false;
        }
        List<Product> data = ProductRepository.load();
        data.remove(p);
        ProductRepository.save(data);
        System.out.println("Info: Successfully removed " + p + " from inventory.");
        return true;
    }

    public static boolean removeProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot remove \"null\" value from inventory!");
            return false;
        }
        List<Product> data = ProductRepository.load();
        Product p = findProduct(data, product.getBranchId(), product.getId());
        if (p == null) {
            System.out.println("Error: Product " + product + " does not exist in inventory!");
            return false;
        }
        data.remove(p);
        ProductRepository.save(data);
        System.out.println("Info: Successfully removed " + p + " from inventory.");
        return true;
    }

    public static List<Product> getInventory() {
        List<Product> data = ProductRepository.load();
        System.out.println("Info: Successfully loaded all items from inventory.");
        return data;
    }

    public static boolean reduceItemsFromCategory(int branchId, String category, int amount) { return modifyItemsInCategory(branchId, category, -amount); }

    public static boolean addItemsToCategory(int branchId, String category, int amount) { return modifyItemsInCategory(branchId, category, amount); }
}