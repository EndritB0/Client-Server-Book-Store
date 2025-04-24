package bookstore.message;

/**
 *
 * @author Endrit Brahimi
 */
public class ErrorMessage {
    // Used as to store the error and its message to format to JSON

    private String error;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
