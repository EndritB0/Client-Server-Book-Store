package bookstore.resources;

import bookstore.dao.OrderDAO;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers/{customerId}/orders")
public class OrderResource {
    
    OrderDAO orderDAO = new OrderDAO();

    @POST
    public Response createOrder(@PathParam("customerId") String customerId) {
        // TODO: Implement logic to create a new order
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("customerId") String customerId) {
        // TODO: Implement logic to retrieve all orders for a customer
        return Response.status(Response.Status.OK).entity(orderDAO.getAllOrders()).build();
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
