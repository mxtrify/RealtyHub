package Boundary;

import Controller.UserAccount.CreateUserAccountControl;
import Entity.UserAccount;
import Entity.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreateUserAccountUI extends JFrame {
    // Declare Variables
    private JTextField usernameField, firstNameField, lastNameField;
    private JPasswordField passwordField;
    private JComboBox<String> profileComboBox;
    private JButton backButton, createButton;

    public CreateUserAccountUI(UserAccount userAccount) {
        initializeUI(userAccount);
    }

    // Sets up the main UI components of the Create User Account window
    private void initializeUI(UserAccount userAccount) {
        setTitle("Create User Account");
        setSize(1200, 800); // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane(), userAccount);
        setVisible(true);
    }

    // Adds Create User Account Form components to the panel
    private void addComponentsToPane(Container pane, UserAccount userAccount) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Create User Account");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30)); // Updated font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        addFieldAndLabel("Username:", usernameField = new JTextField(20), pane, gbc);
        addFieldAndLabel("Password:", passwordField = new JPasswordField(20), pane, gbc);
        addFieldAndLabel("First Name:", firstNameField = new JTextField(20), pane, gbc);
        addFieldAndLabel("Last Name:", lastNameField = new JTextField(20), pane, gbc);

        // Profile ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel profileLabel = new JLabel("Profile:");
        pane.add(profileLabel, gbc);

        ArrayList<String> profileList = new CreateUserAccountControl().getProfileList();
        profileComboBox = new JComboBox<>(new DefaultComboBoxModel<>(profileList.toArray(new String[0])));
        gbc.gridx = 1;
        pane.add(profileComboBox, gbc);

        // Back and Create Buttons
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        backButton = new JButton("Back");
        createButton = new JButton("Create");

        int buttonWidth = lastNameField.getPreferredSize().width / 2 - 5;
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        createButton.setPreferredSize(new Dimension(buttonWidth, 25));

        buttonPanel.add(backButton);
        buttonPanel.add(createButton);
        pane.add(buttonPanel, gbc);

        setupActionListeners(userAccount);
    }

    private void addFieldAndLabel(String labelText, JComponent field, Container pane, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        pane.add(label, gbc);

        gbc.gridx = 1;
        pane.add(field, gbc);

        gbc.gridy++;
    }

    // Configure action listeners for buttons
    private void setupActionListeners(UserAccount userAccount) {
        backButton.addActionListener(e -> {
            dispose(); // Closes the window
            new SysAdminUI(userAccount); // Assumes SysAdminUI is another JFrame that needs to be shown
        });

        createButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int profileId = profileComboBox.getSelectedIndex() + 1;
            UserAccount newUser = new UserAccount(username, password, firstName, lastName, new UserProfile(profileId));
            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please don't leave any field empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new CreateUserAccountControl().addAccount(newUser)) {
                JOptionPane.showMessageDialog(this, "User account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new SysAdminUI(userAccount);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create user account", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}