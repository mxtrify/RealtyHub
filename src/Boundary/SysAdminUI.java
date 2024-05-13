package Boundary;

import Controller.SysAdminControl;
import Controller.UserProfile.*;
import Controller.UserAccount.*;

import Entity.UserAccount;
import Entity.UserProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SysAdminUI extends JFrame {
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private JPanel userProfilePanel;
    private JPanel userAccountPanel;

    private SysAdminControl control;

    private DefaultTableModel profileModel;
    private JTextField searchProfile;
    private ArrayList<UserProfile> userProfiles;
    private String[] profileColumnNames = {"Profile Type", "Information", "Status"};
    private DefaultTableModel accountModel;
    private JTextField searchAccount;
    private ArrayList<UserAccount> userAccounts;
    private String[] accountColumnNames = {"Username", "First Name", "Last Name", "Profile", "Status"};

    public SysAdminUI(UserAccount u) {
        control = new SysAdminControl(u);
        initializeUI(u);
    }

    private void initializeUI(UserAccount u) {
        setTitle("System Administrator Console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        landingPanel = createLandingPanel();
        userProfilePanel = userProfilePanel(u);
        userAccountPanel = userAccountPanel(u);

        tabbedPane.addTab("Welcome", landingPanel);
        tabbedPane.addTab("User Profiles", userProfilePanel);
        tabbedPane.addTab("User Accounts", userAccountPanel);

        add(tabbedPane);
        setVisible(true);
    }

    // SysAdmin Landing Panel
    private JPanel createLandingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Container panel to hold multiple labels and the logout button
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Display welcome message for the logged-in user using controller method
        JLabel titleLabel = new JLabel("Welcome, " + control.getLoggedInUserFullName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // Test label below the welcome message
        JLabel testLabel = new JLabel("Click on a tab to start!", JLabel.CENTER);
        testLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        // Add labels to the container panel with constraints
        containerPanel.add(titleLabel, gbc);
        containerPanel.add(testLabel, gbc);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH); // Add the logout button panel to the bottom right corner

        // Add container panel to the main panel
        panel.add(containerPanel, BorderLayout.CENTER);

        return panel;
    }

    // SysAdmin User Profile Panel
    private JPanel userProfilePanel(UserAccount u) {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchProfile here if not initialized in initializeUI
        searchProfile = new JTextField(20); // Ensure it is instantiated here
        searchProfile.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchProfile.getPreferredSize().height));
        searchProfile.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchUserProfile();
                }
            }
        });

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("User Profile Management", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Button Panel configured with BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Panel for Create, Edit, Suspend buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createProfileButton = new JButton("Create Profile");
        JButton editButton = new JButton("Edit");
        JButton suspendButton = new JButton("Suspend");
        actionButtonPanel.add(createProfileButton);
        actionButtonPanel.add(editButton);
        actionButtonPanel.add(suspendButton);

        // Search and Buttons Panel
        JPanel searchAndButtonsPanel = new JPanel();
        searchAndButtonsPanel.setLayout(new BoxLayout(searchAndButtonsPanel, BoxLayout.LINE_AXIS));
        searchAndButtonsPanel.add(searchProfile);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchUserProfile());
        searchAndButtonsPanel.add(searchButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchProfile.setText("");
            searchUserProfile();
        });
        searchAndButtonsPanel.add(clearButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));

        // Add panels to buttonPanel
        buttonPanel.add(actionButtonPanel, BorderLayout.WEST);
        buttonPanel.add(searchAndButtonsPanel, BorderLayout.EAST);

        // Combine header and buttons in a single panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(combinedPanel, BorderLayout.NORTH);

        // Create a table profileModel with non-editable cells
        profileModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        profileModel.setColumnIdentifiers(profileColumnNames);
        getProfileList();

        // Create the table using the extracted method
        JTable table = createProfileTable(profileModel);
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        // Listener to open Create User Profile UI
        createProfileButton.addActionListener(e -> {
            dispose();
            new CreateUserProfileUI(u);
        });

        // Listener to open Edit User Profile UI
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(userProfilePanel, "Please select a profile to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                String profileType = profileModel.getValueAt(table.getSelectedRow(),0).toString();
                String profileInfo = profileModel.getValueAt(table.getSelectedRow(),1).toString();
                new UpdateUserProfileUI(u, profileType, profileInfo);
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    suspendButton.setEnabled(true);
                    String status = profileModel.getValueAt(table.getSelectedRow(), 2).toString();
                    if (status.equals("Active")) {
                        suspendButton.setText("Suspend");
                    } else {
                        suspendButton.setText("Activate");
                    }
                }
            }
        });

        suspendButton.addActionListener(e -> {
            String status = profileModel.getValueAt(table.getSelectedRow(), 2).toString();
            String profileName = profileModel.getValueAt(table.getSelectedRow(),0).toString();
            if(!profileName.equals("System Admin")) {
                if(status.equals("Active")) {
                    if(new SuspendUserProfileControl().suspendUserProfile(profileName)) {
                        getProfileList();
                        JOptionPane.showMessageDialog(userProfilePanel, "Successfully suspended profile", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(userProfilePanel, "Failed to suspend profile", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(new ActivateUserProfileControl().activateUserProfile(profileName)) {
                        getProfileList();
                        JOptionPane.showMessageDialog(userProfilePanel, "Successfully activated profile", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(userProfilePanel, "Failed to activate profile", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(userProfilePanel, "Can't suspend System Admin", "Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    // User Profile Methods
    private JTable createProfileTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getProfileList() {
        profileModel.setRowCount(0);
        userProfiles = new ViewUserProfileControl().getProfileList();
        for (UserProfile userProfile : userProfiles) {
            String status;
            if(userProfile.isProfileStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            profileModel.addRow(new Object[]{
                    userProfile.getProfileType(),
                    userProfile.getProfileInfo(),
                    status
            });
        }
    }

    public void searchUserProfile() {
        String search = searchProfile.getText();

        if (search.isEmpty()) {
            getProfileList();
        } else {
            profileModel.setRowCount(0);
            userProfiles = new SearchUserProfileControl().SearchUserProfile(search);
            for (UserProfile userProfile : userProfiles) {
                String status;
                if (userProfile.isProfileStatus()) {
                    status = "Active";
                } else {
                    status = "Suspended";
                }
                profileModel.addRow(new Object[]{
                        userProfile.getProfileType(),
                        userProfile.getProfileInfo(),
                        status
                });
            }
        }
    }

    // SysAdmin User Account Panel
    private JPanel userAccountPanel(UserAccount u) {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchAccount here if not initialized in initializeUI
        searchAccount = new JTextField(20); // Ensure it is instantiated here
        searchAccount.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchAccount.getPreferredSize().height));
        searchAccount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchUserAccount();
                }
            }
        });

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("User Account Management", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Button Panel configured with BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Panel for Create, Edit, Suspend buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createAccountButton = new JButton("Create Account");
        JButton editButton = new JButton("Edit");
        JButton suspendButton = new JButton("Suspend");
        actionButtonPanel.add(createAccountButton);
        actionButtonPanel.add(editButton);
        actionButtonPanel.add(suspendButton);

        // Search and Buttons Panel
        JPanel searchAndButtonsPanel = new JPanel();
        searchAndButtonsPanel.setLayout(new BoxLayout(searchAndButtonsPanel, BoxLayout.LINE_AXIS));
        searchAndButtonsPanel.add(searchAccount);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchUserAccount());
        searchAndButtonsPanel.add(searchButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));

        // Profile list dropdown
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(getProfileTypeList().toArray(new String[0]));
        JComboBox<String> profileFilter = new JComboBox<>(profileComboModel);
        profileFilter.setFont(new Font("Arial", Font.PLAIN, 15));
        Dimension buttonSize = searchButton.getPreferredSize();
        profileFilter.setPreferredSize(new Dimension(profileFilter.getPreferredSize().width, buttonSize.height));
        searchAndButtonsPanel.add(profileFilter);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchAccount.setText("");
            searchUserAccount();
        });
        searchAndButtonsPanel.add(clearButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));

        // Add panels to buttonPanel
        buttonPanel.add(actionButtonPanel, BorderLayout.WEST);
        buttonPanel.add(searchAndButtonsPanel, BorderLayout.EAST);

        // Combine header and buttons in a single panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(combinedPanel, BorderLayout.NORTH);

        // Create a table accountModel with non-editable cells
        accountModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        accountModel.setColumnIdentifiers(accountColumnNames);
        getAccountList();

        // Create the table using the extracted method
        JTable table = createAccountTable(accountModel);
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        // Listener for Profile Filter
        profileFilter.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                profileFilter((String) profileFilter.getSelectedItem());
            }
        });

        // Listener to open Create User Account UI
        createAccountButton.addActionListener(e -> {
            dispose();
            new CreateUserAccountUI(u);
        });

        // Listener to open Edit User Account UI
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(userAccountPanel, "Please select an account to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                String username = accountModel.getValueAt(table.getSelectedRow(),0).toString();
                UserAccount userAccount = new UpdateUserAccountControl().getSelectedAccount(username);
                if (userAccount.getUsername().equals(u.getUsername())){
                    JOptionPane.showMessageDialog(userAccountPanel, "Please select other users than yourself", "Failed", JOptionPane.ERROR_MESSAGE);
                }else {
                    dispose();
                    new UpdateUserAccountUI(u, userAccount);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    suspendButton.setEnabled(true);
                    String status = accountModel.getValueAt(table.getSelectedRow(), 4).toString();
                    if (status.equals("Active")) {
                        suspendButton.setText("Suspend");
                    } else {
                        suspendButton.setText("Activate");
                    }
                }
            }
        });

        suspendButton.addActionListener(e -> {
            String status = accountModel.getValueAt(table.getSelectedRow(), 4).toString();
            String username = accountModel.getValueAt(table.getSelectedRow(),0).toString();
            String profile = accountModel.getValueAt(table.getSelectedRow(),3).toString();
            if(!profile.equals("System Admin")) {
                if(status.equals("Active")) {
                    if(new SuspendUserAccountControl().suspendUserAccount(username)) {
                        getAccountList();
                        JOptionPane.showMessageDialog(userAccountPanel, "Successfully suspended account", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(userAccountPanel, "Failed to suspend account", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(new ActivateUserAccountControl().activateUserAccount(username)) {
                        getAccountList();
                        JOptionPane.showMessageDialog(userProfilePanel, "Successfully activated account", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(userProfilePanel, "Failed to activate profile", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(userAccountPanel, "Can't suspend System Admin", "Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    // User Account Methods
    private JTable createAccountTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public ArrayList<String> getProfileTypeList() {
        return new FilterUserAccountControl().getProfileList();
    }

    public void profileFilter(String profileName) {
        accountModel.setRowCount(0);
        userAccounts = new FilterUserAccountControl().FilterUserAccount(profileName);
        for (UserAccount userAccount : userAccounts) {
            String status;
            if(userAccount.isStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            accountModel.addRow(new Object[]{
                    userAccount.getUsername(),
                    userAccount.getfName(),
                    userAccount.getlName(),
                    userAccount.getUserProfile().getProfileType(),
                    status
            });
        }
    }

    public void getAccountList() {
        accountModel.setRowCount(0);
        userAccounts = new ViewUserAccountControl().getAccountList();
        for (UserAccount userAccount : userAccounts) {
            String status;
            if(userAccount.isStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            accountModel.addRow(new Object[]{
                    userAccount.getUsername(),
                    userAccount.getfName(),
                    userAccount.getlName(),
                    userAccount.getUserProfile().getProfileType(),
                    status
            });
        }
    }

    public void searchUserAccount() {
        String search = searchAccount.getText();

        if(search.isEmpty()) {
            getAccountList();
        } else {
            accountModel.setRowCount(0);
            userAccounts = new SearchUserAccountControl().searchUserAccount(search);
            for (UserAccount userAccount : userAccounts) {
                String status;
                if(userAccount.isStatus()) {
                    status = "Active";
                } else {
                    status = "Suspended";
                }
                accountModel.addRow(new Object[]{
                        userAccount.getUsername(),
                        userAccount.getfName(),
                        userAccount.getlName(),
                        userAccount.getUserProfile().getProfileType(),
                        status
                });
            }
        }
    }

    // Global Methods
    // Create a scroll pane for the table
    private JScrollPane createTableScrollPane(JTable table) {
        return new JScrollPane(table);
    }

    // Logout Procedure
    private void performLogout() {
        dispose();
        new LoginUI();
    }
}