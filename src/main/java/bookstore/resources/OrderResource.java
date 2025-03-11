package bookstore.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers/{customerId}/orders")
public class OrderResource {

    @POST
    public Response createOrder(@PathParam("customerId") String customerId) {
        // TODO: Implement logic to create a new order
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response getOrders(@PathParam("customerId") String customerId) {
        // TODO: Implement logic to retrieve all orders for a customer
        return Response.ok().build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrder(
            @PathParam("customerId") String customerId,
            @PathParam("orderId") String orderId) {
        // TODO: Implement logic to retrieve a specific order by ID
        return Response.ok().build();
    }
}
