package Boundary;

import Boundary.viewBidAction.TableActionCellEditor;
import Boundary.viewBidAction.TableActionCellRender;
import Boundary.viewBidAction.TableActionEvent;
import Controller.ManagerController;
import Entity.CafeManager;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

public class viewBidsGUI {
    // ~~ For Table data ~~
    private Object[][] data;
    private DefaultTableModel model;

    private JTable table;
    private JScrollPane scrollPane;

    public viewBidsGUI(CafeManager u){
        displayViewBids(u);
    }

    public void displayViewBids(CafeManager u){
        JFrame frame = new JFrame("View Bids");
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("View Bid");
        title.setFont(new Font("Jost", Font.BOLD, 30));
        title.setBounds(293, 46, 214, 80);
        frame.add(title);

        JButton back = new JButton("Back");
        back.setBounds(660, 23, 110, 36);
        back.addActionListener(e->{
            frame.dispose();
            new CafeManagerGUI(u);
        });
        frame.add(back);

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
        String[] columnNames = {"ID","Staff Name", "Role Name", "Date", "Action"};
        data = new ManagerController().viewBidData("-- Select --");
        model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells in the button column editable
                return column == 4;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onAccept(int row) {
                System.out.println("Accept bid made by: " + model.getValueAt(row, 0));

            }

            @Override
            public void onReject(int row) {
                System.out.println("Reject bid made by: " + model.getValueAt(row, 0));

            }
        };
        table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));

        table.setAutoCreateColumnsFromModel(false);

        // Add table to a scroll pane
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30,254, 740, 316);
        frame.add(scrollPane);


        // Add Dropdown action listener
        // to update data in JTable
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(scrollPane);
                frame.repaint();
                frame.revalidate();

                String selection = (String) filter.getSelectedItem();
                data = new ManagerController().viewBidData(selection);
                model = new DefaultTableModel(data, columnNames){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        // Make all cells in the button column editable
                        return column == 4;
                    }
                };

                table = new JTable (model);
                table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);




                TableActionEvent event = new TableActionEvent() {
                    @Override
                    public void onAccept(int row) {

                        System.out.println("Accept bid made by: " + model.getValueAt(row, 0));

                    }

                    @Override
                    public void onReject(int row) {
                        System.out.println("Reject bid made by: " + model.getValueAt(row, 0));
                    }
                };

                // Add the new cell editor and renderer to column 3
                table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
                table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));


                // Add table to a scroll pane
                scrollPane = new JScrollPane(table);
                scrollPane.setBounds(30,254, 740, 316);
                frame.add(scrollPane);
                frame.repaint();
                frame.revalidate();
            }
        });



    }

}
