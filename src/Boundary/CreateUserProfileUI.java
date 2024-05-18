package Boundary;

import Controller.CreateUserProfileControl;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;

public class CreateUserProfileUI {
    private JFrame frame;
    public CreateUserProfileUI(UserAccount u) {
        displayCreateUserProfile(u);
    }

    private void displayCreateUserProfile(UserAccount u) {
        frame = new JFrame("Create User Profile");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Create User Profile");
        titleLabel.setBounds(230, 30,350, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        JLabel profileNameLabel = new JLabel("Profile Name");
        profileNameLabel.setBounds(215, 125, 235, 50);
        panel.add(profileNameLabel);

        JTextField profileNameField = new JTextField();
        profileNameField.setBounds(315, 125, 235, 50);
        panel.add(profileNameField);

        JLabel descLabel = new JLabel("Description");
        descLabel.setBounds(215, 185, 235, 50);
        panel.add(descLabel);

        JTextArea profileDescArea = new JTextArea();
        profileDescArea.setBounds(315, 185, 235, 75);
        profileDescArea.setLineWrap(true);
        profileDescArea.setWrapStyleWord(true);
        panel.add(profileDescArea);

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
            new SysAdminUI(u);
        });

        createButton.addActionListener(e -> {
            String profileName = profileNameField.getText();
            String profileDesc = profileDescArea.getText();
            if(profileName.isEmpty() || profileDesc.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name or Description cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new CreateUserProfileControl().createUserProfile(profileName, profileDesc)) {
                JOptionPane.showMessageDialog(frame, "User profile created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new SysAdminUI(u);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to create user profile", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
