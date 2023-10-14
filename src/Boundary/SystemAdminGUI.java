package Boundary;

import javax.swing.*;

public class SystemAdminGUI {
    // Constructor
    public SystemAdminGUI() {
        displaySystemAdminGUI();
    }

    // Display system admin GUI
    public void displaySystemAdminGUI() {
        JFrame frame = new JFrame("System Admin");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, System Admin");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(150, 175, 100, 25);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }
}
