package service;

import domain.inventory.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    private boolean modifyItemsInCategory(int branchId, String value, int amount) {
        List<Product> data = repository.load();
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
        repository.save(data);
        System.out.println("Info: Successfully modified " + amount + " items in product " + p + " from " + x + " item/s.");
        return true;
    }

    public boolean addProduct(String id, String name, String category, int branchId, double price, int quantity) {
        List<Product> data = repository.load();
        if (findProduct(branchId, id) != null) {
            System.out.println("Error: Product by " + id + " already exists in inventory!");
            return false;
        }
        Product p = new Product(id, name, category, branchId, price, quantity);
        data.add(p);
        repository.save(data);
        System.out.println("Info: Successfully added " + p + " to inventory.");
        return true;
    }

    public boolean addProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot add \"null\" product to inventory!");
            return false;
        }
        List<Product> data = repository.load();
        data.add(product);
        repository.save(data);
        System.out.println("Info: Successfully added " + product + " to inventory.");
        return true;
    }

    public Product findProduct(int branchId, String value) {
        List<Product> data = repository.load();
        return findProduct(data, branchId, value);
    }

    public Product findProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot search for \"null\" value in inventory!");
            return null;
        }
        List<Product> data = repository.load();
        return findProduct(data, product.getBranchId(), product.getId());
    }

    private Product findProduct(List<Product> data, int branchId, String value) {
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

    public boolean removeProduct(int branchId, String value) {
        Product p = findProduct(branchId, value);
        if (p == null) {
            System.out.println("Error: Cannot remove \"null\" value from inventory!");
            return false;
        }
        List<Product> data = repository.load();
        data.remove(p);
        repository.save(data);
        System.out.println("Info: Successfully removed " + p + " from inventory.");
        return true;
    }

    public boolean removeProduct(Product product) {
        if (product == null) {
            System.out.println("Error: Cannot remove \"null\" value from inventory!");
            return false;
        }
        List<Product> data = repository.load();
        Product p = findProduct(data, product.getBranchId(), product.getId());
        if (p == null) {
            System.out.println("Error: Product " + product + " does not exist in inventory!");
            return false;
        }
        data.remove(p);
        repository.save(data);
        System.out.println("Info: Successfully removed " + p + " from inventory.");
        return true;
    }

    public List<Product> getInventory() {
        List<Product> data = repository.load();
        System.out.println("Info: Successfully loaded all items from inventory.");
        return data;
    }

    public boolean reduceItemsFromCategory(int branchId, String category, int amount) { return modifyItemsInCategory(branchId, category, -amount); }

    public boolean addItemsToCategory(int branchId, String category, int amount) { return modifyItemsInCategory(branchId, category, amount); }
}