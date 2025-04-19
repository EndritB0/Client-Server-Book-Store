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

    // AuthorDAO instance for author operations
    private AuthorDAO authorDAO = new AuthorDAO();

    // POST Request to create new Authors
    // Recieves and Sends JSON data
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthor(Author newAuthor) {
        // Calls DAO to create a new Author Object
        Author author = authorDAO.createAuthor(newAuthor);

        // Returns 201 Created Status
        // With the new Author Object
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    // GET Request to get all Authors
    // Sends JSON data
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        // Calls DAO to get all Authors
        // Returns 200 OK status
        // With all Author Objects
        return Response.ok(authorDAO.getAllAuthors()).build();
    }

    // GET Request to get specific Author by ID
    // Sends JSON data
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") String id) {
        // Calls DAO to get Author by ID
        Author author = authorDAO.getAuthorById(id);

        // Returns 200 OK status
        // With the Author Object
        return Response.ok(author).build();
    }

    // PUT Request to update Author's information
    // Recieves and Sends JSON data
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") String id, Author updatedAuthor) {
        // Calls DAO to update Author by ID
        Author author = authorDAO.updateAuthor(id, updatedAuthor);

        // Returns 200 OK status
        // With the updated Author Object
        return Response.ok(author).build();
    }

    // DELETE Request to delete Author by ID
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") String id) {
        // Calls DAO to delete Author by ID
        authorDAO.deleteAuthor(id);

        // Returns 204 No Content status
        return Response.noContent().build();
    }

    // GET Request to get all the books by author
    // Sends JSON data
    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@PathParam("id") String id) {
        // Calls DAO to get the books written by Author
        List<Book> books = authorDAO.getBooksByAuthor(id);

        // Returns 200 OK status
        // With the ArrayList of Books
        return Response.ok(books).build();
    }
}
