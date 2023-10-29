package Boundary;

import Controller.CreateUserAccountController;
import Controller.UpdateUserAccountController;
import Controller.UpdateUserProfileController;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.List;

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

        JTextField passwordField = new JTextField(u.getPassword());
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
        List<String> profileList = new UpdateUserAccountController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
        profileComboBox.setSelectedIndex(u.getProfile()-1);
        profileComboBox.setBounds(200, 350, 235,50);
        panel.add(profileComboBox);

        // Role label
        JLabel roleLabel = new JLabel("Role");
        roleLabel.setBounds(100, 400, 235, 50);
        panel.add(roleLabel);

        // Role dropdown
        List<String> roleList = new UpdateUserAccountController().getRoleList();
        DefaultComboBoxModel<String> roleComboModel = new DefaultComboBoxModel<>(roleList.toArray(new String[0]));
        JComboBox<String> roleComboBox = new JComboBox<>(roleComboModel);
        roleComboBox.setSelectedIndex(u.getRole()-1);
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
            int profile = profileComboBox.getSelectedIndex();
            int role = roleComboBox.getSelectedIndex();
            UserAccount updatedUser = new UserAccount(username, password, firstName, lastName, email, profile+1, role+1, 0, true);
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
