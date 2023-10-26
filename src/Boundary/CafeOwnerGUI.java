package Boundary;

import Entity.UserAccount;
import Entity.WorkSlot;
import Controller.WorkSlotController;
import Boundary.CafeManagerGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
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
        tableComponents.addColumn("Waiter's");
        tableComponents.addColumn("Delete");


        WorkSlotController workSlotController = new WorkSlotController();
        List<WorkSlot> workSlotData = workSlotController.getAllWorkSlots();

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (WorkSlot workSlot : workSlotData) {
            try {
                String formattedDate = outputFormat.format(workSlot.getDate());

                String buttonText = "Delete";
                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(e -> {
                    int selectedRow = workSlotTable.getSelectedRow();
                    if(selectedRow != -1) {
                        String dateToDelete = tableComponents.getValueAt(selectedRow, 0).toString();
                        tableComponents.removeRow(selectedRow);

                        boolean deletionSuccess = workSlotController.deleteWorkSlot(dateToDelete);
                        if (deletionSuccess) {
                            System.out.println("Row deleted successfully from the database.");
                        } else {
                            System.out.println("Failed to delete row from the database.");
                        }
                    }
                });

                Object[] rowData = {
                        formattedDate,
                        workSlot.getChefAmount(),
                        workSlot.getCashierAmount(),
                        workSlot.getWaiterAmount(),
                        buttonText
                };
                tableComponents.addRow(rowData);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        workSlotTable.getColumnModel().getColumn(4).setCellRenderer((TableCellRenderer) new DeleteButtonRenderer());
        workSlotTable.getColumnModel().getColumn(4).setCellEditor(new DeleteButtonEditor(workSlotTable));


        JScrollPane scrollPane = new JScrollPane(workSlotTable);
        scrollPane.setBounds(50, 150, 500, 300);
        panel.add(scrollPane);
    }

    static class DeleteButtonRenderer extends JButton implements TableCellRenderer {
        public DeleteButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }


    static class DeleteButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private JTable table;

        public DeleteButtonEditor(JTable table) {
            this.table = table;
            button = new JButton("Delete");
            button.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String dateToDelete = table.getValueAt(selectedRow, 0).toString();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(selectedRow);

                    boolean deletionSuccess = deleteWorkSlot(dateToDelete);
                    if (deletionSuccess) {
                        System.out.println("Row deleted successfully from the database.");
                    } else {
                        System.out.println("Failed to delete row from the database.");
                    }
                }
                stopCellEditing();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        public Object getCellEditorValue() {
            return "Delete";
        }
    }

    private static boolean deleteWorkSlot(String date) {
//        // current controller method is static
//        WorkSlotController workSlotController = new WorkSlotController();
        return WorkSlotController.deleteWorkSlot(date);
    }
}


