package Controller;

import Entity.UserAccount;

public class LoginController {
    public UserAccount userAccount;

    public LoginController() {
        this.userAccount = new UserAccount();
    }
    // Checking username and password
    public UserAccount login(String username, String password) {
        // Return true if match
        return userAccount.validateLogin(username, password);
    }
}
