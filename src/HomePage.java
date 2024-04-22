import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Center the window

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create menu panel
        JPanel menuPanel = new JPanel(new GridLayout(5, 1));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        // Create menu items
        JButton dashboardButton = new JButton("Dashboard");
        JButton profileButton = new JButton("Profile");
        JButton settingsButton = new JButton("Settings");
        JButton aboutButton = new JButton("About");
        JButton exitButton = new JButton("Exit");

        // Add menu items to the menu panel
        menuPanel.add(dashboardButton);
        menuPanel.add(profileButton);
        menuPanel.add(settingsButton);
        menuPanel.add(aboutButton);
        menuPanel.add(exitButton);

        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Add menu panel and content panel to the main panel
        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add action listeners to menu items
        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle dashboard button click
                JOptionPane.showMessageDialog(HomePage.this, "You clicked Dashboard");
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle profile button click
                JOptionPane.showMessageDialog(HomePage.this, "You clicked Profile");
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle settings button click
                JOptionPane.showMessageDialog(HomePage.this, "You clicked Settings");
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle about button click
                JOptionPane.showMessageDialog(HomePage.this, "You clicked About");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle exit button click
                int option = JOptionPane.showConfirmDialog(HomePage.this, "Are you sure you want to exit?");
                if (option == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        // Add main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage();
            }
        });
    }
}