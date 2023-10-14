import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TableExample {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/buddies";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    public static void main(String[] args) {
        TableExample example = new TableExample();
        example.createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("User Profile Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create JTable and DefaultTableModel
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);

        // Set column names
        model.setColumnIdentifiers(new Object[]{"Username", "Password", "Profile"});

        // Search functionality
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                searchProfile(searchTerm);
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search Profile:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void searchProfile(String searchTerm) {
        model.setRowCount(0); // Clear previous data

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT username, password, profile FROM user_account WHERE profile LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchTerm + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String profile = resultSet.getString("profile");
                model.addRow(new Object[]{username, password, profile});
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
