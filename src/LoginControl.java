import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginControl {
    private static final String URL = "jdbc:mysql://localhost:3306/RealtyHub";
    private static final String USER = "root";
    private static final String PASSWORD = "CS31iber@11Y";

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if user exists with provided credentials
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void loginSuccessful() {
        System.out.println("Login successful! Redirecting to Home Page.");
        openHomePage();
    }

    private void openHomePage() {
        // Redirect to HomePage.java
        new HomePage();
    }

    public void exit() {
        System.exit(0);
    }
}