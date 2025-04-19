package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class CartNotFoundException extends RuntimeException {

    // Custom exception thrown when an cart cannot be found in the system
    public CartNotFoundException(String message) {
        super(message);
    }
}
