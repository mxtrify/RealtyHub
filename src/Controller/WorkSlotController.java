package Controller;

import Entity.Property;
import Entity.WorkSlot;

import java.sql.Date;
import java.util.ArrayList;

public class WorkSlotController {
    private WorkSlot workSlot;
    public WorkSlotController() {
        this.workSlot = new WorkSlot();
    }

    public ArrayList<Property> getWorkSlotsByDate(Date selectedDate) {
        ArrayList<Property> workSlots = new ArrayList<>();
        ArrayList<Property> allWorkSlots = getAllWorkSlots();

        for (Property workSlot : allWorkSlots) {
           /* if (workSlot.getDate().equals(selectedDate)) {
                workSlots.add(workSlot);
            }*/
        }

        return workSlots;
    }

    public ArrayList<Property> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlotsMax();
    }
}


