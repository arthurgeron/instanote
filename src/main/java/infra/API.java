package infra; /**
 * Created by arthurgeron on 09/05/17.
 */

import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@Path("/")
public class API {

    private Response InternalError() {
        return Response.status(503).entity("Internal Error").build();
    }

    private Response InternalError(Exception e) {
        e.printStackTrace();
        return Response.status(503).entity("Internal Error").build();
    }

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
        String token;
        JSONObject json = new JSONObject();
        try {
            Database database = new Database();
            if (database.Login(user, password)) {
                token = Security.GenerateToken();
                boolean storedToken  = database.StoreToken(user, password, token);
                if (!storedToken || !database.ValidateToken(user, token)) {
                    return InternalError();
                }
            } else {
                json.put("ERROR", "User or Password invalid");
                return Response.status(400).entity(json.toString()).build();
            }
        } catch (SQLException e) {
            return InternalError(e);
        } catch (NoSuchAlgorithmException e) {
            return InternalError(e);
        }
        if (token == null)
            return InternalError();
        else {
            json.put("auth-token", token);
            return Response.status(200).entity(json.toString()).build();
        } 
    }
}
