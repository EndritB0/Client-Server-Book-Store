package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
