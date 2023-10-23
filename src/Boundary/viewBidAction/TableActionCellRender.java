package Boundary.viewBidAction;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableActionCellRender extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component com = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
        AccReject action = new AccReject();

        if(!isSelected && row %2 ==0){
            action.setBackground(Color.WHITE);
        }else{
            action.setBackground(com.getBackground());
        }


        return action;
    }

}
