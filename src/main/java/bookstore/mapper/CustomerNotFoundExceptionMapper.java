package bookstore.mapper;

import bookstore.message.ErrorMessage;
import bookstore.exception.CustomerNotFoundException;
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
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {

    // Exception mapper that converts CustomerNotFoundException to HTTP responses
    // Returns 404 Not Found status with error details in JSON format
    private static final Logger logger = LoggerFactory.getLogger(CustomerNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        logger.error("Customer not found: {}", exception.getMessage(), exception);
        ErrorMessage error = new ErrorMessage("Customer not found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
