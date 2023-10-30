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
    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int waiterAmount) {
        return workSlot.createWorkSlot(date, chefAmount, cashierAmount, waiterAmount);
    }

    public boolean updateRoleAmount(Date date, int roleID, int newAmount) {
        return workSlot.updateRoleAmount(date, roleID, newAmount);

    }
//    public boolean updateWorkSlot(String oldDate, String newDate, int newChefAmount, int newCashierAmount, int newWaiterAmount) {
//        return workSlot.upd
//    }
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            java.util.Date utilDate = dateFormat.parse(oldDate);
//            Date sqlOldDate = new Date(utilDate.getTime());
//
//            java.util.Date newUtilDate = dateFormat.parse(newDate);
//            Date sqlNewDate = new Date(newUtilDate.getTime());
//
//            return workSlot.updateWorkSlot(newChefAmount, newCashierAmount, newWaiterAmount);
//        } catch (ParseException | IllegalArgumentException e) {
//            e.printStackTrace();
//            return false;
//        }



    public boolean deleteWorkSlot(String date) {
        try {
            System.out.println("Format checking: " + date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(date);
            Date sqlDate = new Date(utilDate.getTime());
            System.out.println("Format converted: " + sqlDate);

            return workSlot.deleteWorkSlot(sqlDate);
        } catch (ParseException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlots();
    }
}


