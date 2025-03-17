package bookstore.dao;

import bookstore.model.Book;
import bookstore.model.Customer;
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

    private static ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();

    static {
        Order order = new Order(UUID.randomUUID().toString(), new Customer(), new ArrayList<Book>(), 10);
        orders.put(order.getId(), order);
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
