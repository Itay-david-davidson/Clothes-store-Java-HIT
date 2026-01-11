package service;

import domain.inventory.Product;
import domain.users.Employee;
import repository.EmployeeRepository;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> data = repository.load();
        System.out.println("Info: Successfully loaded all customers.");
        return data;
    }

    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Error: Cannot add \"null\" employee!");
            return false;
        }
        List<Employee> data = repository.load();
        data.add(employee);
        repository.save(data);
        System.out.println("Info: Successfully added " + employee + ".");
        return true;
    }

    public Employee findEmployee(String value) {
        List<Employee> data = repository.load();
        return findEmployee(data, value);
    }

    public Employee findEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Error: Cannot search for \"null\" value!");
            return null;
        }
        List<Employee> data = repository.load();
        return findEmployee(data, employee.getId());
    }

    private Employee findEmployee(List<Employee> data, String id) {
        System.out.println("Info: Searching for " + id + " from " + data.size() + " employees.");
        for (Employee e : data) {
            if (e.compare(id)) {
                System.out.println("Info: Successfully found " + e + ".");
                return e;
            }
        }
        System.out.println("Info: Could not find " + id + "!");
        return null;
    }

    public boolean removeEmployee(String id) {
        Employee e = findEmployee(id);
        if (e == null) {
            System.out.println("Error: Cannot find employee with id of " + id + "!");
            return false;
        }
        List<Employee> data = repository.load();
        data.remove(e);
        repository.save(data);
        System.out.println("Info: Successfully removed " + e + ".");
        return true;
    }

    public boolean removeEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Error: Cannot remove \"null\" value!");
            return false;
        }
        List<Employee> data = repository.load();
        Employee e = findEmployee(data, employee.getId());
        if (e == null) {
            System.out.println("Error: Employee " + employee + " does not exist!");
            return false;
        }
        data.remove(e);
        repository.save(data);
        System.out.println("Info: Successfully removed " + e + ".");
        return true;
    }

    public List<Employee> getEmployees() {
        List<Employee> data = repository.load();
        System.out.println("Info: Successfully loaded all employees from DB.");
        return data;
    }
}