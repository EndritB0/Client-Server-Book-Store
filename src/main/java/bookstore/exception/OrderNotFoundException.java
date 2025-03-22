package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
