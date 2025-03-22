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

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers/{customerId}/cart")
public class CartResource {

    CartDAO cartDAO = new CartDAO();

    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@PathParam("customerId") String id, Item item) {
        Cart cart = cartDAO.addItemToCart(id, item);
        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") String id) {
        Cart cart = cartDAO.getCartByCustomerId(id);
        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItemInCart(
            @PathParam("customerId") String customerId,
            @PathParam("bookId") String bookId,
            Integer quantity) {
        Cart cart = cartDAO.updateItemInCart(customerId, bookId, quantity);
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response deleteItemInCart(
            @PathParam("customerId") String customerId,
            @PathParam("bookId") String bookId) {
        cartDAO.deleteItemInCart(customerId, bookId);
        return Response.noContent().build();
    }
}
