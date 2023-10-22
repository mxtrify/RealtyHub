package Controller;

import Entity.WorkSlot;

public class WorkSlotController {
    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int staffAmount) {
        WorkSlot workSlot = new WorkSlot(date, chefAmount, cashierAmount, staffAmount);
        workSlot.createWorkSlot(date);
        return workSlot;
    }

    public WorkSlot viewWorkSlot(int workSlotID) {
        WorkSlot viewWorkSlot = new WorkSlot();
        viewWorkSlot.viewWorkSlot(workSlotID);
        return viewWorkSlot;
    }
}


