package bookstore.mapper;

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
