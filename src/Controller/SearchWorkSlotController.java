package Controller;

import Entity.WorkSlot;

import java.util.ArrayList;
import java.sql.Date;

public class SearchWorkSlotController {
    private WorkSlot workSlot;

    public SearchWorkSlotController() {this.workSlot = new WorkSlot();}

    public ArrayList<WorkSlot> searchDate(Date date) {
        return workSlot.getWorkSlotByDate(date);
    }
}
