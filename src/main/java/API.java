/**
 * Created by arthurgeron on 09/05/17.
 */

import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/")
public class API {


    @GET
    @Path("/users")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMsg() {

        String output = "List of users is empty.\n";

        return Response.status(200).entity(output).build();

    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMsg(@DefaultValue("") @QueryParam( value = "user") final String user,
                           @DefaultValue("") @QueryParam( value = "password") final String password
    ) {
        String result;
        JSONObject json = new JSONObject();
        try {
            result = Database.GetAuthToken(user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(503).entity("Internal Error").build();
        }
        if (result == null) {
            json.put("ERROR", "User or Password invalid");
            return Response.status(400).entity(json.toString()).build();
        }
        json.put("auth-token", result);
        return Response.status(200).entity(json.toString()).build();
    }
}
