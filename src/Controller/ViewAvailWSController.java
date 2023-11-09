package Controller;

import Entity.WorkSlot;

import java.sql.Date;

public class ViewAvailWSController {
    public Object[][] getAllWorkSlots(){return new WorkSlot().getAllWS();}
}
