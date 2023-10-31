package Boundary;

import Controller.DeleteUserProfileController;
import Controller.SearchUserProfileController;
import Controller.ViewUserProfileController;
import Entity.UserAccount;
import Entity.UserProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ViewUserProfileGUI {
    private JFrame frame;
    private DefaultTableModel model;
    private ArrayList<UserProfile> userProfiles;
    private String[] columnNames = {"Profile Name", "Description", "Status"};

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
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        getProfileList();
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 525, 350);
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

        // Edit profile button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(600, 200, 100, 36);
        editButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(editButton);

        // Delete profile button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(600, 250, 100, 36);
        deleteButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(deleteButton);

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

        createProfileButton.addActionListener(e -> {
            frame.dispose();
            new CreateUserProfileGUI(u);
        });

        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frame, "Please select profile to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                frame.dispose();
                String profileName = model.getValueAt(table.getSelectedRow(),0).toString();
                String profileDesc = model.getValueAt(table.getSelectedRow(),1).toString();
                new UpdateUserProfileGUI(u, profileName, profileDesc);
            }
        });

        deleteButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frame, "Please select profile to delete", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String profileName = model.getValueAt(table.getSelectedRow(),0).toString();
                if(new DeleteUserProfileController().deleteUserProfile(profileName)) {
                    JOptionPane.showMessageDialog(frame, "Successfully delete profile", "Success", JOptionPane.PLAIN_MESSAGE);
                    model.removeRow(table.getSelectedRow());
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete profile", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void getProfileList() {
        model.setRowCount(0);
        userProfiles = new ViewUserProfileController().getProfileList();
        for (UserProfile userProfile : userProfiles) {
            String status;
            if(userProfile.isProfileStatus()) {
                status = "Active";
            } else {
                status = "Suspended";
            }
            model.addRow(new Object[]{
                    userProfile.getProfileName(),
                    userProfile.getProfileDesc(),
                    status
            });
        }
    }

    public void searchUserProfile(JTextField searchField, DefaultTableModel model) {
        String search = searchField.getText();

        if(search.isEmpty()) {
            getProfileList();
        } else {
            new SearchUserProfileController().SearchUserProfile(search, model);
        }
    }
}
