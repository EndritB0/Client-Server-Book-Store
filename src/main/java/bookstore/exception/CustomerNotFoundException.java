package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
