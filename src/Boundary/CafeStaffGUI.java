package Boundary;

import Controller.*;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class CafeStaffGUI {
    private JFrame frame;
    private JPanel panel = new JPanel();
    private Calendar current;
    private JTable workSlotTable;
    private DefaultTableModel tableComponents;
    private JScrollPane scrollPane;

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
        frame = new JFrame("Cafe Staff");
        panel.setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Welcome, Cafe Staff");
        titleLabel.setBounds(100,20, 500, 25);
        panel.add(titleLabel);


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
        searchDate.setDateFormatString("dd MMM, yyyy");
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
            bidSelectedRow(u);
        });

        // Action for Search Button
        searchButton.addActionListener(e -> {
            try {
                if (searchDate.getDate() == null){
                    filterTableByDate(null);
                }else{
                    Date selectedDate = new Date(searchDate.getDate().getTime());
                    filterTableByDate(selectedDate);
                    System.out.println(selectedDate);
                }

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
        tableComponents = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 5;
            }
        };
        workSlotTable = new JTable(tableComponents);
        workSlotTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        tableComponents.setRowCount(0);
        String[] tableTitle = {"Date", "Chef's", "Cashier's", "Waiter's", "Availability"};


        ViewAvailWSController workSlotController = new ViewAvailWSController();
        Object[][] workSlotData = workSlotController.getAllWorkSlots();

        tableComponents.setDataVector(workSlotData, tableTitle);

        scrollPane = new JScrollPane(workSlotTable);
        scrollPane.setBounds(50, 150, 500, 300);
        panel.add(scrollPane);
    }

    private void filterTableByDate(Date selectedDate) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(workSlotTable.getModel());
        workSlotTable.setRowSorter(sorter);

        if (selectedDate != null) {
            String formattedDate = new SimpleDateFormat("dd MMM, yyyy").format(selectedDate);
            RowFilter<TableModel, Integer> rowFilter = RowFilter.regexFilter(formattedDate, 0);
            sorter.setRowFilter(rowFilter);
        } else {
            sorter.setRowFilter(null);
        }
    }

    private void bidSelectedRow(UserAccount u) {
        int selectedRow = workSlotTable.getSelectedRow();
        if (selectedRow != -1) {
            String dateString = (String) tableComponents.getValueAt(selectedRow, 0);
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
                java.util.Date parsedDate = dateFormat.parse(dateString);
                Date selectedDate = new Date(parsedDate.getTime());

                int slotLeft = new ViewMonthlySlotLeft().viewMonthlySlotLeft(u,selectedDate);
                if (slotLeft > 0){
                    int confirm = JOptionPane.showConfirmDialog(frame, String.format("Slot left this Month: %d%nAre you sure want to make bid on this date?", slotLeft) , "Make Bid", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION){
                        MakeBidController bidController = new MakeBidController();
                        if (bidController.makeBid(u.getUsername(), selectedDate)) {
                            panel.remove(scrollPane);
                            panel.repaint();
                            panel.revalidate();
                            DisplayWorkSlotTable();
                        }
                    }


                } else if (slotLeft == 0) {
                    JOptionPane.showMessageDialog(frame, String.format("Slot limit reached!%nSlot left this Month: %d", slotLeft), "Limit Reached", JOptionPane.WARNING_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(frame, "Issue while approving bid, please try again!", "Exception Occurs", JOptionPane.WARNING_MESSAGE);
                }


            } catch (IllegalArgumentException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to place a bid.");
        }
    }
}
