import infra.Database;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
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
    public void canCreateUser() {
        Boolean loggedIn;
        try {
            Database database = new Database();
            loggedIn = database.CreateUser(user, password);
            database.DeleteUser(user, password);
            Assert.assertTrue(loggedIn);
        } catch (Exception e) {
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
            Database database = new Database();
            validToken = database.ValidateToken(token);
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
            deletedUser = database.DeleteUser(user, password);
            Assert.assertTrue(deletedUser);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
