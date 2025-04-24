package bookstore.mapper;

import bookstore.message.ErrorMessage;
import bookstore.exception.CartNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Endrit Brahimi
 */
@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {

    // Exception mapper that converts CartNotFoundException to HTTP responses
    // Returns 404 Not Found status with error details in JSON format
    private static final Logger logger = LoggerFactory.getLogger(CartNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(CartNotFoundException exception) {
        logger.error("Cart not found: {}", exception.getMessage(), exception);
        ErrorMessage error = new ErrorMessage("Cart Not Found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
