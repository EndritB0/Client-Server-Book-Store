package bookstore.resources;

import bookstore.dao.CartDAO;
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
    public Response addItemToCart(@PathParam("customerId") String customerId) {
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") String customerId) {
        return Response.status(Response.Status.OK).entity(cartDAO.getAllCarts()).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(
            @PathParam("customerId") String customerId,
            @PathParam("bookId") String bookId) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response deleteCartItem(
            @PathParam("customerId") String customerId,
            @PathParam("bookId") String bookId) {
        return Response.noContent().build();
    }
}