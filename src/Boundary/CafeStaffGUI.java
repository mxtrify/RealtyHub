package Boundary;

import Controller.SetMaxSlotController;
import Controller.WorkSlotController;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CafeStaffGUI {
    JPanel panel = new JPanel();
    private Calendar current;
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

        // Bid Status button
        JButton bidStatusButton = new JButton("Bid Status");
        bidStatusButton.setBounds(600, 150, 100, 25);
        panel.add(bidStatusButton);

        // Bid Button
        JButton bid = new JButton("Bid");
        bid.setBounds(600,200, 100, 25);
        panel.add(bid);

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(250, 100, 100, 25);
        panel.add(searchButton);

        // Search Bar
        JDateChooser searchDate = new JDateChooser();
        searchDate.setDateFormatString("dd/MM/yyyy");
        current = Calendar.getInstance();
        searchDate.setMinSelectableDate(current.getTime());
        searchDate.setBounds(50,100, 150,25);
        panel.add(searchDate);

        // Clear search
        JButton clearSearchButton = new JButton("Clear");
        clearSearchButton.setBounds(350, 100, 100, 25);
        panel.add(clearSearchButton);

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

        // Action for Bid Button
        bid.addActionListener(e -> {
            // Call controller
        });

        // Action for Search Button
        searchButton.addActionListener(e -> {
            try {
                Date selectedDate = new Date(searchDate.getDate().getTime());
                filterTableByDate(selectedDate);
                System.out.println(selectedDate);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        // Action for Clear Search
        clearSearchButton.addActionListener(e -> {
            searchDate.setDate(null);
            filterTableByDate(null);
        });

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

    private void filterTableByDate(Date selectedDate) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(workSlotTable.getModel());
        workSlotTable.setRowSorter(sorter);

        if (selectedDate != null) {
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(selectedDate);
            RowFilter<TableModel, Integer> rowFilter = RowFilter.regexFilter(formattedDate, 0);
            sorter.setRowFilter(rowFilter);
        } else {
            sorter.setRowFilter(null);
        }
    }
}
