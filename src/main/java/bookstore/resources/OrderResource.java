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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers/{customerId}/orders")
public class OrderResource {
    
    // Logger to log program activites
    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);

    // OrderDAO instance for order operations
    OrderDAO orderDAO = new OrderDAO();

    // POST Request to create new order from customer's cart
    // Produces JSON response
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") String id) {
        // Calls DAO to create order from customer's cart
        Order order = orderDAO.createOrderFromCustomer(id);
        logger.info("Created new order from cart from customer with ID: {}", id);

        // Returns 201 Created Status
        // With the new Order Object
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    // GET Request to retrieve all customer's orders
    // Produces JSON response
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrdersByCustomer(@PathParam("customerId") String id) {
        // Calls DAO to get all orders for customer
        List<Order> orders = orderDAO.getAllOrdersByCustomer(id);
        logger.info("Retrieved all orders by customer with ID: {}", id);

        // Returns 200 OK status
        // With all Orders
        return Response.ok(orders).build();
    }

    // GET Request to retrieve specific order by ID
    // Produces JSON response
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByCustomerId(
            @PathParam("customerId") String customerId,
            @PathParam("orderId") String orderId) {
        // Calls DAO to get specific order
        Order order = orderDAO.getOrderByCustomerId(customerId, orderId);
        logger.info("Retrieved order with ID: {} by customer with ID: {}", orderId, customerId);

        // Returns 200 OK status
        // With the Order Object
        return Response.ok(order).build();
    }
}
