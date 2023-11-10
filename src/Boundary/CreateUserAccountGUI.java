package Boundary;

import Controller.CreateUserAccountController;
import Entity.*;

import javax.swing.*;
import java.util.ArrayList;

public class CreateUserAccountGUI {

    public CreateUserAccountGUI(UserAccount u) {
        displayCreateAccount(u);
    }

    public void displayCreateAccount(UserAccount u) {
        JFrame frame = new JFrame("Create Account");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel createAccountLabel = new JLabel("Create Account");
        createAccountLabel.setBounds(300, 30,235, 50);
        panel.add(createAccountLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 100, 235, 50);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 100, 235, 50);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 150, 235, 50);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 150, 235, 50);
        panel.add(passwordField);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(100, 200, 235, 50);
        panel.add(firstNameLabel);

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(200, 200, 235, 50);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(100, 250, 235, 50);
        panel.add(lastNameLabel);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(200, 250, 235, 50);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 300, 235, 50);
        panel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(200, 300, 235, 50);
        panel.add(emailField);

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setBounds(100, 350, 235, 50);
        panel.add(profileLabel);

        ArrayList<String> profileList = new CreateUserAccountController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
        profileComboBox.setBounds(200, 350, 235,50);
        panel.add(profileComboBox);

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
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int profile = profileComboBox.getSelectedIndex() + 1;
            UserAccount newUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), true);
            if(username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please don't leave any empty field", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new CreateUserAccountController().addAccount(newUser)) {
                JOptionPane.showMessageDialog(frame, "User account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SystemAdminGUI(u);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to create user account", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

//
//package Boundary;
//
//        import Controller.CreateUserAccountController;
//        import Entity.*;
//
//        import javax.swing.*;
//        import java.util.ArrayList;
//
//public class CreateUserAccountGUI {
//
//    public CreateUserAccountGUI(UserAccount u) {
//        displayCreateAccount(u);
//    }
//
//    public void displayCreateAccount(UserAccount u) {
//        JFrame frame = new JFrame("Create Account");
//        JPanel panel = new JPanel();
//        panel.setLayout(null);
//
//        JLabel createAccountLabel = new JLabel("Create Account");
//        createAccountLabel.setBounds(300, 30,235, 50);
//        panel.add(createAccountLabel);
//
//        JLabel usernameLabel = new JLabel("Username");
//        usernameLabel.setBounds(100, 100, 235, 50);
//        panel.add(usernameLabel);
//
//        JTextField usernameField = new JTextField();
//        usernameField.setBounds(200, 100, 235, 50);
//        panel.add(usernameField);
//
//        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setBounds(100, 150, 235, 50);
//        panel.add(passwordLabel);
//
//        JPasswordField passwordField = new JPasswordField();
//        passwordField.setBounds(200, 150, 235, 50);
//        panel.add(passwordField);
//
//        JLabel firstNameLabel = new JLabel("First Name");
//        firstNameLabel.setBounds(100, 200, 235, 50);
//        panel.add(firstNameLabel);
//
//        JTextField firstNameField = new JTextField();
//        firstNameField.setBounds(200, 200, 235, 50);
//        panel.add(firstNameField);
//
//        JLabel lastNameLabel = new JLabel("Last Name");
//        lastNameLabel.setBounds(100, 250, 235, 50);
//        panel.add(lastNameLabel);
//
//        JTextField lastNameField = new JTextField();
//        lastNameField.setBounds(200, 250, 235, 50);
//        panel.add(lastNameField);
//
//        JLabel emailLabel = new JLabel("Email");
//        emailLabel.setBounds(100, 300, 235, 50);
//        panel.add(emailLabel);
//
//        JTextField emailField = new JTextField();
//        emailField.setBounds(200, 300, 235, 50);
//        panel.add(emailField);
//
//        JLabel profileLabel = new JLabel("Profile");
//        profileLabel.setBounds(100, 350, 235, 50);
//        panel.add(profileLabel);
//
//        ArrayList<String> profileList = new CreateUserAccountController().getProfileList();
//        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
//        JComboBox<String> profileComboBox = new JComboBox<>(profileComboModel);
//        profileComboBox.setBounds(200, 350, 235,50);
//        panel.add(profileComboBox);
//
//        JLabel roleLabel = new JLabel("Role");
//        roleLabel.setBounds(100, 400, 235, 50);
//        panel.add(roleLabel);
//
//        ArrayList<String> roleList = new CreateUserAccountController().getRoleList();
//        DefaultComboBoxModel<String> roleComboModel = new DefaultComboBoxModel<>(roleList.toArray(new String[0]));
//        JComboBox<String> roleComboBox = new JComboBox<>(roleComboModel);
//        roleComboBox.setBounds(200, 400, 235, 50);
//        panel.add(roleComboBox);
//        roleComboBox.setEnabled(false);
//
//        profileComboBox.addItemListener(e -> roleComboBox.setEnabled(profileComboBox.getSelectedIndex() == 3));
//
//        JButton backButton = new JButton("Back");
//        backButton.setBounds(100, 500, 235, 30);
//        panel.add(backButton);
//
//        JButton createButton = new JButton("Create");
//        createButton.setBounds(500, 500, 235, 30);
//        panel.add(createButton);
//
//        frame.add(panel);
//        frame.setSize(800, 600);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//        backButton.addActionListener(e -> {
//            frame.dispose();
//            new SystemAdminGUI(u);
//        });
//
//        createButton.addActionListener(e -> {
//            String username = usernameField.getText();
//            String password = passwordField.getText();
//            String firstName = firstNameField.getText();
//            String lastName = lastNameField.getText();
//            String email = emailField.getText();
//            int profile = profileComboBox.getSelectedIndex() + 1;
//            int role = roleComboBox.getSelectedIndex() + 1;
//            UserAccount newUser;
//            if(profile == 1) {
//                newUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), true);
//            } else if(profile == 2) {
//                newUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), true);
//            } else if(profile == 3) {
//                newUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), true);
//            } else if(profile == 4) {
//                newUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), true, role, 0);
//            } else {
//                newUser = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profile), true);
//            }
//            if(username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
//                JOptionPane.showMessageDialog(frame, "Please don't leave any empty field", "Error", JOptionPane.ERROR_MESSAGE);
//            } else if (new CreateUserAccountController().addAccount(newUser)) {
//                JOptionPane.showMessageDialog(frame, "User account created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//                frame.dispose();
//                new SystemAdminGUI(u);
//            } else {
//                JOptionPane.showMessageDialog(frame, "Failed to create user account", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }
//}