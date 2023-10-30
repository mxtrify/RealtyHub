package Controller;

import Entity.WorkSlot;

import java.util.List;

public class WorkSlotController {
    private WorkSlot workSlot;
    public WorkSlotController() {
        this.workSlot = new WorkSlot();
    }

    public List<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlots();
    }
}


