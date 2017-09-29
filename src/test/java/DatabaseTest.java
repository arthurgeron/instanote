import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hellguy on 29/09/17.
 */
public class DatabaseTest {

    @Test
    public void canOpenConnection() {
        Connection connection;
        try {
            connection = Database.OpenConnection();
            Assert.assertNotNull(connection);
            connection.close();
        } catch (SQLException e) {
            Assert.fail();
        }
    }

    @Test
    public void canLogIn() {
        Connection connection;
        String authToken;
        try {
            connection = Database.OpenConnection();
            authToken = Database.GetAuthToken("arthur", "senha123");
            connection.close();
            Assert.assertNotNull(connection);
            Assert.assertNotNull(authToken);
        } catch (SQLException e) {
            Assert.fail();
        }
    }
}
