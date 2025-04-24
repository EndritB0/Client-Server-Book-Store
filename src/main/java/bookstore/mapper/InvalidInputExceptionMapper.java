package bookstore.mapper;

import bookstore.message.ErrorMessage;
import bookstore.exception.InvalidInputException;
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
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    // Exception mapper that converts InvalidInputException to HTTP 400 responses
    // Handles invalid input when provided 
    private static final Logger logger = LoggerFactory.getLogger(InvalidInputExceptionMapper.class);

    @Override
    public Response toResponse(InvalidInputException exception) {
        logger.error("Invalid Input: {}", exception.getMessage(), exception);
        ErrorMessage error = new ErrorMessage("Invalid Input", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
