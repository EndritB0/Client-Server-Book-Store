package bookstore.resources;

import bookstore.dao.BookDAO;
import bookstore.model.Book;
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
@Path("/books")
public class BookResource {

    // BookDAO instance for book operations
    BookDAO bookDAO = new BookDAO();

    // POST Request to create new Books
    // Receives and sends JSON data
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book newBook) {
        // Calls DAO to create new Book
        Book book = bookDAO.createBook(newBook);

        // Returns 201 Created Status
        // With the new Book Object
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    // GET Request to get all Books
    // Sends JSON data
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        // Calls DAO to get all Books
        // Returns 200 OK status
        // With all Book Objects
        return Response.ok(bookDAO.getAllBooks()).build();
    }

    // GET Request to get specific Book by ID
    // Sends JSON data
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") String id) {
        // Calls DAO to get Book by ID
        Book book = bookDAO.getBookById(id);

        // Returns 200 OK status
        // With the Book Object
        return Response.ok(book).build();
    }

    // PUT Request to update Book information
    // Receives and sends JSON data
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") String id, Book updatedBook) {
        // Calls DAO to update Book
        Book book = bookDAO.updateBook(id, updatedBook);

        // Returns 200 OK status
        // With the updated Book Object
        return Response.ok(book).build();
    }

    // DELETE Request to delete Book by ID
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") String id) {
        // Calls DAO to delete Book
        bookDAO.deleteBook(id);

        // Returns 204 No Content status
        return Response.noContent().build();
    }
}
