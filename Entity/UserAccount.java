import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    // Database url, user and password
    String DB_URL = "jdbc:mysql://localhost:3306/buddies";
    String DB_USER = "root";
    String DB_PASSWORD = "";

    // Function for validating login
    public boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM user_account WHERE username = ? AND password = ?";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if there's a matching record
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function for validating profile
    public String validateProfile(String username) {
        String query = "SELECT profile FROM user_account WHERE username = ?";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) { // Return true if there's a record
                return resultSet.getString("profile"); // Get column "profile" value
            } else {
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}