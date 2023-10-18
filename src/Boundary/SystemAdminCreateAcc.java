package Boundary;

import javax.swing.*;

public class SystemAdminCreateAcc {
    private JTextField createName;
    private JTextField createUsername;
    private JPasswordField createPassword;

    public SystemAdminCreateAcc() {
        displaySystemAdminCreateAccGUI();
    }

    public void displaySystemAdminCreateAccGUI(){
        JFrame frame = new JFrame("System Admin Create Account");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame.setContentPane(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Title Label
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setBounds(125, 20, 500, 25);
        panel.add(titleLabel);

        // Create name label
        JLabel nameField = new JLabel("Name: ");
        nameField.setBounds(75, 75, 100, 25);
        panel.add(nameField);

        // Create name field
        createName = new JTextField(20);
        createName.setBounds(150, 75, 175, 25);
        panel.add(createName);
    }
}
