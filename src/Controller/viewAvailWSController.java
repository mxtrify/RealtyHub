package Controller;

import Entity.WorkSlot;

import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

public class viewAvailWSController {
    public Object[][] getWorkSlot(Date date){
        return new WorkSlot(date).getWS();
    }
}
