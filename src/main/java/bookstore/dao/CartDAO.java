package bookstore.dao;

import bookstore.model.Cart;
import bookstore.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class CartDAO {

    private static ConcurrentHashMap<String, Cart> carts = new ConcurrentHashMap<>();

    static {
        Cart cart = new Cart(new Customer(UUID.randomUUID().toString(), "Endrit Brahimi", "w2044456@westminster.ac.uk"));
        carts.put(cart.getCustomer().getId(), cart);
    }

    public List<Cart> getAllCarts() {
        return new ArrayList<>(carts.values());

    }
}
