package bookstore.mapper;

import bookstore.exception.OutOfStockException;
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
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {

    // Exception mapper that converts OutOfStockException to HTTP 400 responses
    // Handles attempting to increase item quantity is higher than stock
    private static final Logger logger = LoggerFactory.getLogger(OutOfStockExceptionMapper.class);

    @Override
    public Response toResponse(OutOfStockException exception) {
        logger.error("Out of Stock: {}", exception.getMessage(), exception);
        ErrorMessage error = new ErrorMessage("Out of Stock", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
