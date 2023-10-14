import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountDAO {
    private Connection conn;

    public UserAccountDAO() {
        try {
            conn = new DBConfig().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Function for validating login
    public boolean validateLogin(UserAccount userAccount) {
        String query = "SELECT * FROM user_account WHERE username = ? AND password = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userAccount.getUsername());
            preparedStatement.setString(2, userAccount.getPassword());
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