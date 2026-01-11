package service;

import domain.customers.Customer;
import repository.CustomerRepository;
import java.util.List;

public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }


    public List<Customer> getAllCustomers() {
        List<Customer> data = repository.load();
        System.out.println("Info: Successfully loaded all customers.");
        return data;
    }

    public boolean addCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Cannot add \"null\" customer!");
            return false;
        }
        List<Customer> data = repository.load();
        data.add(customer);
        repository.save(data);
        System.out.println("Info: Successfully added " + customer + ".");
        return true;
    }

    public Customer findCustomer(String value) {
        List<Customer> data = repository.load();
        return findCustomer(data, value);
    }

    public Customer findCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Cannot search for \"null\" value!");
            return null;
        }
        List<Customer> data = repository.load();
        return findCustomer(data, customer.getId());
    }

    private Customer findCustomer(List<Customer> data, String id) {
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

    public boolean removeCustomer(String id) {
        Customer c = findCustomer(id);
        if (c == null) {
            System.out.println("Error: Cannot find customer with id of " + id + "!");
            return false;
        }
        List<Customer> data = repository.load();
        data.remove(c);
        repository.save(data);
        System.out.println("Info: Successfully removed " + c + ".");
        return true;
    }

    public boolean removeCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Error: Cannot remove \"null\" value!");
            return false;
        }
        List<Customer> data = repository.load();
        Customer c = findCustomer(data, customer.getId());
        if (c == null) {
            System.out.println("Error: Customer " + customer + " does not exist!");
            return false;
        }
        data.remove(c);
        repository.save(data);
        System.out.println("Info: Successfully removed " + c + ".");
        return true;
    }

    public List<Customer> getCustomers() {
        List<Customer> data = repository.load();
        System.out.println("Info: Successfully loaded all customers from DB.");
        return data;
    }
}