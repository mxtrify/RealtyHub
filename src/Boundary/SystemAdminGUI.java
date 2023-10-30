package Boundary;

import Controller.*;
import Entity.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SystemAdminGUI {
    private JFrame frame;
    private ArrayList<UserAccount> userAccounts;
    // Constructor
    public SystemAdminGUI(UserAccount u) {
        displaySystemAdminGUI(u);
    }

    // Display system admin GUI
    public void displaySystemAdminGUI(UserAccount u) {
        frame = new JFrame("System Admin");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, " + u.getUsername());
        titleLabel.setBounds(50,75, 500, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        // Search Field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(50, 135, 200, 36);
        searchTextField.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchTextField);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(250, 135, 100, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        // Profile list dropdown
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(getProfileList().toArray(new String[0]));
        JComboBox profileFilter = new JComboBox(profileComboModel);
        profileFilter.setBounds(350, 135, 200, 36);
        profileFilter.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(profileFilter);

        // View profile button
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(580, 135, 135, 36);
        viewProfileButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(viewProfileButton);

        // Create account button
        JButton createAccountButton = new JButton("+");
        createAccountButton.setBounds(716, 135, 36, 36);
        createAccountButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(createAccountButton);

        // User account table
        String[] columnNames = {"Username", "First Name", "Last Name", "Profile", "Status"};

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        getAccountList(model);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 500, 350);
        frame.add(scrollPane);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 36);
        logoutButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(logoutButton);

        // Edit profile button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(600, 200, 100, 36);
        editButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(editButton);

        // Delete profile button
        JButton suspendButton = new JButton("Suspend");
        suspendButton.setBounds(600, 250, 100, 36);
        suspendButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(suspendButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for logout button
        logoutButton.addActionListener(e -> logout());

        // Action for search button
        searchButton.addActionListener(e -> searchUserAccount(searchTextField, model));

        // Action for view profile button
        viewProfileButton.addActionListener(e -> {
            frame.dispose();
            new ViewUserProfileGUI(u);
        });

        // Press "ENTER" to search
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchUserAccount(searchTextField, model);
                }
            }
        });

        // Action for create account
        createAccountButton.addActionListener(e -> {
            frame.dispose();
            new CreateUserAccountGUI(u);
        });

        // Action for profile filter
        profileFilter.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                String profileName = (String) profileFilter.getSelectedItem();
                new FilterUserAccountController().FilterUserAccount(profileName, model);
            }
        });

        // Edit account button
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frame, "Please select account to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String username = model.getValueAt(table.getSelectedRow(),0).toString();
                UserAccount userAccount = new UpdateUserAccountController().getSelectedAccount(username);
                frame.dispose();
                new UpdateUserAccountGUI(userAccount);
            }
        });

        // Suspend account button
        suspendButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frame, "Please select account to suspend", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String username = model.getValueAt(table.getSelectedRow(),0).toString();
                if(new SuspendUserAccountController().suspendUserAccount(username)) {
                    getAccountList(model);
                    JOptionPane.showMessageDialog(frame, "Successfully suspend account", "Success", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to suspend account", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Logout function
    public void logout() {
        frame.dispose();
        new LoginGUI();
    }

    // Function for search user account
    public void searchUserAccount(JTextField searchTextField, DefaultTableModel model) {
        String search = searchTextField.getText();

        if(search.isEmpty()) {
            getAccountList(model);
        } else {
            new SearchUserAccountController().searchUserAccount(search, model);
        }
    }

    // Function for profile dropdown
    public List<String> getProfileList() {
        return new ViewUserAccountController().getProfileList();
    }

    // Function for get all user account
    public void getAccountList(DefaultTableModel model) {
        model.setRowCount(0);
        userAccounts = new ViewUserAccountController().getAccountList();
        for (UserAccount userAccount : userAccounts) {
            String status;
            if(userAccount.isStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            model.addRow(new Object[]{
                    userAccount.getUsername(),
                    userAccount.getFirstName(),
                    userAccount.getLastName(),
                    userAccount.getProfileName(),
                    status
            });
        }
    }
}
