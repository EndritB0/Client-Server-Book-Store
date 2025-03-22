package bookstore.dao;

import bookstore.exception.CustomerNotFoundException;
import bookstore.exception.InvalidInputException;
import bookstore.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class CustomerDAO {

    private static ConcurrentHashMap<String, Customer> customers = new ConcurrentHashMap<>();

    static {
//        Customer customer = new Customer(UUID.randomUUID().toString(), "Endrit", "w2044456@westminster.ac.uk");
//        customers.put(customer.getId(), customer);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    public Customer getCustomerById(String id) {
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
        return customer;
    }

    public Customer createCustomer(Customer customer) {
        if (customer.getName() == null || customer.getEmail() == null) {
            throw new InvalidInputException("Customer name and email must both be provided.");
        }

        customer.setId(UUID.randomUUID().toString());
        customers.put(customer.getId(), customer);

        CartDAO cartDAO = new CartDAO();
        cartDAO.createCartforCustomer(customer.getId());

        return customer;
    }

    public Customer updateCustomer(String id, Customer updatedCustomer) {
        if (updatedCustomer.getName() == null && updatedCustomer.getEmail() == null) {
            throw new InvalidInputException("At least one field (name or email) must be provided.");
        }

        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        if (updatedCustomer.getName() != null) {
            customer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            customer.setEmail(updatedCustomer.getEmail());
        }

        customers.put(customer.getId(), customer);
        return customer;
    }

    public void deleteCustomer(String id) {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        customers.remove(id);
    }

    public boolean validCustomer(String id) {
        return customers.containsKey(id);
    }
}
