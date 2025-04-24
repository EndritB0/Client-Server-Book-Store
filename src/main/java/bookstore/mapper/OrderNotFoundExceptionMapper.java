package bookstore.mapper;

import bookstore.message.ErrorMessage;
import bookstore.exception.OrderNotFoundException;
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
public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException> {

    // Exception mapper that converts OrderNotFoundException to HTTP responses
    // Returns 404 Not Found status with error details in JSON format
    private static final Logger logger = LoggerFactory.getLogger(OrderNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(OrderNotFoundException exception) {
        logger.error("Order not found: {}", exception.getMessage(), exception);
        ErrorMessage error = new ErrorMessage("Order not found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
