package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }
}
