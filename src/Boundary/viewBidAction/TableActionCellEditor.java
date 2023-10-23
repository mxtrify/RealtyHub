package Boundary.viewBidAction;

import javax.swing.*;
import java.awt.*;

public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;

    public TableActionCellEditor(TableActionEvent event){
        super(new JCheckBox());
        this.event = event;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        AccReject action = new AccReject();
        action.initEvent(event, row);
        action.setBackground(table.getSelectionBackground());
        return action;


    }
}
