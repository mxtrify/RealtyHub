package Boundary;

import Controller.UpdateUserProfileController;
import Entity.UserAccount;

import javax.swing.*;

public class UpdateUserProfileGUI {
    public UpdateUserProfileGUI(UserAccount u, String profileName, String profileDesc) {
        displayUpdateUserProfile(u, profileName, profileDesc);
    }

    public void displayUpdateUserProfile(UserAccount u, String profileName, String profileDesc) {
        JFrame frame = new JFrame("Edit User Profile");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Edit User Profile");
        titleLabel.setBounds(300, 30,235, 50);
        panel.add(titleLabel);

        JLabel profileNameLabel = new JLabel("Profile Name");
        profileNameLabel.setBounds(100, 100, 235, 50);
        panel.add(profileNameLabel);

        JTextField profileNameField = new JTextField(profileName);
        profileNameField.setEnabled(false);
        profileNameField.setBounds(200, 100, 235, 50);
        panel.add(profileNameField);

        JLabel descLabel = new JLabel("Description");
        descLabel.setBounds(100, 150, 235, 50);
        panel.add(descLabel);

        JTextArea profileDescArea = new JTextArea(profileDesc);
        profileDescArea.setBounds(200, 150, 235, 50);
        profileDescArea.setLineWrap(true);
        profileDescArea.setWrapStyleWord(true);
        panel.add(profileDescArea);

        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(500, 500, 235, 30);
        panel.add(saveButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new ViewUserProfileGUI(u);
        });

        saveButton.addActionListener(e -> {
            String newProfileDesc = profileDescArea.getText();
            if(newProfileDesc.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Description cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new UpdateUserProfileController().UpdateUserProfile(profileName, newProfileDesc)) {
                JOptionPane.showMessageDialog(frame, "User profile successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new ViewUserProfileGUI(u);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to save user profile", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
    }
}
