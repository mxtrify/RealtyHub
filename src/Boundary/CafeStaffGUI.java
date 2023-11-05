package Boundary;

import Controller.SetMaxSlotController;
import Controller.WorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class CafeStaffGUI {
    JPanel panel = new JPanel();
    private JTable workSlotTable;
    private DefaultTableModel tableComponents;

    // Constructor
    public CafeStaffGUI(UserAccount u) {
        askMaxSlot(u);
        displayCafeStaffGUI(u);
    }

    public void askMaxSlot(UserAccount u) {
        while (u.getMax_slot() < 0) {
            String input = JOptionPane.showInputDialog(null, "Please input max slot (must be greater than 0):");
            try {
                int newMaxSlot = Integer.parseInt(input);
                if (newMaxSlot > 0) {
                    u.setMax_slot(newMaxSlot);
                    new SetMaxSlotController().setMaxSlot(u);;
                    break; // Exit the loop when a valid input is provided
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a value greater than 0.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }

    // Display cafe staff GUI
    public void displayCafeStaffGUI(UserAccount u) {
        JFrame frame = new JFrame("Cafe Staff");
        panel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Welcome, Cafe Staff");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);

        // Max Slot label
        JLabel maxSlotLabel = new JLabel("My Slot : " + u.getMax_slot() );
        maxSlotLabel.setBounds(500,20, 500, 25);
        panel.add(maxSlotLabel);

        // Schedule button
        JButton scheduleButton = new JButton("Schedule");
        scheduleButton.setBounds(600, 100, 100, 25);
        panel.add(scheduleButton);

        // Bid button
        JButton bidStatusButton = new JButton("Bid Status");
        bidStatusButton.setBounds(600, 150, 100, 25);
        panel.add(bidStatusButton);


        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 20, 100, 25);
        panel.add(logoutButton);

        // Display All Work Slots Table
        DisplayWorkSlotTable();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for schedule button
        scheduleButton.addActionListener(e -> {
            frame.dispose();
            new MyScheduleGUI(u);
        });

        // Action for bid status button
        bidStatusButton.addActionListener(e -> {
            frame.dispose();
            new BidStatusGUI(u);
        });

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }

    public void DisplayWorkSlotTable() {
        tableComponents = new DefaultTableModel();
        workSlotTable = new JTable(tableComponents);
        workSlotTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        tableComponents.setRowCount(0);
        tableComponents.addColumn("Date");
        tableComponents.addColumn("Chef's");
        tableComponents.addColumn("Cashier's");
        tableComponents.addColumn("Waiter's");

        WorkSlotController workSlotController = new WorkSlotController();
        List<WorkSlot> workSlotData = workSlotController.getAllWorkSlots();

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (WorkSlot workSlot : workSlotData) {
            try {
                String formattedDate = outputFormat.format(workSlot.getDate());

                Object[] rowData = {
                        formattedDate,
                        workSlot.getChefAmount(),
                        workSlot.getCashierAmount(),
                        workSlot.getWaiterAmount()
                };
                tableComponents.addRow(rowData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JScrollPane scrollPane = new JScrollPane(workSlotTable);
        scrollPane.setBounds(50, 150, 500, 300);
        panel.add(scrollPane);
    }
}
