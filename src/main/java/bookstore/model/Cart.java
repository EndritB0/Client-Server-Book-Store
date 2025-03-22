package bookstore.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class Cart {

    private Customer customer;
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
