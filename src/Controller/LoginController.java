import java.sql.SQLException;

public class LoginController {
    // Checking username and password
    public boolean login(String username, String password) {
        UserAccount userAccount = new UserAccount(username, password);
        // Return true if match
        return new UserAccount().validateLogin(userAccount);
    }

    // Checking profile
    public String validateProfile(String username) {
        // Return the profile name
        return new UserAccount().validateProfile(username);
    }
}