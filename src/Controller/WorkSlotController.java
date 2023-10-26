package Controller;

import Entity.WorkSlot;

import java.sql.Date;
import java.util.List;

public class WorkSlotController {
    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int staffAmount) {
        return new WorkSlot(Date.valueOf(date), chefAmount, cashierAmount, staffAmount);
    }


    public List<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlots();
    }
}


