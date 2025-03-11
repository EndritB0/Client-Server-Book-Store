package bookstore.model;

import java.util.Map;

/**
 *
 * @author Endrit Brahimi
 */
public class Cart {

    public static class Item {

        private Book book;
        private int quantity;

        public Item() {
        }

        public Item(Book book, int quantity) {
            this.book = book;
            this.quantity = quantity;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    private Customer customer;
    private Map<String, Item> items;

    public Cart() {
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }
}
