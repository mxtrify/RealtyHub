package Controller;

import Entity.UserAccount;

public class LoginControl {
    public UserAccount userAccount;

    public LoginControl() {
        this.userAccount = new UserAccount();
    }

    // Check username and password
    public UserAccount processLogin(String username, String password) {
        // Return user account object if correct else, return null
        return userAccount.validateLogin(username, password);
    }
}