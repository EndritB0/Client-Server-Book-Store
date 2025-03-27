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

    private static ConcurrentHashMap<String, List<Order>> orders = new ConcurrentHashMap<>();

    static {
//        Order order = new Order(UUID.randomUUID().toString(), new Customer(), new ArrayList<Book>(), 10);
//        orders.put(order.getId(), order);
    }

    public List<Order> getAllOrdersByCustomer(String id) {
        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        return orders.get(id);
    }

    public Order getOrderByCustomerId(String customerId, String orderId) {
        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.validCustomer(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        List<Order> customerOrders = orders.get(customerId);
        if (customerOrders == null) {
            throw new OrderNotFoundException("No orders found for customer with ID: " + customerId);
        }

        for (Order order : customerOrders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }

        throw new OrderNotFoundException("Order not found with ID: " + orderId + " for customer with ID: " + customerId);
    }

    public Order createOrderFromCustomer(String id) {
        CustomerDAO customerDAO = new CustomerDAO();
        CartDAO cartDAO = new CartDAO();
        BookDAO bookDAO = new BookDAO();

        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        Cart cart = cartDAO.getCartByCustomerId(id);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new InvalidInputException("Cannot create an order: Cart is empty.");
        }

        ArrayList<Item> items = new ArrayList<>(cart.getItems().values());
        for (Item item : items) {
            Book updatedBook = item.getBook();
            updatedBook.setStock(updatedBook.getStock() - item.getQuantity());
            bookDAO.updateBook(updatedBook.getId(), updatedBook);
        }

        Order order = new Order(
                UUID.randomUUID().toString(),
                new ArrayList<>(cart.getItems().values()),
                calculateTotalPrice(cart));

        List<Order> customerOrders = orders.get(id);
        if (customerOrders == null) {
            customerOrders = new ArrayList<>();
            orders.put(id, customerOrders);
        }
        customerOrders.add(order);
        orders.put(id, customerOrders);

        cartDAO.clearCart(id);

        return order;
    }

    public double calculateTotalPrice(Cart cart) {
        double totalPrice = 0.0;

        for (Item item : cart.getItems().values()) {
            totalPrice += item.getBook().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
