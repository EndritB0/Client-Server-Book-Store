package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
