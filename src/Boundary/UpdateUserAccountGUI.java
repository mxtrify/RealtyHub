package Boundary;

import Controller.CreateUserAccountController;
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

        List<String> profileList = new CreateUserAccountController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
        profileComboBox.setSelectedIndex(u.getProfile()-1);
        profileComboBox.setBounds(200, 350, 235,50);
        panel.add(profileComboBox);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setBounds(100, 400, 235, 50);
        panel.add(roleLabel);

        List<String> roleList = new CreateUserAccountController().getRoleList();
        DefaultComboBoxModel<String> roleComboModel = new DefaultComboBoxModel<>(roleList.toArray(new String[0]));
        JComboBox<String> roleComboBox = new JComboBox<>(roleComboModel);
        roleComboBox.setSelectedIndex(u.getRole()-1);
        roleComboBox.setBounds(200, 400, 235, 50);
        panel.add(roleComboBox);
        roleComboBox.setEnabled(false);

        profileComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                roleComboBox.setEnabled(profileComboBox.getSelectedIndex() == 3);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        JButton createButton = new JButton("Create");
        createButton.setBounds(500, 500, 235, 30);
        panel.add(createButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new SystemAdminGUI(u);
        });

        createButton.addActionListener(e -> {
            frame.dispose();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int profile = profileComboBox.getSelectedIndex();
            int role = roleComboBox.getSelectedIndex();
            int maxSlot = 0;
            UserAccount newUser = new UserAccount(username, password, firstName, lastName, email, profile+1, role+1, maxSlot, true);
            new CreateUserAccountController().addAccount(newUser);
            new SystemAdminGUI(u);
        });
    }
}
