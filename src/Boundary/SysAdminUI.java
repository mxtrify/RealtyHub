package Boundary;

import Controller.SysAdminControl;
import Entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SysAdminUI extends JFrame {
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private SysAdminControl control;

    private DefaultTableModel model;
    private JTextField searchTextField;
    private final String[] columnNames = {"Username", "First Name", "Last Name", "Profile", "Status"};
    private String userFullName;

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

        tabbedPane.addTab("Welcome", landingPanel);

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

    private JPanel createLandingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Container panel to hold multiple labels
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Display welcome message for the logged-in user
        JLabel titleLabel = new JLabel("Welcome, " + control.getLoggedInUserFullName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // Test label below the welcome message
        JLabel testLabel = new JLabel("Click on a tab to start!", JLabel.CENTER);
        testLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        // Add labels to the container panel with constraints
        containerPanel.add(titleLabel, gbc);
        containerPanel.add(testLabel, gbc);

        // Add container panel to the main panel
        panel.add(containerPanel, BorderLayout.CENTER);

        return panel;
    }


    // Logout Procedure
    private void performLogout() {
        dispose();
        new LoginUI();
    }
}