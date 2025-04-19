package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class AuthorNotFoundException extends RuntimeException {

    // Custom exception thrown when an author cannot be found in the system
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
