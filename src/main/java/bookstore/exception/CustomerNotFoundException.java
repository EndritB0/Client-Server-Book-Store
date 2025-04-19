package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class CustomerNotFoundException extends RuntimeException {

    // Custom exception thrown when an customer cannot be found in the system
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
