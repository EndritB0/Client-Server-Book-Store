package bookstore.dao;

import bookstore.exception.CustomerNotFoundException;
import bookstore.exception.InvalidInputException;
import bookstore.exception.OrderNotFoundException;
import bookstore.model.Book;
import bookstore.model.Cart;
import bookstore.model.Item;
import bookstore.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class OrderDAO {

    // Concurrent HashMap that holds all customer orders in memory
    // Key: Customer ID, Value: List of Order Objects
    private static ConcurrentHashMap<String, List<Order>> orders = new ConcurrentHashMap<>();

    static {
//        Order order = new Order(UUID.randomUUID().toString(), new Customer(), new ArrayList<Book>(), 10);
//        orders.put(order.getId(), order);
    }

    // Gets all orders for a specific customer
    // Returns List of Order Objects
    public List<Order> getAllOrdersByCustomer(String id) {
        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        return orders.get(id);
    }

    // Finds a specific order for a customer
    // Returns Order Object
    public Order getOrderByCustomerId(String customerId, String orderId) {
        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.validCustomer(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        // Validate if Customer's Order isn't null 
        // Throws OrderNotFoundException if orders doesn't exist
        List<Order> customerOrders = orders.get(customerId);
        if (customerOrders == null) {
            throw new OrderNotFoundException("No orders found for customer with ID: " + customerId);
        }

        // Loops through the customers orders to find the specific Order
        for (Order order : customerOrders) {
            // Returns the order if found
            if (order.getId().equals(orderId)) {
                return order;
            }
        }

        // Throws OrderNotFoundException if the order isnt found
        throw new OrderNotFoundException("Order not found with ID: " + orderId + " for customer with ID: " + customerId);
    }

    // Creates a new order from customer's cart
    // Returns created Order Object
    public Order createOrderFromCustomer(String id) {
        CustomerDAO customerDAO = new CustomerDAO();
        CartDAO cartDAO = new CartDAO();
        BookDAO bookDAO = new BookDAO();

        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        // Validate if cart is not empty or not null
        // Throws InvalidInputException if cart is empty or null
        Cart cart = cartDAO.getCartByCustomerId(id);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new InvalidInputException("Cannot create an order: Cart is empty.");
        }

        // Get the Items in the Customers Cart
        // Loops through the items in the cart
        ArrayList<Item> items = new ArrayList<>(cart.getItems().values());
        for (Item item : items) {
            // Update the Books Stock amount by subtracting the quantity of in the cart
            Book updatedBook = item.getBook();
            updatedBook.setStock(updatedBook.getStock() - item.getQuantity());
            bookDAO.updateBook(updatedBook.getId(), updatedBook);
        }

        // Create a new Order and input all the values
        Order order = new Order(
                UUID.randomUUID().toString(),
                new ArrayList<>(cart.getItems().values()),
                calculateTotalPrice(cart));

        // Validate if orders is not empty or not null
        // If customerOrders is null, Initializes orders ArrayList
        List<Order> customerOrders = orders.get(id);
        if (customerOrders == null) {
            // Add the Array list to Orders HashMap
            customerOrders = new ArrayList<>();
            orders.put(id, customerOrders);
        }

        // Save the new Order to the List of Orders from Customer
        customerOrders.add(order);
        orders.put(id, customerOrders);

        // Clear customers Cart from any previous Items
        cartDAO.clearCart(id);

        return order;
    }

    // Calculates total price for all items in cart
    // Returns total price as double
    public double calculateTotalPrice(Cart cart) {
        double totalPrice = 0.0;

        // Loop through the Carts items, getting the Total Cost
        for (Item item : cart.getItems().values()) {
            totalPrice += item.getBook().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
