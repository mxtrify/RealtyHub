package Controller;

import Entity.UserAccount;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

public class LoginController {
    // Checking username and password
    public boolean login(UserAccount userAccount) {
        // Return true if match
        return new UserAccount().validateLogin(userAccount);
    }

    // Checking profile
    public String validateProfile(String username) {
        // Return the profile name
        return new UserAccount().validateProfile(username);
    }
}
