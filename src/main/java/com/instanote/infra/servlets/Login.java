package com.instanote.infra.servlets;

import com.instanote.infra.Database;
import com.instanote.infra.Security;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by hellguy on 02/10/17.
 */
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String token = null;
        JSONObject json = new JSONObject();
        try {
            Database database = new Database();
            if (database.Login(user, password)) {
                token = Security.GenerateToken();
                boolean storedToken  = database.StoreToken(user, password, token);
                if (!storedToken || !database.ValidateToken(user, token)) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                json.put("ERROR", "User or Password invalid");
                response.getWriter().print(json.toJSONString());
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NoSuchAlgorithmException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        if (token == null)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        else {
            json.put("auth-token", token);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(json.toJSONString());
        }
    }
}
