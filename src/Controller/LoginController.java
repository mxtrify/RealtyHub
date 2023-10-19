package Controller;

import Entity.UserAccount;

public class LoginController {
    // Checking username and password
    public boolean login(UserAccount userAccount) {
        // Return true if match
        return new UserAccount().validateLogin(userAccount);
    }

    // Checking profile
    public int validateProfile(String username) {
        // Return the profile name
        return new UserAccount().validateProfile(username);
    }
}
