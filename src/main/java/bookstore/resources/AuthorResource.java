package bookstore.resources;

import bookstore.dao.AuthorDAO;
import bookstore.model.Author;
import bookstore.model.Book;
import java.util.List;
import javax.ws.rs.Consumes;
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
@Path("/authors")
public class AuthorResource {

    private AuthorDAO authorDAO = new AuthorDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author newAuthor) {
        Author author = authorDAO.createAuthor(newAuthor);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        return Response.ok(authorDAO.getAllAuthors()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") String id) {
        Author author = authorDAO.getAuthorById(id);
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") String id, Author updatedAuthor) {
        Author author = authorDAO.updateAuthor(id, updatedAuthor);
        return Response.ok(author).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") String id) {
        authorDAO.deleteAuthor(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@PathParam("id") String id) {
        List<Book> books = authorDAO.getBooksByAuthor(id);
        return Response.ok(books).build();
    }
}
