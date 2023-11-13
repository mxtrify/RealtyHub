package Boundary;

import Controller.UpdateUserAccountController;
import Entity.*;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateUserAccountGUI {
    public UpdateUserAccountGUI(UserAccount original, UserAccount selected) {
        displayUpdateUserAccount(original, selected);
    }

    public void displayUpdateUserAccount(UserAccount original, UserAccount selected) {
        JFrame frame = new JFrame("Edit Account");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit Account");
        titleLabel.setBounds(300, 30,235, 50);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 100, 235, 50);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField(selected.getUsername());
        usernameField.setBounds(200, 100, 235, 50);
        usernameField.setEnabled(false);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 150, 235, 50);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(selected.getPassword());
        passwordField.setBounds(200, 150, 235, 50);
        panel.add(passwordField);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(100, 200, 235, 50);
        panel.add(firstNameLabel);

        JTextField firstNameField = new JTextField(selected.getFirstName());
        firstNameField.setBounds(200, 200, 235, 50);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(100, 250, 235, 50);
        panel.add(lastNameLabel);

        JTextField lastNameField = new JTextField(selected.getLastName());
        lastNameField.setBounds(200, 250, 235, 50);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 300, 235, 50);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(selected.getEmail());
        emailField.setBounds(200, 300, 235, 50);
        panel.add(emailField);

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setBounds(100, 350, 235, 50);
        panel.add(profileLabel);

        // Profile dropdown
        ArrayList<String> profileList = new UpdateUserAccountController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
        profileComboBox.setSelectedIndex(selected.getUserProfile().getProfileID()-1);
        profileComboBox.setBounds(200, 350, 235,50);
        panel.add(profileComboBox);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(500, 500, 235, 30);
        panel.add(saveButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new SystemAdminGUI(original);
        });

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int profile = profileComboBox.getSelectedIndex() + 1;
            UserAccount updatedUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile));
            if(password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please don't leave any empty field", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new UpdateUserAccountController().UpdateUserAccount(updatedUser)) {
                JOptionPane.showMessageDialog(frame, "User account successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SystemAdminGUI(original);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save user account", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}