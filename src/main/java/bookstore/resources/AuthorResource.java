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
@Path("/authors")
public class AuthorResource {

    @POST
    public Response addAuthor() {
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response getAuthors() {
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") String id) {
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") String id) {
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") String id) {
        return Response.ok().build();
    }
}
