package Boundary;

import Controller.CreateUserProfileControl;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;

public class CreateUserProfileUI extends JFrame {
    // Declare Variables
    private JTextField profileTypeField;
    private JTextArea profileInfoArea;
    private JButton backButton, createButton;

    public CreateUserProfileUI(UserAccount userAccount) {
        initializeUI(userAccount);
    }

    // Sets up the main UI components of the Create User Profile window
    private void initializeUI(UserAccount userAccount) {
        setTitle("Create User Profile");
        setSize(1200, 800);  // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane(), userAccount);
        setVisible(true);
    }

    // Adds Create User Profile Form components to the panel
    private void addComponentsToPane(Container pane, UserAccount userAccount) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Create User Profile");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));  // Updated font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Profile Name Label and Text Field
        JLabel profileTypeLabel = new JLabel("Profile Name:");
        gbc.gridx = 0;
        pane.add(profileTypeLabel, gbc);

        profileTypeField = new JTextField(20);
        gbc.gridx = 1;
        pane.add(profileTypeField, gbc);

        // Description Label and Text Area
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel InfoLabel = new JLabel("Profile Information:");
        pane.add(InfoLabel, gbc);

        profileInfoArea = new JTextArea(5, 20);
        profileInfoArea.setLineWrap(true);
        profileInfoArea.setWrapStyleWord(true);
        gbc.gridx = 1;
        pane.add(new JScrollPane(profileInfoArea), gbc);

        // Back and Create Buttons
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        backButton = new JButton("Back");
        createButton = new JButton("Create");

        int buttonWidth = profileInfoArea.getPreferredSize().width / 2 - 5;
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        createButton.setPreferredSize(new Dimension(buttonWidth, 25));

        buttonPanel.add(createButton);
        buttonPanel.add(backButton);
        pane.add(buttonPanel, gbc);

        setupActionListeners(userAccount);
    }

    // Configure action listeners for buttons
    private void setupActionListeners(UserAccount userAccount) {
        backButton.addActionListener(e -> {
            dispose();  // Closes the window
            new SysAdminUI(userAccount);  // Assumes SysAdminUI is another JFrame that needs to be shown
        });

        createButton.addActionListener(e -> {
            String profileName = profileTypeField.getText();
            String profileDesc = profileInfoArea.getText();
            if (profileName.isEmpty() || profileDesc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name or Description cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (new CreateUserProfileControl().createUserProfile(profileName, profileDesc)) {
                JOptionPane.showMessageDialog(this, "User profile created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new SysAdminUI(userAccount);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create user profile", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}