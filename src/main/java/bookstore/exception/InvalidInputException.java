package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class InvalidInputException extends RuntimeException {

    // Custom exception thrown when invalid input is provided 
    public InvalidInputException(String message) {
        super(message);
    }
}
