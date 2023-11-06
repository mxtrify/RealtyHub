package Boundary;

import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;

public class BidStatusGUI {
    private JFrame frame;
    private DefaultTableModel model;
    private String[] columnNames = {"Date", "Status"};

    public BidStatusGUI(UserAccount userAccount) {
        displayBidStatus(userAccount);
    }

    public void displayBidStatus(UserAccount userAccount) {
        frame = new JFrame("Bid Status");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Bid Status");
        titleLabel.setBounds(45, 42, 415, 58);
        titleLabel.setFont(new Font("Jost", Font.BOLD, 30));
        frame.add(titleLabel);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(650, 20, 100, 25);
        panel.add(backButton);

        // DateChooser
        JDateChooser dateChooser = new JDateChooser();
        Calendar currentDate = Calendar.getInstance();
        dateChooser.setMinSelectableDate(currentDate.getTime());
        dateChooser.setBounds(50,135, 175,36);
        frame.add(dateChooser);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(230, 135, 90, 36);
        searchButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(searchButton);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(320, 135, 60, 36);
        clearButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        panel.add(clearButton);

        // Dropdown filter
        String[] options = {"Pending", "Accepted", "Rejected"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setBounds(380, 135, 125, 36);
        dropdown.setFont(new Font("Helvetica", Font.PLAIN,18));
        frame.add(dropdown);

        // Table
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        model.setColumnIdentifiers(columnNames);

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,175, 510, 350);
        frame.add(scrollPane);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeStaffGUI(userAccount);
        });
    }

    public void getBidStatus() {
        model.setRowCount(0);

    }
}
