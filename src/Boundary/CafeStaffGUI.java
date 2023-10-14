
import javax.swing.*;

public class CafeStaffGUI {
    // Constructor
    public CafeStaffGUI() {
        displayCafeStaffGUI();
    }

    // Display cafe staff GUI
    public void displayCafeStaffGUI() {
        JFrame frame = new JFrame("Cafe Staff");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Welcome, Cafe Staff");
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
