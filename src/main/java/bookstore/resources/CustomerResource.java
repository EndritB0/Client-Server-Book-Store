package bookstore.resources;

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

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers")
public class CustomerResource {

    CustomerDAO customerDAO = new CustomerDAO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer newCustomer) {
        try {
            Customer customer = customerDAO.createCustomer(newCustomer);
            return Response.status(Response.Status.CREATED).entity(customer).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        return Response.ok(customerDAO.getAllCustomers()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") String id) {
        try {
            Customer customer = customerDAO.getCustomerById(id);
            return Response.ok(customer).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") String id, Customer updatedCustomer) {
        try {
            Customer customer = customerDAO.updateCustomer(id, updatedCustomer);
            return Response.ok(customer).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") String id) {
        try {
            customerDAO.deleteCustomer(id);
            return Response.noContent().build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found").build();
        }
    }
}
