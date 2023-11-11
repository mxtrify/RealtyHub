package Boundary;

import Controller.WorkSlotController;
import Controller.CreateWorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class OwnerCreateWorkSlotGUI {
    private Calendar current;
    private JButton backButton;
    private JButton createButton;
    private JTextField searchField;
    private JTextField cashierField;
    private JTextField chefField;
    private JTextField waiterField;

    public OwnerCreateWorkSlotGUI(UserAccount u){
        displayCreateWorkSlotGUI(u);
    }

    public void displayCreateWorkSlotGUI(UserAccount u){
        JFrame frame = new JFrame("Create Work Slot");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Create Work Slot");
        titleLabel.setBounds(100, 10, 500, 25);
        panel.add(titleLabel);

        // Date Label
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(75, 75, 100, 25);
        panel.add(dateLabel);

        // Date Field
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        current = Calendar.getInstance();
        dateChooser.setMinSelectableDate(current.getTime());
        dateChooser.setBounds(150, 75, 175, 25);
        panel.add(dateChooser);

        // Chef Label
        JLabel chefLabel = new JLabel("Chef:");
        chefLabel.setBounds(75, 115, 100, 25);
        panel.add(chefLabel);

        // Chef Field
        chefField = new JTextField(20);
        chefField.setBounds(150, 115, 100, 25);
        panel.add(chefField);

        // Cashier Label
        JLabel cashierLabel = new JLabel("Cashier:");
        cashierLabel.setBounds(75, 155, 100, 25);
        panel.add(cashierLabel);

        // Cashier Field
        cashierField = new JTextField(20);
        cashierField.setBounds(150, 155, 100, 25);
        panel.add(cashierField);

        // Waiter Label
        JLabel waiterLabel = new JLabel("Waiter:");
        waiterLabel.setBounds(75, 195, 100, 25);
        panel.add(waiterLabel);

        // Staff Field
        waiterField = new JTextField(20);
        waiterField.setBounds(150, 195, 100, 25);
        panel.add(waiterField);

        // Create Button
        createButton = new JButton("Create");
        createButton.setBounds(300, 300, 100, 25);
        panel.add(createButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(20, 300, 100, 25);
        panel.add(backButton);

        // Action for create button
        createButton.addActionListener(e -> {
            // If date field is not selected, prompt user to select
            if(dateChooser.getDate() != null) {
                java.util.Date selectedDateUtil = dateChooser.getDate();
                java.sql.Date selectedDate = new java.sql.Date(selectedDateUtil.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(selectedDate);

                // Error message if there is already an existing workslot
                if (workSlotAlreadyExists(selectedDate)) {
                    JOptionPane.showMessageDialog(frame, "Work slot already exists for the selected date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If any of the fields is empty, prompt user to enter valid amount
                if(!chefField.getText().isEmpty() && !cashierField.getText().isEmpty() && !waiterField.getText().isEmpty()) {
                    int numOfChef = Integer.parseInt(chefField.getText());
                    int numOfCashier = Integer.parseInt(cashierField.getText());
                    int numOfWaiter = Integer.parseInt(waiterField.getText());

                    if(numOfCashier < 1 || numOfChef < 1 || numOfWaiter < 1) {
                        JOptionPane.showMessageDialog(frame, "Chef, Cashier or Staff must be more than 1");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Successfully created!");
                        WorkSlot workSlot = new CreateWorkSlotController().createWorkSlot(date, numOfChef, numOfCashier, numOfWaiter);
                        frame.dispose();
                        new CafeOwnerGUI(u);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Fields must be filled");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a date");
            }
        });

        // Action for back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeOwnerGUI(u);
        });

        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean workSlotAlreadyExists(Date selectedDate){
        WorkSlotController workSlotController = new WorkSlotController();
        List<WorkSlot> workSlots = workSlotController.getWorkSlotsByDate(selectedDate);
        return !workSlots.isEmpty();
    }
}