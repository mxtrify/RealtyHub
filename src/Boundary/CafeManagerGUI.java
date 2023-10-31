package Boundary;

import Controller.ViewAvailWSController;
import Entity.CafeManager;
import Entity.UserAccount;
import Entity.WorkSlot;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CafeManagerGUI {
    private JDateChooser date_search;

    private Object[][] data;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableCellRenderer centerRenderer;
    private WorkSlot workSlot;
    private String status;
    // Constructor
    public CafeManagerGUI(UserAccount u) {
        displayCafeManagerGUI((CafeManager) u);
    }

    // Display Cafe Manager GUI
    public void displayCafeManagerGUI(CafeManager u) {
        JFrame frame = new JFrame("Cafe Manager");
        frame.setLayout(null);


        // Title label
        JLabel titleLabel = new JLabel("Welcome, "+u.getUsername());
        titleLabel.setFont(new Font("Jost", Font.BOLD, 36));
        titleLabel.setBounds(30,83, 413, 55);
        frame.add(titleLabel);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(660, 23, 110, 36);
        frame.add(logoutButton);

        // Assign Button
        JButton assignButton = new JButton("Assign");
        assignButton.setBounds(660, 200, 110, 36);
        frame.add(assignButton);

        // Assign Button implementation
        assignButton.addActionListener(e-> {
            if(workSlot == null){
                JOptionPane.showMessageDialog(frame, "Please select a workslot", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if(status.equals("Not Available")){
                JOptionPane.showMessageDialog(frame, "Slot is full", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            else {
                // Dispose this frame
                frame.dispose();

                // Switch to assign staff frame
                new AssignStaffGUI(u, workSlot);


            }

        });


        // Date search bar
        date_search = new JDateChooser();
        Calendar current = Calendar.getInstance();
        // Prevent selecting past dates
        date_search.setMinSelectableDate(current.getTime());
        date_search.setBounds(30,145, 350,36);
        frame.add(date_search);

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(385,145,80, 36);
        frame.add(searchButton);

        // Table
        data = new ViewAvailWSController().getAllWorkSlots();
        String[] columnNames = {"Date", "Chef", "Cashier", "Waiter", "Status"};

        // Generate table model
        model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 5;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // To align the data to the center
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Align center to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add table to scroll pane
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30,200, 550, 316);
        frame.add(scrollPane);

        // Get data from table after selecting
        table.getSelectionModel().addListSelectionListener(e->{

            if(!e.getValueIsAdjusting()){
                int selected = table.getSelectedRow();
                if(selected != -1){
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");

                    try {
                        // Create Workslot object
                        Date date = new Date(sdf.parse( (String)table.getValueAt(selected, 0)).getTime());
                        int chef_amt = (int) table.getValueAt(selected, 1);
                        int cashier_amt = (int) table.getValueAt(selected, 2);
                        int waiter_amt = (int) table.getValueAt(selected, 3);
                        status = (String) table.getValueAt(selected, 4);

                        workSlot = new WorkSlot(date, chef_amt, cashier_amt, waiter_amt);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }


                }
            }

        });


        // Action button to search specific date
        searchButton.addActionListener(e-> {
            // Remove old table
            frame.remove(scrollPane);
            // Clear selected workslot (back to unselected)
            workSlot = null;

            // Refresh frame
            frame.repaint();
            frame.revalidate();

            // Get selected date
            java.util.Date d = date_search.getDate();

            // Get data from database
            if (d == null){
                // Search nothing
                // Display all workslots
                data = new ViewAvailWSController().getAllWorkSlots();
            }else {
                // Search for a workslot
                java.sql.Date selectedDate = new java.sql.Date(d.getTime());
                data = new ViewAvailWSController().getWorkSlot(selectedDate);
            }

            // Set data into model
            model.setDataVector(data, columnNames);
            table.setModel(model);

            // To align the data to the center
            centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Align center to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Add table to scroll pane
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(30,200, 550, 316);
            // Add to frame
            frame.add(scrollPane);

            // Refresh frame
            frame.repaint();
            frame.revalidate();





        });

        // Button for clear filter (Date)
        JButton clearFilter = new JButton("Clear Filter");
        clearFilter.setBounds(475,145,100, 36);
        frame.add(clearFilter);
        // Implement clear filter
        clearFilter.addActionListener(e->{
            // Clear date chooser
            date_search.setDate(null);
            // Remove old table
            frame.remove(scrollPane);
            // Clear selected workslot (back to unselected)
            workSlot = null;

            // Refresh frame
            frame.repaint();
            frame.revalidate();


            // Get data from database
            data = new ViewAvailWSController().getAllWorkSlots();

            // Set data into model
            model.setDataVector(data, columnNames);
            table.setModel(model);

            // To align the data to the center
            centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            // Align center to all columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // Add table to scroll pane
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(30,200, 550, 316);
            // Add to frame
            frame.add(scrollPane);

            // Refresh frame
            frame.repaint();
            frame.revalidate();






        });



        // Frame format
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }

}

