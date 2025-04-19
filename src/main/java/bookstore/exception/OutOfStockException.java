package bookstore.exception;

/**
 *
 * @author Endrit Brahimi
 */
public class OutOfStockException extends RuntimeException {

    // Custom exception thrown when attempting to increase item quantity is higher than stock
    public OutOfStockException(String message) {
        super(message);
    }
}
