package Controller;

import Entity.WorkSlot;

import java.util.List;

public class WorkSlotController {
    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int staffAmount) {
        WorkSlot workSlot = new WorkSlot(date, chefAmount, cashierAmount, staffAmount);
        workSlot.createWorkSlot(date);
        return workSlot;
    }

    public List<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlots();
    }
}


