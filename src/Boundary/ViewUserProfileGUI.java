package Boundary;

import Controller.SearchUserAccountController;
import Controller.SearchUserProfileController;
import Controller.ViewUserProfileController;
import Entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ViewUserProfileGUI {
    private JFrame frame;

    public ViewUserProfileGUI() {

    }

    public ViewUserProfileGUI(UserAccount u) {
        frame = new JFrame("User Profile");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Profile List");
        titleLabel.setBounds(300, 30,235, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        // View profile button
        JButton viewUserAccountButton = new JButton("Account List");
        viewUserAccountButton.setBounds(585, 135, 135, 36);
        viewUserAccountButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(viewUserAccountButton);

        // Profile table
        DefaultTableModel model = new DefaultTableModel();
        String[] columnNames = {"Profile Name", "Description"};
        model.setColumnIdentifiers(columnNames);
        getProfileList(model);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 700, 350);
        frame.add(scrollPane);

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

        // Create profile button
        JButton createProfileButton = new JButton("+");
        createProfileButton.setBounds(720, 135, 36, 36);
        createProfileButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(createProfileButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        viewUserAccountButton.addActionListener(e -> {
            frame.dispose();
            new SystemAdminGUI(u);
        });

        searchButton.addActionListener(e -> searchUserProfile(searchTextField, model));

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchUserProfile(searchTextField, model);
                }
            }
        });
    }

    public void getProfileList(DefaultTableModel model) {
        new ViewUserProfileController().getProfileList(model);
    }

    public void searchUserProfile(JTextField searchField, DefaultTableModel model) {
        String search = searchField.getText();

        if(search.isEmpty()) {
            getProfileList(model);
        } else {
            new SearchUserProfileController().SearchUserProfile(search, model);
        }
    }
}
