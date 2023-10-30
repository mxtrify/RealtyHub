package Boundary;

import Controller.WorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

// Need to extend JFrame?
public class OwnerCreateWorkSlotGUI {
    private JButton backButton;
    private JButton createButton;
    private JTextField searchField;
    private JTextField cashierField;
    private JTextField chefField;
    private JTextField waiterField;

    public OwnerCreateWorkSlotGUI(UserAccount u){
        displayCreateWorkSlotGUI(u);
    }

    // Need each part to be an object?
    public void displayCreateWorkSlotGUI(UserAccount u){
        JFrame frame = new JFrame("Create Work Slot");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Create Work Slot");
        titleLabel.setBounds(100, 10, 500, 25);
        panel.add(titleLabel);

        // Search Bar
//        JLabel searchLabel = new JLabel("Search:");
//        searchLabel.setBounds(75, 35, 100, 25);
//        panel.add(searchLabel);
//        searchField = new JTextField(25);
//        searchField.setBounds(150, 35, 100, 25);
//        panel.add(searchField);

        // Date Label
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(75, 75, 100, 25);
        panel.add(dateLabel);

        // Date Field
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
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

        // Staff Label
        JLabel staffLabel = new JLabel("Staff:");
        staffLabel.setBounds(75, 195, 100, 25);
        panel.add(staffLabel);

        // Staff Field
        waiterField = new JTextField(20);
        waiterField.setBounds(150, 195, 100, 25);
        panel.add(waiterField);

        // Create Button
        createButton = new JButton("Create");
        createButton.setBounds(300, 220, 100, 25);
        panel.add(createButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(20, 220, 100, 25);
        panel.add(backButton);

        // Action for create button
        createButton.addActionListener(e -> {
            java.util.Date selectedDateUtil = dateChooser.getDate();
            java.sql.Date selectedDate = new java.sql.Date(selectedDateUtil.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(selectedDate);

            int numOfChef = Integer.parseInt(chefField.getText());
            int numOfCashier = Integer.parseInt(cashierField.getText());
            int numOfWaiter = Integer.parseInt(waiterField.getText());
            WorkSlot workSlot = new WorkSlotController().createWorkSlot(date, numOfChef, numOfCashier, numOfWaiter);

            if(numOfCashier < 1 || numOfChef < 1 || numOfWaiter < 1) {
                JOptionPane.showMessageDialog(frame, "Chef, Cashier or Staff must be more than 1");
            } else {
                JOptionPane.showMessageDialog(frame, "Successfully created!");
                frame.dispose();
                new CafeOwnerGUI(u);
            }

        });


        // Action for back button
        backButton.addActionListener(e -> {
            frame.dispose();
            new CafeOwnerGUI(u);
        });

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
