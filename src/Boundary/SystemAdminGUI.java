package Boundary;

import Entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(100, 68, 150, 25);
        panel.add(searchTextField);

        String[] profileList = {"Default", "System Admin" , "Cafe Owner", "Cafe Manager", "Cafe Staff"};
        JComboBox profileChoice = new JComboBox(profileList);
        profileChoice.setBounds(260, 70, 150, 25);
        panel.add(profileChoice);

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(415, 68, 115, 25);
        panel.add(viewProfileButton);

        JButton createAccountButton = new JButton("+");
        createAccountButton.setBounds(545, 68, 25, 25);
        panel.add(createAccountButton);

        Object[][] data = {
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
                {"a", "1"},
        };
        String[] columnNames = {"Username", "Password"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.setAutoCreateColumnsFromModel(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        model.setColumnIdentifiers(columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100,100, 470, 300);
        frame.add(scrollPane);


        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(475, 20, 100, 25);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setSize(675, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();

        });

        createAccountButton.addActionListener(e -> {

        });
    }
}
