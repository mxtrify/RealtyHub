package Controller;

import Entity.WorkSlot;

public class WorkSlotController {
    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int staffAmount) {
        WorkSlot workSlot = new WorkSlot(date, chefAmount, cashierAmount, staffAmount);
        workSlot.createWorkSlot(date);
        return workSlot;
    }
}


