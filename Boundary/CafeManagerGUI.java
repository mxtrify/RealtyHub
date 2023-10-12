import javax.swing.*;

public class CafeManagerGUI {
    // Constructor
    public CafeManagerGUI() {
        displayCafeManagerGUI();
    }

    // Display cafe manager GUI
    public void displayCafeManagerGUI() {
        JFrame frame = new JFrame("Cafe Manager");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Welcome, Cafe Manager");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        // Logout button
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
