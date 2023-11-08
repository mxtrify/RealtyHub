package Boundary;

import Controller.*;
import Entity.Bid;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UpdateBidGUI {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;
    private JPanel panel;

    public UpdateBidGUI(UserAccount userAccount, Bid bid) {
        displayUpdateBid(userAccount, bid);
    }

    private void displayUpdateBid(UserAccount userAccount, Bid bid) {
        frame = new JFrame("Update Bid");
        panel = new JPanel();
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Update Bid");
        titleLabel.setBounds(350, 30, 415, 58);
        titleLabel.setFont(new Font("Jost", Font.BOLD, 30));
        frame.add(titleLabel);

        // Label
        JLabel currentDateLabel = new JLabel("Current date : ");
        currentDateLabel.setBounds(50, 110, 415, 36);
        currentDateLabel.setFont(new Font("Jost", Font.BOLD, 18));
        frame.add(currentDateLabel);

        // DateChooser
        JDateChooser dateChooser = new JDateChooser(bid.getDate());
        AtomicReference<Calendar> currentDate = new AtomicReference<>(Calendar.getInstance());
        dateChooser.setMinSelectableDate(currentDate.get().getTime());
        dateChooser.setBounds(225,110, 175,36);
        dateChooser.setEnabled(false);
        frame.add(dateChooser);

        // Label
        JLabel newDate = new JLabel("Choose new date : ");
        newDate.setBounds(50, 150, 415, 36);
        newDate.setFont(new Font("Jost", Font.BOLD, 18));
        frame.add(newDate);

        // Table
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        getWorkSlot(bid.getDate());
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(225,150, 450, 250);
        frame.add(scrollPane);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(500, 500, 235, 30);
        panel.add(saveButton);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        backButton.addActionListener(e -> {
            frame.dispose();
            new BidStatusGUI(userAccount);
        });

        saveButton.addActionListener(e -> {
            String dateString = model.getValueAt(table.getSelectedRow(), 0).toString();
            System.out.println(dateString);
            SimpleDateFormat format = new SimpleDateFormat("dd MMM, yyyy");
            try {
                java.util.Date utilDate = format.parse(dateString); // Parse to java.util.Date
                Date sqlDate = new Date(utilDate.getTime()); // Convert to java.sql.Date
                if(userAccount.getRole_id() == 1) { // Waiter
                    int chefAmountLeft = (int) model.getValueAt(table.getSelectedRow(),3);
                    if(chefAmountLeft != 0) {
                        UpdateBidController updateBidController = new UpdateBidController();
                        if(updateBidController.updateBid(bid.getBidId(), userAccount.getUsername(), sqlDate)) {
                            JOptionPane.showMessageDialog(frame, "Successfully Update bid", "Success", JOptionPane.PLAIN_MESSAGE);
                            frame.dispose();
                            new BidStatusGUI(userAccount);
                        } else {
                            JOptionPane.showMessageDialog(frame, "You already submitted bid for this date", "Failed", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Work slot is full for your role", "Failed", JOptionPane.PLAIN_MESSAGE);
                    }
                } else if (userAccount.getRole_id() == 2) { // Cashier
                    int chefAmountLeft = (int) model.getValueAt(table.getSelectedRow(),2);
                    if(chefAmountLeft != 0) {
                        UpdateBidController updateBidController = new UpdateBidController();
                        if(updateBidController.updateBid(bid.getBidId(), userAccount.getUsername(), sqlDate)) {
                            JOptionPane.showMessageDialog(frame, "Successfully Update bid", "Success", JOptionPane.PLAIN_MESSAGE);
                            frame.dispose();
                            new BidStatusGUI(userAccount);
                        } else {
                            JOptionPane.showMessageDialog(frame, "You already submitted bid for this date", "Failed", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Work slot is full for your role", "Failed", JOptionPane.PLAIN_MESSAGE);
                    }
                } else if (userAccount.getRole_id() == 3) { // Chef
                    int chefAmountLeft = (int) model.getValueAt(table.getSelectedRow(),1);
                    if(chefAmountLeft != 0) {
                        UpdateBidController updateBidController = new UpdateBidController();
                        if(updateBidController.updateBid(bid.getBidId(), userAccount.getUsername(), sqlDate)) {
                            JOptionPane.showMessageDialog(frame, "Successfully Update bid", "Success", JOptionPane.PLAIN_MESSAGE);
                            frame.dispose();
                            new BidStatusGUI(userAccount);
                        } else {
                            JOptionPane.showMessageDialog(frame, "You already submitted bid for this date", "Failed", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Work slot is full for your role", "Failed", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            } catch (ParseException d) {
                d.printStackTrace();
            }
        });
    }

    public void getWorkSlot(Date date) {
        model.setRowCount(0);
        String[] tableTitle = {"Date", "Chef's", "Cashier's", "Waiter's", "Availability"};
        ViewAvailWSController workSlotController = new ViewAvailWSController();
        Object[][] workSlotData = workSlotController.getAllWorkSlots();

        model.setDataVector(workSlotData, tableTitle);
    }

}
