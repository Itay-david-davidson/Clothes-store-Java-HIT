package Services;

import inventory.Product;
import employees.Employee;
import repository.EmployeeRepository;
import java.util.List;

public class EmployeeService {



    public static List<Employee> getAllEmployees() {
        List<Employee> data = EmployeeRepository.load();
        System.out.println("Info: Successfully loaded all customers.");
        return data;
    }

    public static boolean addEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Error: Cannot add \"null\" employee!");
            return false;
        }
        List<Employee> data = EmployeeRepository.load();
        data.add(employee);
        EmployeeRepository.save(data);
        System.out.println("Info: Successfully added " + employee + ".");
        return true;
    }

    public static Employee findEmployee(String value) {
        List<Employee> data = EmployeeRepository.load();
        return findEmployee(data, value);
    }

    public static Employee findEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Error: Cannot search for \"null\" value!");
            return null;
        }
        List<Employee> data = EmployeeRepository.load();
        return findEmployee(data, employee.getID());
    }

    private static Employee findEmployee(List<Employee> data, String id) {
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

    public static boolean removeEmployee(String id) {
        Employee e = findEmployee(id);
        if (e == null) {
            System.out.println("Error: Cannot find employee with id of " + id + "!");
            return false;
        }
        List<Employee> data = EmployeeRepository.load();
        data.remove(e);
        EmployeeRepository.save(data);
        System.out.println("Info: Successfully removed " + e + ".");
        return true;
    }

    public static boolean removeEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("Error: Cannot remove \"null\" value!");
            return false;
        }
        List<Employee> data = EmployeeRepository.load();
        Employee e = findEmployee(data, employee.getID());
        if (e == null) {
            System.out.println("Error: Employee " + employee + " does not exist!");
            return false;
        }
        data.remove(e);
        EmployeeRepository.save(data);
        System.out.println("Info: Successfully removed " + e + ".");
        return true;
    }

    public static List<Employee> getEmployees() {
        List<Employee> data = EmployeeRepository.load();
        System.out.println("Info: Successfully loaded all employees from DB.");
        return data;
    }

    public static Employee Login(String username, String password)
    {
        List<Employee> data = EmployeeRepository.load();
        for (Employee e : data)
        {
            if (e.CheckLogin(username,password))
                return e;
        }
        return null;

    }
}