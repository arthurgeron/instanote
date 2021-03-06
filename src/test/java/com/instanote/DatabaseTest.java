package com.instanote;

import com.instanote.infra.Database;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hellguy on 29/09/17.
 */
public class DatabaseTest {

    private String user = "thisIsATestUser123123", password = "test123", token = "testToken";
    @Test
    public void canOpenConnection() {
        Connection connection;
        try {
            Database database = new Database();
            connection = database.OpenConnection();
            Assert.assertNotNull(connection);
            connection.close();
        } catch (SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void canLogIn() {
        Boolean loggedIn;
        try {
            Database database = new Database();
            loggedIn = database.Login(user, password);
            Assert.assertTrue(loggedIn);
        } catch (Exception e) {
            Assert.fail();
        }
    }
    
    @Test
    public void canStoreToken() {
        Boolean storedToken;
        try {
            Database database = new Database();
            storedToken = database.StoreToken(user, password, token);
            Assert.assertTrue(storedToken);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void canValidateToken() {
        Boolean validToken;
        try {
            //Enforce user is active to avoid failing the test, since tests are ran in a random order
            canActivateUser();
            Database database = new Database();
            validToken = database.ValidateToken(user, token);
            Assert.assertTrue(validToken);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void canDeleteUser() {
        Boolean deletedUser;
        try {
            Database database = new Database();
            deletedUser = database.DeleteUser(user, password, token);
            Assert.assertTrue(deletedUser);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void canActivateUser() {
        Boolean deletedUser;
        try {
            Database database = new Database();
            deletedUser = database.ActivateUser(user, password, token);
            Assert.assertTrue(deletedUser);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
