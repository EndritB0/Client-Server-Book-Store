package bookstore.model;

import java.util.List;

/**
 *
 * @author Endrit Brahimi
 */
public class Order {

    private String id;
    private List<Item> items;
    private double totalPrice;

    public Order() {
    }

    public Order(String id, List<Item> items, double totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
