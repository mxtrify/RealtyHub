package Controller;

import Entity.WorkSlot;

import java.sql.Date;

public class UpdateWorkSlotController {
    private WorkSlot workSlot;

    public UpdateWorkSlotController() {this.workSlot = new WorkSlot();}

    public boolean updateRoleAmount(Date date, int roleID, int newAmount) {
        return workSlot.updateRoleAmount(date, roleID, newAmount);

    }
}
