package Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeUI extends JFrame {
    public HomeUI() {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window on the screen

        // Create a panel with a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // padding
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Dashboard button
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeUI.this, "You clicked Dashboard");
            }
        });
        panel.add(dashboardButton, constraints);

        // Profile button
        constraints.gridy++;
        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeUI.this, "You clicked Profile");
            }
        });
        panel.add(profileButton, constraints);

        // Settings button
        constraints.gridy++;
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeUI.this, "You clicked Settings");
            }
        });
        panel.add(settingsButton, constraints);

        // About button
        constraints.gridy++;
        JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeUI.this, "You clicked About");
            }
        });
        panel.add(aboutButton, constraints);

        // Exit button
        constraints.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(HomeUI.this, "Are you sure you want to exit?");
                if (option == JOptionPane.YES_OPTION) {
                    dispose(); // Close the window
                }
            }
        });
        panel.add(exitButton, constraints);

        // Add panel to the frame
        add(panel);
        setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeUI();  // Run the constructor to display the GUI
            }
        });
    }
}