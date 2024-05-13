package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static final String URL = "jdbc:mysql://localhost:3306/RealtyHub";
    private static final String USER = "root";
    private static final String PASSWORD = "CS31iber@11Y";

    // Method to establish and return a database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create and return a connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Handle error for missing JDBC driver
            throw new RuntimeException("JDBC Driver not found", e);
        }
    }
}