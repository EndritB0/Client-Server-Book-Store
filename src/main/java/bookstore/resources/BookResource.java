package bookstore.resources;

import bookstore.dao.BookDAO;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Endrit Brahimi
 */
@Path("/books")
public class BookResource {
    
    BookDAO bookDAO = new BookDAO();

    @POST
    public Response addBook() {
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.status(Response.Status.OK).entity(bookDAO.getAllBooks()).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") String id) {
        return Response.noContent().build();
    }
}
