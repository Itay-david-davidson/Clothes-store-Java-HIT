package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import domain.users.Employee;
import domain.users.EmployeeData;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static final String FILE_PATH = "data/employees.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Employee> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Info: DB file does not exist! Creating new DB.");
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<List<EmployeeData>>(){}.getType();
            List<EmployeeData> list = gson.fromJson(reader, type);
            if (list == null) return new ArrayList<>();
            List<Employee> employees = new ArrayList<>(list.size());
            for (EmployeeData d : list) {
                employees.add(Employee.fromData(d));
            }
            return employees;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save(List<Employee> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("Error: Invalid length of data to save!");
            return;
        }
        List<EmployeeData> employees = new ArrayList<>(data.size());
        for (Employee d : data) {
            employees.add(d.toData());
        }
        new File("data").mkdirs(); // וודא שהתיקייה קיימת
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(employees, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
