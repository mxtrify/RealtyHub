package Boundary;

import Controller.SysAdminControl;
import Controller.ViewUserProfileControl;
import Controller.SearchUserProfileControl;
import Controller.ActivateUserProfileControl;
import Controller.SuspendUserProfileControl;

import Entity.UserAccount;
import Entity.UserProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SysAdminUI extends JFrame {
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private JPanel userProfilePanel;
    private JPanel userAccPanel;

    private SysAdminControl control;

    private DefaultTableModel model;
    private JTextField searchTextField;
    private ArrayList<UserProfile> userProfiles;
    private String[] columnNames = {"Profile Type", "Information", "Status"};

    public SysAdminUI(UserAccount u) {
        control = new SysAdminControl(u);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("System Administrator Console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        landingPanel = createLandingPanel();
        userProfilePanel = userProfilePanel();
        userAccPanel = userAccPanel();

        tabbedPane.addTab("Welcome", landingPanel);
        tabbedPane.addTab("User Profiles", userProfilePanel);
        tabbedPane.addTab("User Accounts", userAccPanel);

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
    private JPanel userProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchTextField here if not initialized in initializeUI
        searchTextField = new JTextField(20); // Ensure it is instantiated here
        searchTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchTextField.getPreferredSize().height));
        searchTextField.addKeyListener(new KeyAdapter() {
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
        searchAndButtonsPanel.add(searchTextField);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchUserProfile());
        searchAndButtonsPanel.add(searchButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchTextField.setText("");
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

        // Create a table model with non-editable cells
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        model.setColumnIdentifiers(columnNames);
        getProfileList();

        // Create the table using the extracted method
        JTable table = createProfileTable(model);
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
            //new CreateUserProfileUI(u);
        });

        // Listener to open Edit User Profile UI
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(userProfilePanel, "Please select profile to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                String profileType = model.getValueAt(table.getSelectedRow(),0).toString();
                String profileInfo = model.getValueAt(table.getSelectedRow(),1).toString();
                //new UpdateUserProfileUI(u, profileType, profileInfo);
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    suspendButton.setEnabled(true);
                    String status = model.getValueAt(table.getSelectedRow(), 2).toString();
                    if (status.equals("Active")) {
                        suspendButton.setText("Suspend");
                    } else {
                        suspendButton.setText("Activate");
                    }
                }
            }
        });

        suspendButton.addActionListener(e -> {
            String status = model.getValueAt(table.getSelectedRow(), 2).toString();
            String profileName = model.getValueAt(table.getSelectedRow(),0).toString();
            if(!profileName.equals("System Admin")) {
                if(status.equals("Active")) {
                    if(new SuspendUserProfileControl().suspendUserProfile(profileName)) {
                        getProfileList();
                        JOptionPane.showMessageDialog(userProfilePanel, "Successfully suspend profile", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(userProfilePanel, "Failed to suspend profile", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if(new ActivateUserProfileControl().activateUserProfile(profileName)) {
                        getProfileList();
                        JOptionPane.showMessageDialog(userProfilePanel, "Successfully unsuspend profile", "Success", JOptionPane.PLAIN_MESSAGE);
                        suspendButton.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(userProfilePanel, "Failed to unsuspend profile", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(userProfilePanel, "Can't suspend System Admin", "Failed", JOptionPane.ERROR_MESSAGE);
            }

        });
        return panel;
    }

    public void getProfileList() {
        model.setRowCount(0);
        userProfiles = new ViewUserProfileControl().getProfileList();
        for (UserProfile userProfile : userProfiles) {
            String status;
            if(userProfile.isProfileStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            model.addRow(new Object[]{
                    userProfile.getProfileType(),
                    userProfile.getProfileInfo(),
                    status
            });
        }
    }

    public void searchUserProfile() {
        String search = searchTextField.getText();

        if (search.isEmpty()) {
            getProfileList();
        } else {
            model.setRowCount(0);
            userProfiles = new SearchUserProfileControl().SearchUserProfile(search);
            for (UserProfile userProfile : userProfiles) {
                String status;
                if (userProfile.isProfileStatus()) {
                    status = "Active";
                } else {
                    status = "Suspended";
                }
                model.addRow(new Object[]{
                        userProfile.getProfileType(),
                        userProfile.getProfileInfo(),
                        status
                });
            }
        }
    }

    // SysAdmin User Account Panel
    private JPanel userAccPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        return panel;
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

    private JTable createProfileTable(DefaultTableModel model) {
        // Create a table with single selection mode
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    private JScrollPane createTableScrollPane(JTable table) {
        // Create a scroll pane for the table
        return new JScrollPane(table);
    }


    // Logout Procedure
    private void performLogout() {
        dispose();
        new LoginUI();
    }
}