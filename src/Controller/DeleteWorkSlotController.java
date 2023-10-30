package Controller;

import Entity.WorkSlot;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DeleteWorkSlotController {
    private WorkSlot workSlot;

    public DeleteWorkSlotController() {this.workSlot = new WorkSlot();}

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
}
