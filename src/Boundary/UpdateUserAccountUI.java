package Boundary;

import Controller.UserAccount.UpdateUserAccountControl;
import Entity.UserAccount;
import Entity.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UpdateUserAccountUI extends JFrame {
    // Declare Variables
    private JTextField usernameField, firstNameField, lastNameField;
    private JPasswordField passwordField;
    private JComboBox<String> profileComboBox;
    private JButton backButton, updateButton;

    public UpdateUserAccountUI(UserAccount original, UserAccount selected ) {
        initializeUI(original, selected);
    }

    // Sets up the main UI components of the Create User Account window
    private void initializeUI(UserAccount original, UserAccount selected) {
        setTitle("Update User Account");
        setSize(1200, 800); // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane(), original, selected);
        setVisible(true);
    }

    // Adds Update User Account Form components to the panel
    private void addComponentsToPane(Container pane, UserAccount original, UserAccount selected) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Update User Account");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30)); // Updated font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        usernameField = new JTextField(selected.getUsername(), 20);
        usernameField.setEnabled(false);  // Disable editing of the username field
        addFieldAndLabel("Username:", usernameField, pane, gbc);
        addFieldAndLabel("Password:", passwordField = new JPasswordField(selected.getPassword(),20), pane, gbc);
        addFieldAndLabel("First Name:", firstNameField = new JTextField(selected.getfName(),20), pane, gbc);
        addFieldAndLabel("Last Name:", lastNameField = new JTextField(selected.getlName(),20), pane, gbc);

        // Profile ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel profileLabel = new JLabel("Profile:");
        pane.add(profileLabel, gbc);

        ArrayList<String> profileList = new UpdateUserAccountControl().getProfileList();
        profileComboBox = new JComboBox<>(new DefaultComboBoxModel<>(profileList.toArray(new String[0])));
        profileComboBox.setSelectedIndex(selected.getUserProfile().getProfileID()-1);
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
        updateButton = new JButton("Update");

        int buttonWidth = lastNameField.getPreferredSize().width / 2 - 5;
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        updateButton.setPreferredSize(new Dimension(buttonWidth, 25));

        buttonPanel.add(backButton);
        buttonPanel.add(updateButton);
        pane.add(buttonPanel, gbc);

        setupActionListeners(original, selected);
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
    private void setupActionListeners(UserAccount original, UserAccount selected) {
        backButton.addActionListener(e -> {
            dispose(); // Closes the window
            new SysAdminUI(original); // Assumes SysAdminUI is another JFrame that needs to be shown
        });

        updateButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int profileId = profileComboBox.getSelectedIndex() + 1;
            UserAccount updatedUser = new UserAccount(username, password, firstName, lastName, new UserProfile(profileId));
            if (password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please don't leave any field empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new UpdateUserAccountControl().UpdateUserAccount(updatedUser)) {
                JOptionPane.showMessageDialog(this, "User account successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new SysAdminUI(original);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update user account", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}