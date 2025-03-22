package bookstore.dao;

import bookstore.exception.BookNotFoundException;
import bookstore.exception.CartNotFoundException;
import bookstore.exception.CustomerNotFoundException;
import bookstore.exception.InvalidInputException;
import bookstore.exception.OutOfStockException;
import bookstore.model.Book;
import bookstore.model.Cart;
import bookstore.model.Item;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class CartDAO {

    private static ConcurrentHashMap<String, Cart> carts = new ConcurrentHashMap<>();

    static {
//        Cart cart = new Cart(new Customer(UUID.randomUUID().toString(), "Endrit Brahimi", "w2044456@westminster.ac.uk"));
//        carts.put(cart.getCustomer().getId(), cart);
    }

    public void createCartforCustomer(String id) {
        carts.put(id, new Cart());
    }

    public Cart getCartByCustomerId(String id) {
        CustomerDAO customerDAO = new CustomerDAO();

        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        Cart cart = carts.get(id);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + id);
        }
        if (cart.getItems() == null) {
            cart.setItems(new ConcurrentHashMap<>());
        }

        return cart;
    }

    public Cart addItemToCart(String id, Item item) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();

        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        if (item == null || item.getBook() == null || item.getBook().getId() == null
                || item.getQuantity() == null) {
            throw new InvalidInputException("Item, book, book ID, and quantity must all be provided.");
        }

        if (!bookDAO.validBook(item.getBook().getId())) {
            throw new BookNotFoundException("Book not found with ID: " + item.getBook().getId());
        }

        if (!carts.containsKey(id)) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + id);
        }

        Book book = bookDAO.getBookById(item.getBook().getId());
        int requestedQuantity = item.getQuantity();
        int currentStock = book.getStock();

        if (requestedQuantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        if (currentStock < requestedQuantity) {
            throw new OutOfStockException("Insufficient stock for book with ID: " + item.getBook().getId());
        }

        Cart cart = carts.get(id);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + id);
        }

        if (cart.getItems() == null) {
            cart.setItems(new ConcurrentHashMap<>());
        }

        if (cart.getItems().containsKey(book.getId())) {
            int totalQuantity = cart.getItems().get(book.getId()).getQuantity() + requestedQuantity;
            if (totalQuantity > currentStock) {
                throw new InvalidInputException("Total quantity exceeds available stock for book with ID: " + book.getId());
            }
            cart.getItems().put(book.getId(),
                    new Item(bookDAO.getBookById(book.getId()), totalQuantity));
        } else {
            cart.getItems().put(book.getId(),
                    new Item(bookDAO.getBookById(book.getId()), requestedQuantity));
        }

        carts.put(id, cart);

        return cart;
    }

    public Cart updateItemInCart(String customerId, String bookId, Integer quantity) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();

        if (!customerDAO.validCustomer(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        if (!bookDAO.validBook(bookId)) {
            throw new BookNotFoundException("Book not found with ID: " + bookId);
        }

        if (quantity == null || quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        if (!carts.containsKey(customerId)) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + customerId);
        }

        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + customerId);
        }

        if (cart.getItems().containsKey(bookId)) {
            if (quantity > bookDAO.getBookById(bookId).getStock()) {
                throw new InvalidInputException("Quantity exceeds available stock for book with ID: " + bookId);
            }
            cart.getItems().put(bookId,
                    new Item(bookDAO.getBookById(bookId), quantity));
        } else {
            throw new CartNotFoundException("Item not found in cart for book with ID: " + bookId);
        }

        carts.put(customerId, cart);

        return cart;
    }

    public void deleteItemInCart(String customerId, String bookId) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();

        if (!customerDAO.validCustomer(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        if (!bookDAO.validBook(bookId)) {
            throw new BookNotFoundException("Book not found with ID: " + bookId);
        }

        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + customerId);
        }

        if (cart.getItems().containsKey(bookId)) {
            cart.getItems().remove(bookId);
        } else {
            throw new CartNotFoundException("Item not found in cart for book with ID: " + bookId);
        }
    }

    public void clearCart(String id) {
        Cart cart = carts.get(id);

        if (cart != null) {
            cart.getItems().clear();
            carts.put(id, cart);
        }
    }
}
