package Controller;

import Entity.WorkSlot;

import java.sql.Date;

public class SearchAvailWSController {
    public Object[][] getWorkSlot(Date date){
        return new WorkSlot(date).getWS();
    }

}
