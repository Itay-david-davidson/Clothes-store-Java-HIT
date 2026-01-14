package Services;

import customers.Customer;
import repository.CustomerRepository;
import java.util.List;

public class CustomerService {




    public static List<Customer> getAllCustomers() {
        List<Customer> data = CustomerRepository.load();
        System.out.println("Info: Successfully loaded all customers.");
        return data;
    }

    public static boolean addCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Cannot add \"null\" customer!");
            return false;
        }
        List<Customer> data = CustomerRepository.load();
        data.add(customer);
        CustomerRepository.save(data);
        System.out.println("Info: Successfully added " + customer + ".");
        return true;
    }

    public static Customer findCustomer(String value) {
        List<Customer> data = CustomerRepository.load();
        return findCustomer(data, value);
    }

    public static Customer findCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Cannot search for \"null\" value!");
            return null;
        }
        List<Customer> data = CustomerRepository.load();
        return findCustomer(data, customer.getID());
    }

    private static Customer findCustomer(List<Customer> data, String id) {
        System.out.println("Info: Searching for " + id + " from " + data.size() + " customers.");
        for (Customer c : data) {
            if (c.compare(id)) {
                System.out.println("Info: Successfully found " + c + ".");
                return c;
            }
        }
        System.out.println("Info: Could not find " + id + "!");
        return null;
    }

    public static boolean removeCustomer(String id) {
        Customer c = findCustomer(id);
        if (c == null) {
            System.out.println("Error: Cannot find customer with id of " + id + "!");
            return false;
        }
        List<Customer> data = CustomerRepository.load();
        data.remove(c);
        CustomerRepository.save(data);
        System.out.println("Info: Successfully removed " + c + ".");
        return true;
    }

    public static boolean removeCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Cannot remove \"null\" value!");
            return false;
        }
        List<Customer> data = CustomerRepository.load();
        Customer c = findCustomer(data, customer.getID());
        if (c == null) {
            System.out.println("Error: Customer " + customer + " does not exist!");
            return false;
        }
        data.remove(c);
        CustomerRepository.save(data);
        System.out.println("Info: Successfully removed " + c + ".");
        return true;
    }

    public static List<Customer> getCustomers() {
        List<Customer> data = CustomerRepository.load();
        System.out.println("Info: Successfully loaded all customers from DB.");
        return data;
    }
}