package Boundary;

import Entity.CafeManager;
import Entity.UserAccount;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CafeManagerGUI {
    // Constructor
    public CafeManagerGUI(UserAccount u) {
        displayCafeManagerGUI((CafeManager) u);
    }

    // Display cafe manager GUI
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

        // View Bids button
        JButton viewBidsButton = new JButton("View Bids");
        viewBidsButton.setBounds(630, 140, 140, 36);
        viewBidsButton.addActionListener(e->{
            frame.dispose();
            new viewBidsGUI(u);
        });
        frame.add(viewBidsButton);

        // View available staff button
        JButton viewAvailStaffButton = new JButton("<html><div style = 'text-align:center;'>View <br/>Available Staff</div></html>");
        viewAvailStaffButton.setBounds(630, 190, 140, 36);
        frame.add(viewAvailStaffButton);

        // Date search bar
        JDateChooser date_search = new JDateChooser();
        Calendar current = Calendar.getInstance();
        date_search.setDate(current.getTime());
        date_search.setMinSelectableDate(current.getTime());
        date_search.setBounds(30,145, 350,36);
        frame.add(date_search);

        // Filter search
        List<String> roleList = u.getRoles();
        JComboBox<String> filter = new JComboBox<>(roleList.toArray(new String[0]));
        filter.setBounds(388, 145, 159, 36);
        frame.add(filter);

        // Table
        Object[][] data = {
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"},
                {"2023-09-15", "10:00:00", "Chef", "2", "Available", "Assign"}

        };
        String[] columnNames = {"Date", "Time", "Role", "Amount", "Status", "Assign"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 5;
            }
        };
        JTable table = new JTable(model);

        // Custom button renderer and editor
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(table));


        table.setAutoCreateColumnsFromModel(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        model.setColumnIdentifiers(columnNames);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30,254, 740, 316);
        frame.add(scrollPane);





        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Action for logout button
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });
    }

    // Custom button renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom button editor
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private JTable table;

        public ButtonEditor(JTable table) {
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e->{
                int selected_row = table.getSelectedRow();
                int col = table.getColumnCount();

                for(int c = 0; c < col;c++){
                    Object value = table.getValueAt(selected_row, c);
                    System.out.println(value);
                }
                System.out.println("");

                stopCellEditing();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }

    }




}
