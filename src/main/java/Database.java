import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by hellguy on 29/09/17.
 */
public class Database {
    public static Connection OpenConnection() throws SQLException {
        Credentials credentials = new Credentials();
        Connection connection;
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(credentials.getUser());
        dataSource.setPassword(credentials.getPassword());
        dataSource.setServerName(credentials.getAddress());
        dataSource.setDatabaseName(credentials.getDatabaseName());
        connection = dataSource.getConnection();
        return connection;
    }

    public static String GetAuthToken(String user, String password) throws SQLException {
        Connection connection = OpenConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select id from users where username='"+user+"' and password='"+password+"'");
        String authToken = null;
        if (resultSet.next()) {
            authToken = resultSet.getString("id");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return authToken;
    }
}
