package bookstore.mapper;

import bookstore.exception.AuthorNotFoundException;
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
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {

    // Exception mapper that converts AuthorNotFoundException to HTTP responses
    // Returns 404 Not Found status with error details in JSON format
    private static final Logger logger = LoggerFactory.getLogger(AuthorNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(AuthorNotFoundException exception) {
        logger.error("Author not found: {}", exception.getMessage(), exception);
        ErrorMessage error = new ErrorMessage("Author not found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
