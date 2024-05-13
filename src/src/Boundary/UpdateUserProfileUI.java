package Boundary;

import Controller.UpdateUserProfileController;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;

public class UpdateUserProfileUI extends JFrame {
    private JTextField profileTypeField;
    private JTextArea profileInfoArea;
    private JButton backButton, saveButton;

    public UpdateUserProfileUI(UserAccount userAccount, String profileType, String profileInfo) {
        initializeUI(userAccount, profileType, profileInfo);
    }

    private void initializeUI(UserAccount userAccount, String profileType, String profileInfo) {
        setTitle("Update User Profile");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane(), userAccount, profileType, profileInfo);
        setVisible(true);
    }

    private void addComponentsToPane(Container pane, UserAccount userAccount, String profileType, String profileInfo) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Update User Profile");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(pane, titleLabel, 0, 0, 2, 1, GridBagConstraints.CENTER);

        JLabel profileTypeLabel = new JLabel("Profile Name:");
        profileTypeField = new JTextField(profileType, 20);
        profileTypeField.setEnabled(false);
        addComponent(pane, profileTypeLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
        addComponent(pane, profileTypeField, 1, 1, 1, 1, GridBagConstraints.EAST);

        JLabel infoLabel = new JLabel("Profile Information:");
        profileInfoArea = new JTextArea(profileInfo, 5, 20);
        profileInfoArea.setLineWrap(true);
        profileInfoArea.setWrapStyleWord(true);
        addComponent(pane, infoLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
        addComponent(pane, new JScrollPane(profileInfoArea), 1, 2, 1, 1, GridBagConstraints.EAST);

        backButton = new JButton("Back");
        saveButton = new JButton("Save");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        addComponent(pane, buttonPanel, 1, 3, 1, 1, GridBagConstraints.EAST);

        setupActionListeners(userAccount);
    }

    private void addComponent(Container container, Component component, int x, int y, int width, int height, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = anchor;
        gbc.insets = new Insets(10, 10, 10, 10);
        container.add(component, gbc);
    }

    private void setupActionListeners(UserAccount userAccount) {
        backButton.addActionListener(e -> {
            dispose();
            new SysAdminUI(userAccount);
        });

        saveButton.addActionListener(e -> {
            String newProfileType = profileTypeField.getText();
            String newProfileInfo = profileInfoArea.getText();
            if (newProfileInfo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean updateSuccessful = new UpdateUserProfileController().UpdateUserProfile(newProfileType, newProfileInfo);
                if (updateSuccessful) {
                    JOptionPane.showMessageDialog(this, "User profile successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new SysAdminUI(userAccount);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save user profile", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}