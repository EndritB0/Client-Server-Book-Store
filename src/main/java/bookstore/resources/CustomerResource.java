package bookstore.resources;

import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.model.Customer;
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
@Path("/customers")
public class CustomerResource {
    
    // Logger to log program activites
    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);

    // CustomerDAO instance for customer operations
    CustomerDAO customerDAO = new CustomerDAO();

    // POST Request to create new customer
    // Receives and sends JSON data
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer newCustomer) {
        // Calls DAO to create new customer
        Customer customer = customerDAO.createCustomer(newCustomer);
        logger.info("Created new customer with ID: {}", customer.getId());

        // Returns 201 Created Status
        // With the new Customer Object
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    // GET Request to get all customers
    // Sends JSON data
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        // Calls DAO to get all customers
        // Returns 200 OK status
        // With all Customer Objects
        logger.info("Retrieved all customers");
        return Response.ok(customerDAO.getAllCustomers()).build();
    }

    // GET Request to get specific customer by ID
    // Sends JSON data
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") String id) {
        // Calls DAO to get customer by ID
        Customer customer = customerDAO.getCustomerById(id);
        logger.info("Retrieved customer with ID: {}", id);

        // Returns 200 OK status
        // With the Customer Object
        return Response.ok(customer).build();
    }

    // PUT Request to update customer information
    // Receives and sends JSON data
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") String id, Customer updatedCustomer) {
        // Calls DAO to update customer
        Customer customer = customerDAO.updateCustomer(id, updatedCustomer);
        logger.info("Updated customer with ID: {}", id);

        // Returns 200 OK status
        // With the updated Customer Object
        return Response.ok(customer).build();
    }

    // DELETE Request to delete customer by ID
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") String id) {
        // Calls DAO to delete customer
        customerDAO.deleteCustomer(id);
        logger.info("Deleted customer with ID: {}", id);

        // Returns 204 No Content status
        return Response.noContent().build();
    }
}
