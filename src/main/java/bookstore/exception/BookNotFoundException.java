package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class BookNotFoundException extends RuntimeException {

    // Custom exception thrown when an book cannot be found in the system
    public BookNotFoundException(String message) {
        super(message);
    }
}
