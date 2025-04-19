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

    // Concurrent HashMap that holds all customer carts in memory
    // Key: Customer ID, Value: Cart Object
    private static ConcurrentHashMap<String, Cart> carts = new ConcurrentHashMap<>();

    static {
//        Cart cart = new Cart(new Customer(UUID.randomUUID().toString(), "Endrit Brahimi", "w2044456@westminster.ac.uk"));
//        carts.put(cart.getCustomer().getId(), cart);
    }

    // Creates a new empty cart for a customer
    public void createCartforCustomer(String id) {
        carts.put(id, new Cart());
    }

    // Retrieves a customer's cart by their ID
    // Returns Cart Object
    public Cart getCartByCustomerId(String id) {
        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        CustomerDAO customerDAO = new CustomerDAO();
        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        // Validate if Cart for customer with ID exists
        // Throws CartNotFoundException if CART doesn't exist
        Cart cart = carts.get(id);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + id);
        }

        // Initializes items if null
        if (cart.getItems() == null) {
            cart.setItems(new ConcurrentHashMap<>());
        }

        return cart;
    }

    // Adds an item to a customer's cart
    // Returns updated Cart Object
    public Cart addItemToCart(String id, Item item) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();

        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        if (!customerDAO.validCustomer(id)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }

        // Validates all required fields are provided
        // Throws InvalidInputException if any field is missing
        if (item == null || item.getBook() == null || item.getBook().getId() == null
                || item.getQuantity() == null) {
            throw new InvalidInputException("Item, book, book ID, and quantity must all be provided.");
        }

        // Validate if Book with book ID exists
        // Throws BookNotFoundException if book doesn't exist
        if (!bookDAO.validBook(item.getBook().getId())) {
            throw new BookNotFoundException("Book not found with ID: " + item.getBook().getId());
        }

        // Validate if Cart for customer with ID exists
        // Throws CartNotFoundException if cart doesn't exist 
        Cart cart = carts.get(id);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + id);
        }

        // Gets stock availability 
        Book book = bookDAO.getBookById(item.getBook().getId());
        int requestedQuantity = item.getQuantity();
        int currentStock = book.getStock();

        // Validate if the requested quantity isn't smaller than than 0
        // Throws InvalidInputException if true
        if (requestedQuantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        // Validate if requested quanity is smaller than current Book stock
        // Throws OutOfStockException if true
        if (currentStock < requestedQuantity) {
            throw new OutOfStockException("Insufficient stock for book with ID: " + item.getBook().getId());
        }

        // Initializes items if null
        if (cart.getItems() == null) {
            cart.setItems(new ConcurrentHashMap<>());
        }

        // Checks if the Book is already in the Cart
        if (cart.getItems().containsKey(book.getId())) {
            // Gets the total Quantity requested and checks if its smaller than Book Stock amount
            // Throws InvalidInputException if requested amount is larger than stocks amount
            int totalQuantity = cart.getItems().get(book.getId()).getQuantity() + requestedQuantity;
            if (totalQuantity > currentStock) {
                throw new InvalidInputException("Total quantity exceeds available stock for book with ID: " + book.getId());
            }

            // Updates Cart with the new requested amount
            cart.getItems().put(book.getId(),
                    new Item(bookDAO.getBookById(book.getId()), totalQuantity));
        } else {
            // When its first time book is being added
            // Adds Book into the cart with request quantity
            cart.getItems().put(book.getId(),
                    new Item(bookDAO.getBookById(book.getId()), requestedQuantity));
        }

        // Saves updated Cart Object
        carts.put(id, cart);

        return cart;
    }

    // Updates item quantity in a customer's cart
    // Returns updated Cart Object
    public Cart updateItemInCart(String customerId, String bookId, Integer quantity) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();

        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        if (!customerDAO.validCustomer(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        // Validate if Book with ID exists
        // Throws BookNotFoundException if book doesn't exist
        if (!bookDAO.validBook(bookId)) {
            throw new BookNotFoundException("Book not found with ID: " + bookId);
        }

        // Validate if quantity isn't smaller than 0 and not null
        // Throws BookNotFoundException if quantity larger or equal to 0 or null
        if (quantity == null || quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        // Validate if Cart for customer with ID exists
        // Throws CartNotFoundException if cart doesn't exist 
        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + customerId);
        }

        // Checks if the Book is already in the Cart
        if (cart.getItems().containsKey(bookId)) {
            // Checks if the new quantity is smaller than Book Stock amount
            // Throws InvalidInputException if requested amount is larger than stocks amount
            if (quantity > bookDAO.getBookById(bookId).getStock()) {
                throw new InvalidInputException("Quantity exceeds available stock for book with ID: " + bookId);
            }

            // Updates Cart with the new requested amount
            cart.getItems().put(bookId,
                    new Item(bookDAO.getBookById(bookId), quantity));
        } else {
            // Throws CartNotFoundException when Book isn't found in Cart
            throw new CartNotFoundException("Item not found in cart for book with ID: " + bookId);
        }

        // Saves updated Cart Object
        carts.put(customerId, cart);

        return cart;
    }

    // Removes an item from a customer's cart
    public void deleteItemInCart(String customerId, String bookId) {
        CustomerDAO customerDAO = new CustomerDAO();
        BookDAO bookDAO = new BookDAO();

        // Validate if Customer with ID exists
        // Throws CustomerNotFoundException if customer doesn't exist
        if (!customerDAO.validCustomer(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        // Validate if Book with ID exists
        // Throws BookNotFoundException if book doesn't exist
        if (!bookDAO.validBook(bookId)) {
            throw new BookNotFoundException("Book not found with ID: " + bookId);
        }

        // Validate if Cart for customer with ID exists
        // Throws CartNotFoundException if cart doesn't exist 
        Cart cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer with ID: " + customerId);
        }

        // Validate if Book is in customer's cart
        // Throws CartNotFoundException if book isn't in the cart
        if (cart.getItems().containsKey(bookId)) {
            // Removes book from the Cart
            cart.getItems().remove(bookId);
        } else {
            throw new CartNotFoundException("Item not found in cart for book with ID: " + bookId);
        }
    }

    // Clears all items from a customer's cart
    public void clearCart(String id) {
        Cart cart = carts.get(id);

        // Checks for Customers Cart if null
        if (cart != null) {
            // When cart isn't null
            // Clear cart and save the cart
            cart.getItems().clear();
            carts.put(id, cart);
        } else {
            // Initializes cart if is null
            createCartforCustomer(id);
        }
    }
}
