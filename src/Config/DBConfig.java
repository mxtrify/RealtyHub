package Config;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// Database Connector
public class DBConfig {
    // Database url, user and password
    String DB_URL = "jdbc:mysql://localhost:3306/realtyhub";
    String DB_USER = "root";
    String DB_PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    /* public byte[] getImageDataFromDatabase(int propertyID) {
        byte[] imageData = null;
        String query = "SELECT Image FROM Properties WHERE PropertyID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, propertyID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    java.sql.Blob blob = resultSet.getBlob("Image");
                    if (blob != null) {
                        imageData = blob.getBytes(1, (int) blob.length());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imageData;
    } */
    public byte[] getImageDataFromDatabase(int propertyID) {
        byte[] imageData = null;
        String query = "SELECT Image FROM Properties WHERE PropertyID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the property ID parameter in the prepared statement
            statement.setInt(1, propertyID);
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if there's a result
                if (resultSet.next()) {
                    // Get the BLOB from the result set
                    Blob blob = resultSet.getBlob("Image");
                    if (blob != null) {
                        // Convert the BLOB to a byte array
                        imageData = blob.getBytes(1, (int) blob.length());
                    }
                }
            }
        } catch (SQLException e) {
            // Properly handle SQL exceptions
            e.printStackTrace(); // or log the exception
        }
        return imageData;
    }
    

}
