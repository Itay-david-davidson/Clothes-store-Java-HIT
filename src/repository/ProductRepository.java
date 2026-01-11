package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import domain.inventory.Product;
import domain.inventory.ProductData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static final String FILE_PATH = "data/product.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Product> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Info: Product file does not exist! Creating new DB.");
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<List<ProductData>>(){}.getType();
            List<ProductData> list = gson.fromJson(reader, type);
            if (list == null) return new ArrayList<>();
            List<Product> products = new ArrayList<>(list.size());
            for (ProductData d : list) {
                products.add(Product.fromData(d));
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save(List<Product> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("Error: Invalid length of data to save!");
            return;
        }
        List<ProductData> products = new ArrayList<>(data.size());
        for (Product d : data) {
            products.add(d.toData());
        }
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(products, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
