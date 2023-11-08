package Controller;

import Entity.Bid;
import Entity.UserAccount;
import Entity.WorkSlot;

import java.sql.Date;

public class ViewMonthlySlotLeft{
    public int viewMonthlySlotLeft(UserAccount u, Date date){
        return new Bid(u.getUsername(), date).getMonthlySlotLeft();
    }
}