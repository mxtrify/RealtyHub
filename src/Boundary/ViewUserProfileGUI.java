package Boundary;

import Controller.SuspendUserProfileController;
import Controller.SearchUserProfileController;
import Controller.UnsuspendUserProfileController;
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
        displayViewUserProfile(u);
    }

    public void displayViewUserProfile(UserAccount u) {
        frame = new JFrame("User Profile");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Profile List");
        titleLabel.setBounds(300, 30,235, 36);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        // View profile button
        JButton viewUserAccountButton = new JButton("View Account");
        viewUserAccountButton.setBounds(585, 135, 135, 36);
        viewUserAccountButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(viewUserAccountButton);

        // Profile table
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
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
        JButton suspendButton = new JButton("Suspend");
        suspendButton.setBounds(590, 250, 120, 36);
        suspendButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(suspendButton);
        suspendButton.setEnabled(false);

        // Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(350, 135, 60, 36);
        clearButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(clearButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        viewUserAccountButton.addActionListener(e -> {
            frame.dispose();
            new SystemAdminGUI(u);
        });

        clearButton.addActionListener(e -> {
            searchTextField.setText("");
            searchUserProfile(searchTextField, model);
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

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    suspendButton.setEnabled(true);
                    String status = model.getValueAt(table.getSelectedRow(), 2).toString();
                    if (status.equals("Active")) {
                        suspendButton.setText("Suspend");
                    } else {
                        suspendButton.setText("Unsuspend");
                    }
                }
            }
        });

        suspendButton.addActionListener(e -> {
            String status = model.getValueAt(table.getSelectedRow(), 2).toString();
            String profileName = model.getValueAt(table.getSelectedRow(),0).toString();
            if(status.equals("Active")) {
                if(new SuspendUserProfileController().suspendUserProfile(profileName)) {
                    getProfileList();
                    JOptionPane.showMessageDialog(frame, "Successfully suspend profile", "Success", JOptionPane.PLAIN_MESSAGE);
                    suspendButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to suspend profile", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if(new UnsuspendUserProfileController().unsuspendUserProfile(profileName)) {
                    getProfileList();
                    JOptionPane.showMessageDialog(frame, "Successfully unsuspend profile", "Success", JOptionPane.PLAIN_MESSAGE);
                    suspendButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to unsuspend profile", "Failed", JOptionPane.ERROR_MESSAGE);
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
