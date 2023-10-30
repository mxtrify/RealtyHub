package Boundary;

import Entity.UserAccount;
import Entity.WorkSlot;
import Controller.WorkSlotController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;

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
        titleLabel.setBounds(100, 20, 500, 25);
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

        // Delete Button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(600, 200, 100, 25);
        panel.add(deleteButton);
        deleteButton.addActionListener(e -> deleteSelectedRow());

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Edit Button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(600, 250, 100, 25);
        panel.add(editButton);
        editButton.addActionListener(e -> editSelectedRow());

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

    private void editSelectedRow() {
        int selectedRow = workSlotTable.getSelectedRow();

        if (selectedRow != -1) {
            // Retrieve data from the selected row
            String dateToEdit = tableComponents.getValueAt(selectedRow, 0).toString();
            int chefAmount = (int) tableComponents.getValueAt(selectedRow, 1);
            int cashierAmount = (int) tableComponents.getValueAt(selectedRow, 2);
            int waiterAmount = (int) tableComponents.getValueAt(selectedRow, 3);

            JFrame editFrame = new JFrame("Edit Work Slot");
            JPanel editPanel = new JPanel();
            editPanel.setLayout(null);

            JLabel dateLabel = new JLabel("Date");
            JLabel chefLabel = new JLabel("Chef");
            JLabel cashierLabel = new JLabel("Cashier");
            JLabel waiterLabel = new JLabel("Waiter");

            dateLabel.setBounds(75, 75, 100, 25);
            chefLabel.setBounds(75, 115, 100, 25);
            cashierLabel.setBounds(75, 155, 100, 25);
            waiterLabel.setBounds(75, 195, 100, 25);

            editPanel.add(dateLabel);
            editPanel.add(chefLabel);
            editPanel.add(cashierLabel);
            editPanel.add(waiterLabel);

            JTextField dateField = new JTextField(dateToEdit);
            JTextField chefField = new JTextField(String.valueOf(chefAmount));
            JTextField cashierField = new JTextField(String.valueOf(cashierAmount));
            JTextField waiterField = new JTextField(String.valueOf(waiterAmount));

            dateField.setBounds(150, 75, 100, 25);
            chefField.setBounds(150, 115, 100, 25);
            cashierField.setBounds(150, 155, 100, 25);
            waiterField.setBounds(150, 195, 100, 25);

            editPanel.add(dateField);
            editPanel.add(chefField);
            editPanel.add(cashierField);
            editPanel.add(waiterField);

            JButton saveButton = new JButton("Save");
            saveButton.setBounds(600, 400, 100, 25);
            editPanel.add(saveButton);

            // Action for the save button
            saveButton.addActionListener(e -> {
                try{
                    tableComponents.setValueAt(dateField.getText(), selectedRow, 0);
                    tableComponents.setValueAt(Integer.parseInt(chefField.getText()), selectedRow, 1);
                    tableComponents.setValueAt(Integer.parseInt(cashierField.getText()), selectedRow, 2);
                    tableComponents.setValueAt(Integer.parseInt(waiterField.getText()), selectedRow, 3);

                    WorkSlotController workSlotController = new WorkSlotController();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date utilDate = dateFormat.parse(dateToEdit);
                    Date sqlDate = new Date(utilDate.getTime());

                    workSlotController.updateRoleAmount(sqlDate, 1, Integer.parseInt(chefField.getText()));
                    workSlotController.updateRoleAmount(sqlDate, 2, Integer.parseInt(cashierField.getText()));
                    workSlotController.updateRoleAmount(sqlDate, 3, Integer.parseInt(waiterField.getText()));
                    editFrame.dispose();
                } catch(NumberFormatException | ParseException ex) {
                    ex.printStackTrace();
                }
            });

            editFrame.add(editPanel);
            editFrame.setSize(800, 600);
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editFrame.setVisible(true);
        }
    }

    private void deleteSelectedRow() {
        int seletedRow = workSlotTable.getSelectedRow();
        if(seletedRow != -1) {
            String dateToDelete = tableComponents.getValueAt(seletedRow, 0).toString();
            tableComponents.removeRow(seletedRow);
            WorkSlotController workSlotController = new WorkSlotController();
            boolean deleteSuccess = workSlotController.deleteWorkSlot(dateToDelete);
            if(deleteSuccess) {
                System.out.println("Row Deleted");
            }
            else {
                System.out.println("Error In Deleting");
            }
        }
    }

    public void WorkSlotTable() {
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


