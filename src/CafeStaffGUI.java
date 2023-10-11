import javax.swing.*;

public class CafeStaffGUI {
    public CafeStaffGUI() {
        displayCafeStaffGUI();
    }

    public void displayCafeStaffGUI() {
        JFrame frame = new JFrame("Cafe Staff");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome, Cafe Staff");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(150, 175, 100, 25);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }
}
