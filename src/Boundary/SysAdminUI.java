package Boundary;

import Database.DBConn;
import Controller.SysAdminControl;
import Entity.UserProfile;
import Entity.UserAccount;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SysAdminUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel userProfilePanel;
    private JPanel userAccountPanel;
    private JPanel createUserPanel;
    private SysAdminControl control;

    public SysAdminUI() {
        control = new SysAdminControl();
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

    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel(title, JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(logoutButton);

        headerPanel.add(headerLabel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private void performLogout() {
        // Implement logout procedure, cleanup resources, and exit or return to login screen
        System.exit(0);  // Or another appropriate action
    }

    private JPanel createUserProfilesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel("User Profiles Management"), BorderLayout.NORTH);

        // Table model for user profiles
        String[] columnNames = {"User ID", "Username", "Password"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // User ID is not editable
            }
        };
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton updateButton = new JButton("Update");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(updateButton);
        panel.add(searchPanel, BorderLayout.SOUTH);

        // Load initial data
        loadUserProfiles("", tableModel);

        // Search functionality
        searchButton.addActionListener(e -> loadUserProfiles(searchField.getText(), tableModel));

        // Update functionality
        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String userId = (String) table.getValueAt(row, 0);
                String username = (String) table.getValueAt(row, 1);
                String password = (String) table.getValueAt(row, 2);
                control.updateUserProfile(userId, username, password);
            }
        });

        return panel;
    }

    private void loadUserProfiles(String criteria, DefaultTableModel tableModel) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBConn.getConnection(); // Assuming DBConn provides the connection
            String sql = "SELECT userid, username, password FROM user WHERE username LIKE ? OR userid LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + criteria + "%");
            pstmt.setString(2, "%" + criteria + "%");
            rs = pstmt.executeQuery();

            List<UserProfile> profiles = new ArrayList<>();
            while (rs.next()) {
                String userid = rs.getString("userid");
                String username = rs.getString("username");
                String password = rs.getString("password");
                profiles.add(new UserProfile(userid, username, password));
            }

            // Now populate the table model
            tableModel.setRowCount(0); // Clear existing data
            for (UserProfile profile : profiles) {
                Object[] row = {profile.getUserId(), profile.getUsername(), profile.getPassword()};
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Close resources in the reverse order of their opening
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private JPanel createUserAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel("User Accounts Management"), BorderLayout.NORTH);

        String[] columnNames = {"User ID", "First Name", "Last Name", "Account Type", "Account Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // User ID is not editable
            }
        };
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel searchAndUpdatePanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton updateButton = new JButton("Update");
        searchAndUpdatePanel.add(searchField);
        searchAndUpdatePanel.add(searchButton);
        searchAndUpdatePanel.add(updateButton);
        panel.add(searchAndUpdatePanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> loadUserAccounts(searchField.getText(), tableModel));
        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String userId = (String) table.getValueAt(row, 0);
                String fname = (String) table.getValueAt(row, 1);
                String lname = (String) table.getValueAt(row, 2);
                String accType = (String) table.getValueAt(row, 3);
                String accStatus = (String) table.getValueAt(row, 4);
                // Update logic here
            }
        });

        loadUserAccounts("", tableModel);
        return panel;
    }

    private void loadUserAccounts(String criteria, DefaultTableModel tableModel) {
        String sql = "SELECT userid, fname, lname, accType, accStatus FROM user WHERE fname LIKE ? OR lname LIKE ? OR userid LIKE ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + criteria + "%");
            pstmt.setString(2, "%" + criteria + "%");
            pstmt.setString(3, "%" + criteria + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                List<UserAccount> accounts = new ArrayList<>();
                while (rs.next()) {
                    String userid = rs.getString("userid");
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    String accType = rs.getString("accType");
                    String accStatus = rs.getString("accStatus");
                    accounts.add(new UserAccount(userid, fname, lname, accType, accStatus));
                }

                tableModel.setRowCount(0); // Clear existing data
                if (accounts.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No user accounts found matching criteria.");
                } else {
                    for (UserAccount account : accounts) {
                        Object[] row = {account.getUserId(), account.getFirstName(), account.getLastName(), account.getAccountType(), account.getAccountStatus()};
                        tableModel.addRow(row);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel("Create New User Profile and User Account"), BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

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