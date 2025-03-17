package bookstore.dao;

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
    
    static{
        Customer customer = new Customer(UUID.randomUUID().toString(), "Endrit", "w2044456@westminster.ac.uk");
        customers.put(customer.getId(), customer);
    }
    
    public List<Customer> getAllCustomers(){
        return new ArrayList<>(customers.values());
    }
    
}
