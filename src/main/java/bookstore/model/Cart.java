package bookstore.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class Cart {

    private Customer customer;
    // Concurrent HashMap that holds the book and its quantity in the cart
    // Key: Book ID, Value: Item Object to hold the Book
    private ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>();

    public Cart() {
    }

    public ConcurrentHashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(ConcurrentHashMap<String, Item> items) {
        this.items = items;
    }
}
