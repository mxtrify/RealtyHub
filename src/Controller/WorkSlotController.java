package Controller;

import Entity.WorkSlot;
import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


