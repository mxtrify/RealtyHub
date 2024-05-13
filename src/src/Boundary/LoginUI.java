package Boundary;

import Controller.LoginControl;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private LoginControl loginControl;
    private boolean errorMessageDisplayed;  // To keep track of error messages

    // Initializes the login control and sets up the UI components
    public LoginUI() {
        loginControl = new LoginControl();  // Corrected constructor
        setupUI();
    }

    // Sets up the main UI components of the login window
    private void setupUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        addHeader(panel, constraints);
        addLoginForm(panel, constraints);
        add(panel);
        setVisible(true);
    }

    // Adds a header to the panel
    private void addHeader(JPanel panel, GridBagConstraints constraints) {
        JLabel headerLabel = new JLabel("Welcome to RealtyHub!");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(headerLabel, constraints);
        constraints.gridwidth = 1;
    }

    // Adds login form components to the panel
    private void addLoginForm(JPanel panel, GridBagConstraints constraints) {
        constraints.insets = new Insets(10, 0, 10, 10);

        // Add username label and field
        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(usernameLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(usernameField, constraints);

        // Add password label and field
        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        // Create an ActionListener for login action
        ActionListener loginAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        };

        // Add login button and set its action
        loginButton = new JButton("Login");
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(loginButton, constraints);
        loginButton.addActionListener(loginAction);

        // Set the same action for the password field to handle "Enter" key press
        passwordField.addActionListener(loginAction);
    }

    // Handles login logic and navigation based on user authentication and status
    public void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        UserAccount userAccount = loginControl.processLogin(username, password);

        if (userAccount == null) {
            JOptionPane.showMessageDialog(LoginUI.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            errorMessageDisplayed = true;
        } else if (!userAccount.getUserProfile().isProfileStatus()) {
            JOptionPane.showMessageDialog(LoginUI.this, "This profile is suspended", "Login Error", JOptionPane.ERROR_MESSAGE);
            errorMessageDisplayed = true;
        } else if (!userAccount.isStatus()) {
            JOptionPane.showMessageDialog(LoginUI.this, "This account is suspended", "Login Error", JOptionPane.ERROR_MESSAGE);
            errorMessageDisplayed = true;
        } else {
            dispose();
            openGUI(userAccount);
        }
    }

    // Determines the appropriate GUI to open based on the user's profile type
    private void openGUI(UserAccount userAccount) {
        switch (userAccount.getUserProfile().getProfileType()) {
            case "System Admin":
                new SysAdminUI(userAccount);
                break;
            case "Real Estate Agent":
                //new AgentUI(userAccount);
                System.out.println("Agent");
                break;
            case "Buyer":
                new BuyerUI(userAccount);
                System.out.println("Buyer");
                break;
            case "Seller":
                //new SellerUI(userAccount);
                System.out.println("Seller");
                break;
            default:
                //new OtherProfileUI(userAccount);
                System.out.println("Others");
                break;
        }
    }

    // Main method to launch the login UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });
    }
}

