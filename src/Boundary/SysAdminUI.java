package Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SysAdminUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel userProfilePanel;
    private JPanel userAccountPanel;
    private JPanel createUserPanel;

    public SysAdminUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("System Administrator Console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        userProfilePanel = createUserProfilesPanel();
        userAccountPanel = createUserAccountsPanel();
        createUserPanel = createUserPanel();

        tabbedPane.addTab("User Profiles", userProfilePanel);
        tabbedPane.addTab("User Accounts", userAccountPanel);
        tabbedPane.addTab("Create New User", createUserPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createUserProfilesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("User Profiles Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(headerLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createUserAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("User Accounts Management", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(headerLabel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Create New User Profile and User Account", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(headerLabel, BorderLayout.NORTH);

        formPanel.add(createUserProfileForm());
        formPanel.add(createUserAccountForm());
        panel.add(formPanel, BorderLayout.CENTER);

        JButton createButton = new JButton("Create User Profile and User Account");
        createButton.addActionListener(this::createUserAction);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createUserProfileForm() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Profile"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField usernameField = new JTextField(10);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(10);
        formPanel.add(passwordField, gbc);

        return formPanel;
    }

    private JPanel createUserAccountForm() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Create New Account"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField firstNameField = new JTextField(10);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField lastNameField = new JTextField(10);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Account Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JComboBox<String> accountTypeBox = new JComboBox<>(new String[]{"System Admin", "Real Estate Agent", "Buyer", "Seller"});
        formPanel.add(accountTypeBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Account Status:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JComboBox<String> accountStatusBox = new JComboBox<>(new String[]{"Active", "Suspended"});
        formPanel.add(accountStatusBox, gbc);

        return formPanel;
    }


    private void createUserAction(ActionEvent e) {
        // Placeholder for creating user profile and account
        JOptionPane.showMessageDialog(this, "Create Profile and Account functionality to be implemented.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SysAdminUI::new);
    }
}