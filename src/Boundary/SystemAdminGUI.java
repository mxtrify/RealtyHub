package Boundary;

import Controller.SystemAdminController;
import Entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SystemAdminGUI {
    // Constructor
    public SystemAdminGUI(UserAccount u) {
        displaySystemAdminGUI(u);
    }

    // Display system admin GUI
    public void displaySystemAdminGUI(UserAccount u) {
        JFrame frame = new JFrame("System Admin");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, " + u.getUsername());
        titleLabel.setBounds(50,75, 500, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(50, 135, 200, 36);
        searchTextField.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchTextField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(250, 135, 100, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        List<String> profileList = new SystemAdminController().getProfileList();
        DefaultComboBoxModel<String> profileComboModel = new DefaultComboBoxModel<>(profileList.toArray(new String[0]));
        JComboBox profileChoice = new JComboBox(profileComboModel);
        profileChoice.setBounds(350, 135, 200, 36);
        profileChoice.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(profileChoice);

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(585, 135, 135, 36);
        viewProfileButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(viewProfileButton);

        JButton createAccountButton = new JButton("+");
        createAccountButton.setBounds(720, 135, 36, 36);
        createAccountButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(createAccountButton);

        DefaultTableModel model = new DefaultTableModel();
        new SystemAdminController().getAccountList(model);
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
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();

        });

        createAccountButton.addActionListener(e -> {
            frame.dispose();
            new CreateAccountGUI(u);
        });
    }
}
