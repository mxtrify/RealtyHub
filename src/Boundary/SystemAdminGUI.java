package Boundary;

import Controller.FilterUserAccountController;
import Controller.SearchUserAccountController;
import Controller.ViewUserAccountController;
import Entity.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class SystemAdminGUI {
    private JFrame frame;
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
        viewProfileButton.setBounds(585, 135, 135, 36);
        viewProfileButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(viewProfileButton);

        // Create account button
        JButton createAccountButton = new JButton("+");
        createAccountButton.setBounds(720, 135, 36, 36);
        createAccountButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(createAccountButton);

        // User account table
        DefaultTableModel model = new DefaultTableModel();
        String[] columnNames = {"Username", "First Name", "Last Name", "Profile"};
        model.setColumnIdentifiers(columnNames);
        getAccountList(model);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 700, 350);
        frame.add(scrollPane);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 36);
        logoutButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(logoutButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for logout button
        logoutButton.addActionListener(e -> logout());

        searchButton.addActionListener(e -> searchUserAccount(searchTextField, model));

        viewProfileButton.addActionListener(e -> {
            frame.dispose();
            new ViewUserProfileGUI(u);
        });

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

        profileFilter.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                String profileName = (String) profileFilter.getSelectedItem();
                new FilterUserAccountController().FilterUserAccount(profileName, model);
            }
        });
    }

    public void logout() {
        frame.dispose();
        new LoginGUI();
    }

    public void searchUserAccount(JTextField searchTextField, DefaultTableModel model) {
        String search = searchTextField.getText();

        if(search.isEmpty()) {
            getAccountList(model);
        } else {
            new SearchUserAccountController().searchUserAccount(search, model);
        }
    }

    public List<String> getProfileList() {
        return new ViewUserAccountController().getProfileList();
    }

    public void getAccountList(DefaultTableModel model) {
        new ViewUserAccountController().getAccountList(model);
    }

}
