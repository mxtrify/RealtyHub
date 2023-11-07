package Boundary;

import Controller.UpdateUserAccountController;
import Entity.*;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateUserAccountGUI {
    public UpdateUserAccountGUI(UserAccount u) {
        displayUpdateUserAccount(u);
    }

    public void displayUpdateUserAccount(UserAccount u) {
        JFrame frame = new JFrame("Edit Account");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit Account");
        titleLabel.setBounds(300, 30,235, 50);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 100, 235, 50);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField(u.getUsername());
        usernameField.setBounds(200, 100, 235, 50);
        usernameField.setEnabled(false);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 150, 235, 50);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(u.getPassword());
        passwordField.setBounds(200, 150, 235, 50);
        panel.add(passwordField);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(100, 200, 235, 50);
        panel.add(firstNameLabel);

        JTextField firstNameField = new JTextField(u.getFirstName());
        firstNameField.setBounds(200, 200, 235, 50);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(100, 250, 235, 50);
        panel.add(lastNameLabel);

        JTextField lastNameField = new JTextField(u.getLastName());
        lastNameField.setBounds(200, 250, 235, 50);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 300, 235, 50);
        panel.add(emailLabel);

        JTextField emailField = new JTextField(u.getEmail());
        emailField.setBounds(200, 300, 235, 50);
        panel.add(emailField);

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setBounds(100, 350, 235, 50);
        panel.add(profileLabel);

        // Profile dropdown
        ArrayList<String> profileList = new UpdateUserAccountController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
        profileComboBox.setSelectedIndex(u.getUserProfile().getProfileID()-1);
        profileComboBox.setBounds(200, 350, 235,50);
        panel.add(profileComboBox);

        // Role label
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setBounds(100, 400, 235, 50);
        panel.add(roleLabel);

        // Role dropdown
        ArrayList<String> roleList = new UpdateUserAccountController().getRoleList();
        DefaultComboBoxModel<String> roleComboModel = new DefaultComboBoxModel<>(roleList.toArray(new String[0]));
        JComboBox<String> roleComboBox = new JComboBox<>(roleComboModel);
        if(u.getUserProfile().getProfileID() == 4) {
            roleComboBox.setSelectedIndex(u.getRole_id()-1);
        }
        roleComboBox.setBounds(200, 400, 235, 50);
        roleComboBox.setEnabled(profileComboBox.getSelectedIndex() == 3);
        panel.add(roleComboBox);

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

        profileComboBox.addItemListener(e -> roleComboBox.setEnabled(profileComboBox.getSelectedIndex() == 3));

        backButton.addActionListener(e -> {
            frame.dispose();
            new SystemAdminGUI(u);
        });

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int profile = profileComboBox.getSelectedIndex() + 1;
            int role = roleComboBox.getSelectedIndex() + 1;
            UserAccount updatedUser;
            if(profile == 1) {
                updatedUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile));
            } else if(profile == 2) {
                updatedUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile));
            } else if(profile == 3) {
                updatedUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile));
            } else if(profile == 4) {
                updatedUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), role);
            } else {
                updatedUser = new UserAccount();
            }
            if(password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please don't leave any empty field", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new UpdateUserAccountController().UpdateUserAccount(updatedUser)) {
                JOptionPane.showMessageDialog(frame, "User account successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SystemAdminGUI(u);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save user account", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
