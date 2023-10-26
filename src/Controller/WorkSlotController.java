package Controller;

import Entity.WorkSlot;

import java.sql.Date;
import java.util.List;

public class WorkSlotController {
    private WorkSlot workSlot;
    public WorkSlotController() {
        this.workSlot = new WorkSlot();
    }
    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int waiterAmount) {
        return workSlot.createWorkSlot(date, chefAmount, cashierAmount, waiterAmount);
    }

    public boolean deleteWorkSlot(String date) {
        try {
            Date sqlDate = Date.valueOf(date);
            return workSlot.deleteWorkSlot(sqlDate);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlots();
    }
}


