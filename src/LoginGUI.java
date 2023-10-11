import javax.swing.*;

public class LoginGUI extends JFrame {
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        initLoginGUI();
    }

    public void initLoginGUI() {
        JFrame frame = new JFrame("Login");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Cafe Management System");
        titleLabel.setBounds(120,20, 500, 25);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username : ");
        usernameLabel.setBounds(75,75, 100, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(150, 75, 175, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password : ");
        passwordLabel.setBounds(75, 115, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(150, 115, 175, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 175, 100, 25);
        panel.add(loginButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

//        loginButton.addActionListener(e -> {
//            String username = usernameField.getText();
//            String password = new String(passwordField.getPassword());
//
//            UserAccount user = new UserAccount();
//            if (user.validateLogin(username, password)) {
//                JOptionPane.showMessageDialog(frame, "Successfully login", "Login Success", JOptionPane.ERROR_MESSAGE);
//                new LoginController().login(username, password);
//                // Successful login
//                // You can open the appropriate GUI based on the user's role here
//            } else {
//                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });

    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
