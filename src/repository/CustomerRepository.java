package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import domain.customers.Customer;
import domain.customers.CustomerData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private static final String FILE_PATH = "data/customers.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Customer> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Info: Customer file does not exist! Creating new DB.");
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<List<CustomerData>>(){}.getType();
            List<CustomerData> list = gson.fromJson(reader, type);
            if (list == null) return new ArrayList<>();
            List<Customer> customers = new ArrayList<>(list.size());
            for (CustomerData d : list) {
                customers.add(Customer.fromData(d));
            }
            return customers;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save(List<Customer> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("Error: Invalid length of data to save!");
            return;
        }
        List<CustomerData> customers = new ArrayList<>(data.size());
        for (Customer d : data) {
            customers.add(d.toData());
        }
        new File("data").mkdirs(); // וודא שהתיקייה קיימת
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(customers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}