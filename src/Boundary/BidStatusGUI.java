package Boundary;

import Controller.CancelBidController;
import Controller.ViewBidStatusController;
import Entity.Bid;
import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

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
        AtomicReference<Calendar> currentDate = new AtomicReference<>(Calendar.getInstance());
        dateChooser.setMinSelectableDate(currentDate.get().getTime());
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

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(600, 250, 120, 36);
        cancelButton.setFont(new Font("Helvetica", Font.PLAIN,18));
        cancelButton.setEnabled(false);
        panel.add(cancelButton);

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
        getBidStatus(userAccount);
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

        clearButton.addActionListener(e -> {
            dateChooser.setDate(null);
            currentDate.set(Calendar.getInstance());
            getBidStatus(userAccount);
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1 ) {
                    String bidStatus = model.getValueAt(table.getSelectedRow(), 1).toString();
                    if (bidStatus.equals("Pending")) {
                        cancelButton.setEnabled(true);
                        cancelButton.setText("Cancel");
                    } else {
                        cancelButton.setEnabled(false);
                        cancelButton.setText("Can't cancel");
                    }
                }
            }
        });

        cancelButton.addActionListener(e -> {
            String dateString = model.getValueAt(table.getSelectedRow(), 0).toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date utilDate = format.parse(dateString); // Parse to java.util.Date
                java.sql.Date date = new java.sql.Date(utilDate.getTime()); // Convert to java.sql.Date
                CancelBidController cancelBidController = new CancelBidController();
                if(cancelBidController.cancelBid(date)) {
                    JOptionPane.showMessageDialog(frame, "Successfully cancel bid", "Success", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to cancel bid", "Failed", JOptionPane.PLAIN_MESSAGE);
                }
                getBidStatus(userAccount);
            } catch (ParseException d) {
                d.printStackTrace();
            }
        });
    }

    public void getBidStatus(UserAccount userAccount) {
        model.setRowCount(0);
        ArrayList<Bid> bids = new ViewBidStatusController().viewBidStatus(userAccount.getUsername());
        for (Bid bid : bids) {
            model.addRow(new Object[]{
                    bid.getDate(),
                    bid.getBidStatus()
            });
        }
    }
}
