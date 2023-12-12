import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
    /**
     * Establishes a connection to the database.
     *
     * @return the database connection
     * @throws ClassNotFoundException if the MySQL JDBC driver class is not found
     * @throws SQLException if a database access error occurs
     *
     * Example:
     * <pre>{@code
     * Connection connection = getDBConnection();
     * }</pre>
     */
    public static Connection getDBConnection() {
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus", "root", "");
            return connection;
        } catch (Exception ex) {
            return null;
        }//try catch closed
    }//getDBConnection() closed
}//class closed
