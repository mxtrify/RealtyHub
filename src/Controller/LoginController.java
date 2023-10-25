package Controller;

import Entity.UserAccount;

public class LoginController {
    // Checking username and password
    public UserAccount login(String username, String password) {
        // Return true if match
        return new UserAccount().validateLogin(username, password);
    }
}
