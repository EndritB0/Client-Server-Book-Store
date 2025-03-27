package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String message) {
        super(message);
    }
}
