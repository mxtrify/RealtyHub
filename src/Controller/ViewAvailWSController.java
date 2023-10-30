package Controller;

import Entity.WorkSlot;

import java.sql.Date;

public class ViewAvailWSController {
    public Object[][] getWorkSlot(Date date){
        return new WorkSlot(date).getWS();
    }
}
