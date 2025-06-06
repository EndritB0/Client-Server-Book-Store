package bookstore.resources;

import bookstore.dao.CartDAO;
import bookstore.model.Cart;
import bookstore.model.Item;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers/{customerId}/cart")
public class CartResource {

    // Logger to log program activites
    private static final Logger logger = LoggerFactory.getLogger(CartResource.class);

    // CartDAO instance for cart operations
    CartDAO cartDAO = new CartDAO();

    // POST Request to add item to cart
    // Receives and sends JSON data
    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@PathParam("customerId") String id, Item item) {
        // Calls DAO to add item to customer's cart
        Cart cart = cartDAO.addItemToCart(id, item);
        logger.info("Added book with ID: {} to cart for customer with ID: {} ", item.getBook().getId(), id);

        // Returns 201 Created Status
        // With the added Item to the Cart
        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    // GET Request to retrieve customer's cart
    // Sends JSON data
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") String id) {
        // Calls DAO to get customer's cart
        Cart cart = cartDAO.getCartByCustomerId(id);
        logger.info("Retrieved cart for customer with ID: {} ", id);

        // Returns 200 OK status
        // With Cart object
        return Response.ok(cart).build();
    }

    // PUT Request to update item quantity in cart
    // Receives and sends JSON data
    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItemInCart(
            @PathParam("customerId") String customerId,
            @PathParam("bookId") String bookId,
            Integer quantity) {
        // Calls DAO to update item quantity
        Cart cart = cartDAO.updateItemInCart(customerId, bookId, quantity);
        logger.info("Updated cart for customer with ID: {} ", customerId);

        // Returns 200 OK status
        // With the updated Cart Object
        return Response.ok(cart).build();
    }

    // DELETE Request to remove item from cart
    @DELETE
    @Path("/items/{bookId}")
    public Response deleteItemInCart(
            @PathParam("customerId") String customerId,
            @PathParam("bookId") String bookId) {
        // Calls DAO to remove item from cart
        cartDAO.deleteItemInCart(customerId, bookId);
        logger.info("Deleted book with id: {} from cart for customer with ID: {} ", bookId, customerId);

        // Returns 204 No Content status
        return Response.noContent().build();
    }
}
