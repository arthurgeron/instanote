package infra;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by hellguy on 29/09/17.
 */
public class Database {
    public  Connection OpenConnection() throws SQLException {
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

    public  Boolean CreateUser(String user, String password) {
        try {
            Connection connection = OpenConnection();
            Statement statement;
            statement = connection.createStatement();
            statement.executeQuery("CALL createUser(\""+user+"\",\""+Security.GenerateMD5(password)+"\")");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  Boolean Login(String user, String password) throws SQLException, NoSuchAlgorithmException {
        Connection connection = OpenConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select login(\""+user+"\",\""+Security.GenerateMD5(password)+"\") as result");
        String result = null;
        if (resultSet.next()) {
            result = resultSet.getString("result");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return result.equals("1");
    }

    public  Boolean DeleteUser(String user, String password, String token) {
        try {
            Connection connection = OpenConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL deleteUser(\"" + user + "\",\"" + Security.GenerateMD5(password) + "\",\""+token+"\")");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  Boolean ActivateUser(String user, String password, String token) {
        try {
            Connection connection = OpenConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL activateUser(\"" + user + "\",\"" + Security.GenerateMD5(password) + "\",\""+token+"\")");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public  Boolean ValidateToken(String user, String token) throws SQLException, NoSuchAlgorithmException {
        Connection connection = OpenConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select token(\""+user+"\",\""+token+"\") as result");
        String result = null;
        if (resultSet.next()) {
            result = resultSet.getString("result");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return result.equals("1");
    }

    public  Boolean StoreToken(String user, String password, String token){
        try {
            Connection connection = OpenConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL storeToken(\""+user+"\",\""+Security.GenerateMD5(password)+"\",\""+token+"\")");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
