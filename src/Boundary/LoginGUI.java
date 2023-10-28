package Boundary;

import Controller.LoginController;
import Entity.*;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {
    // Variables
    private JFrame frame;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Constructor
    public LoginGUI() {
        displayLogin();
    }

    // Display login function
    public void displayLogin() {
        frame = new JFrame("Login");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Cafe Staff Management System");
        titleLabel.setBounds(100, 20, 500, 25);
        panel.add(titleLabel);

        // Username Label
        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setBounds(75, 75, 100, 25);
        panel.add(usernameLabel);

        // Username Field
        usernameField = new JTextField(20);
        usernameField.setBounds(150, 75, 175, 25);
        panel.add(usernameField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(75, 115, 100, 25);
        panel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 115, 175, 25);
        panel.add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 175, 100, 25);
        panel.add(loginButton);

        frame.setContentPane(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for Login Button
        loginButton.addActionListener(e -> login());
    }

    // Login handling function
    public void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        UserAccount userAccount = new LoginController().login(username, password);

        if (userAccount == null) {
            // Error message for invalid username or password
            JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else {
            frame.dispose();
            // Login based on the profile
            if (userAccount instanceof SystemAdmin) {
                new SystemAdminGUI(userAccount);
            } else if (userAccount instanceof CafeOwner) {
                new CafeOwnerGUI(userAccount);
            } else if (userAccount instanceof CafeManager) {
                new CafeManagerGUI(userAccount);
            } else if (userAccount instanceof CafeStaff) {
                new CafeStaffGUI(userAccount);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI();
            }
        });
    }
}


