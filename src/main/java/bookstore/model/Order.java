package bookstore.model;

import java.util.List;

/**
 *
 * @author Endrit Brahimi
 */
public class Order {

    private String id;
    private Customer customer;
    private List<Book> books;
    private double totalPrice;

    public Order() {
    }

    public Order(String id, Customer customer, List<Book> books, double totalPrice) {
        this.id = id;
        this.customer = customer;
        this.books = books;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
