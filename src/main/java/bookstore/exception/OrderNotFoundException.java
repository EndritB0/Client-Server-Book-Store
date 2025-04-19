package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class OrderNotFoundException extends RuntimeException {

    // Custom exception thrown when an book cannot be found in the system
    public OrderNotFoundException(String message) {
        super(message);
    }
}
