package Controller;

import javax.swing.JOptionPane;
import Boundary.LoginUI;
import Entity.Login;

public class LoginControl {
    private Login login;
    private LoginUI loginUI;

    public LoginControl(LoginUI loginUI) {
        this.loginUI = loginUI;
        this.login = new Login();
    }

    public void processLogin(String username, String password) {
        String accountType = login.authenticate(username, password);
        if (accountType != null) {
            loginSuccessful(accountType);
        } else {
            JOptionPane.showMessageDialog(loginUI, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loginSuccessful(String accountType) {
        System.out.println("Login successful! Redirecting to appropriate page.");
        loginUI.dispose(); // Close the LoginUI window

        if ("System Admin".equals(accountType)) {
            new Boundary.SysAdminUI();
        } else if ("Real Estate Agent".equals(accountType)) {
            // new AgentUI();
            new Boundary.HomeUI();
        } else if ("Buyer".equals(accountType)) {
            // new BuyerUI();
            new Boundary.HomeUI();
        } else if ("Seller".equals(accountType)) {
            // new SellerUI();
            new Boundary.HomeUI();
        }
    }
}
