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

    // Concurrent HashMap that holds all customers in memory
    // Key: Customer ID, Value: Customer Object
    private static ConcurrentHashMap<String, Customer> customers = new ConcurrentHashMap<>();

    static {
//        Customer customer = new Customer(UUID.randomUUID().toString(), "Endrit", "w2044456@westminster.ac.uk");
//        customers.put(customer.getId(), customer);
    }

    // Returns all Customers in an ArrayList
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    // Finds Customer by ID
    // Returns Customer Object
    public Customer getCustomerById(String id) {
        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        return customer;
    }

    // Creates a new Customer and stores it in the HashMap
    // Returns new Customer Object
    public Customer createCustomer(Customer customer) {
        // Validates all required fields are provided
        // Throws InvalidInputException if any field is missing
        if (customer.getName() == null || customer.getEmail() == null
                || customer.getPassword() == null) {
            throw new InvalidInputException("Customer name, email and password must all be provided.");
        }

        // Generate a unigue ID and save Customer
        customer.setId(UUID.randomUUID().toString());
        customers.put(customer.getId(), customer);

        // Create an empty Cart for Customer
        CartDAO cartDAO = new CartDAO();
        cartDAO.createCartforCustomer(customer.getId());

        return customer;
    }

    // Updates an existing Customer's information
    // Returns updated Customer Object
    public Customer updateCustomer(String id, Customer updatedCustomer) {
        // Validates at least one field is provided
        // Throws InvalidInputException if all fields are missing
        if (updatedCustomer.getName() == null && updatedCustomer.getEmail() == null
                && updatedCustomer.getPassword() == null) {
            throw new InvalidInputException("At least one field (name, email or password) must be provided.");
        }

        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        Customer customer = customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        // Updated each field if provided
        if (updatedCustomer.getName() != null) {
            customer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            customer.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPassword() != null) {
            customer.setPassword(updatedCustomer.getPassword());
        }

        // Saves updated Customer Object
        customers.put(customer.getId(), customer);

        return customer;
    }

    // Deletes Customer from the Map
    public void deleteCustomer(String id) {
        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        // Removes Customer from the Map
        customers.remove(id);
    }

    // Checks if a Customer ID exists in the Map, used for other DAOs
    // Returns boolean value
    public boolean validCustomer(String id) {
        return customers.containsKey(id);
    }
}
