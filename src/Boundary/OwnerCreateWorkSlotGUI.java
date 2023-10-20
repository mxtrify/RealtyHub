package Boundary;

import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// Need to extend JFrame?
public class OwnerCreateWorkSlotGUI {
    private JButton backButton;
    private JButton createButton;
    private JTextField searchField;
    private JTextField dateField;
    private JTextField cashierField;
    private JTextField chefField;
    private JTextField staffField;

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
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(75, 35, 100, 25);
        panel.add(searchLabel);
        searchField = new JTextField(25);
        searchField.setBounds(150, 35, 100, 25);
        panel.add(searchField);

        // Date Label
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(75, 75, 100, 25);
        panel.add(dateLabel);

        // Date Field
        /*
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        dateChooser.setBounds(150, 75, 175, 25);
        panel.add(dateChooser);

        */ // Calendar Testing
        dateField = new JTextField(20);
        dateField.setBounds(150, 75, 175, 25);
        panel.add(dateField);

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
        staffField = new JTextField(20);
        staffField.setBounds(150, 195, 100, 25);
        panel.add(staffField);

        // Create Button
        createButton = new JButton("Create");
        createButton.setBounds(300, 220, 100, 25);
        panel.add(createButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(20, 220, 100, 25);
        panel.add(backButton);

        // Action for create button


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
