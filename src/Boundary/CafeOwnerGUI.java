package Boundary;

import Entity.UserAccount;
import Entity.WorkSlot;
import Controller.WorkSlotController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class CafeOwnerGUI {
    JPanel panel = new JPanel();
    private JTable workSlotTable;
    private DefaultTableModel tableComponents;
    private WorkSlotController getAllWorkSlotData;

    // Constructor
    public CafeOwnerGUI(UserAccount u) {
        this.getAllWorkSlotData = new WorkSlotController();
        displayCafeOwnerGUI(u);
    }

    // Display cafe owner GUI
    public void displayCafeOwnerGUI(UserAccount u) {
        JFrame frame = new JFrame("Cafe Owner");
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, Cafe Owner");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 50, 100, 25);
        panel.add(logoutButton);

        // Search bar
        JTextField searchField = new JTextField();
        searchField.setBounds(100, 100, 150, 25);
        panel.add(searchField);

        // Create workSlot button
        JButton createWorkSlotButton = new JButton("Create Slot");
        createWorkSlotButton.setBounds(300, 100, 100, 25);
        panel.add(createWorkSlotButton);

        // Display Table
        WorkSlotTable();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for createWorkSlotButton
        createWorkSlotButton.addActionListener(e -> {
            frame.dispose();
            new OwnerCreateWorkSlotGUI(u);
        });

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }

    public void WorkSlotTable() {
        tableComponents = new DefaultTableModel();
        workSlotTable = new JTable(tableComponents);

        tableComponents.setRowCount(0);
        tableComponents.addColumn("Date");
        tableComponents.addColumn("Chef's");
        tableComponents.addColumn("Cashier's");
        tableComponents.addColumn("Staff's");

        WorkSlotController workSlotController = new WorkSlotController();
        List<WorkSlot> workSlotData = workSlotController.getAllWorkSlots();

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (WorkSlot workSlot : workSlotData) {
            try {
                java.util.Date parsedDate = inputFormat.parse(workSlot.getDate());
                String formattedDate = outputFormat.format(parsedDate);

                Object[] rowData = {
                        formattedDate,
                        workSlot.getChefAmount(),
                        workSlot.getCashierAmount(),
                        workSlot.getStaffAmount()
                };
                tableComponents.addRow(rowData);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }


        JScrollPane scrollPane = new JScrollPane(workSlotTable);
        scrollPane.setBounds(50, 150, 500, 300);
        panel.add(scrollPane);
    }
}


