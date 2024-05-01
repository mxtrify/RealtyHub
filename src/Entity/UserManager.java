package Entity;

import Database.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
    public String authenticate(String username, String password) {
        String query = "SELECT account_type FROM user WHERE username=? AND password=?";
        try (Connection connection = DBConn.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("account_type");  // Return the account type
                } else {
                    return null;  // Return null if no such user exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
