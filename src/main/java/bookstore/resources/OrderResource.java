package bookstore.resources;

import bookstore.dao.OrderDAO;
import bookstore.model.Order;
import java.util.List;
import javax.ws.rs.Consumes;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") String id) {
        Order order = orderDAO.createOrderFromCustomer(id);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrdersByCustomer(@PathParam("customerId") String id) {
        List<Order> orders = orderDAO.getAllOrdersByCustomer(id);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByCustomerId(
            @PathParam("customerId") String customerId,
            @PathParam("orderId") String orderId) {
        Order order = orderDAO.getOrderByCustomerId(customerId, orderId);
        return Response.ok(order).build();
    }
}
