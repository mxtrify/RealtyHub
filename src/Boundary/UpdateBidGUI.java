package Boundary;

import Controller.WorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UpdateBidGUI {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;
    private JPanel panel;
    private String[] columnNames = {"Date"};

    public UpdateBidGUI(UserAccount userAccount, Date date) {
        displayUpdateBid(userAccount, date);
    }

    private void displayUpdateBid(UserAccount userAccount, Date date) {
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
        JDateChooser dateChooser = new JDateChooser(date);
        AtomicReference<Calendar> currentDate = new AtomicReference<>(Calendar.getInstance());
        dateChooser.setMinSelectableDate(currentDate.get().getTime());
        dateChooser.setBounds(180,110, 175,36);
        dateChooser.setEnabled(false);
        frame.add(dateChooser);

        // Label
        JLabel newDate = new JLabel("Choose new date : ");
        newDate.setBounds(50, 150, 415, 58);
        newDate.setFont(new Font("Jost", Font.BOLD, 18));
        frame.add(newDate);

        DisplayWorkSlotTable(date);

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
            frame.dispose();
        });
    }

    public void DisplayWorkSlotTable(Date date) {
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        model.setRowCount(0);
        model.setColumnIdentifiers(columnNames);

        WorkSlotController workSlotController = new WorkSlotController();
        List<WorkSlot> workSlotData = workSlotController.getAllWorkSlots();


        for (WorkSlot workSlot : workSlotData) {
            try {
                if (!date.equals(workSlot.getDate())) {
                    Object[] rowData = {
                            workSlot.getDate()
                    };
                    model.addRow(rowData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 200, 200, 250);
        panel.add(scrollPane);
    }
}
