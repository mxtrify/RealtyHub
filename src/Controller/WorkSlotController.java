package Controller;

import Entity.WorkSlot;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WorkSlotController {
    private WorkSlot workSlot;
    public WorkSlotController() {
        this.workSlot = new WorkSlot();
    }

    public List<WorkSlot> getWorkSlotsByDate(Date selectedDate) {
        List<WorkSlot> workSlots = new ArrayList<>();

        List<WorkSlot> allWorkSlots = getAllWorkSlots();

        for (WorkSlot workSlot : allWorkSlots) {
            if (workSlot.getDate().equals(selectedDate)) {
                workSlots.add(workSlot);
            }
        }

        return workSlots;
    }

    public List<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlots();
    }
}


