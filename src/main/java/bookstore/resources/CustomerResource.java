package bookstore.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Endrit Brahimi
 */
@Path("/customers")
public class CustomerResource {

    @POST
    public Response addCustomer() {
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response getCustomers() {
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") String id) {
        return Response.noContent().build();
    }
}
