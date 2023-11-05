package Controller;

import Entity.Bid;
import Entity.UserAccount;

import java.sql.Date;

public class ViewPastWorkController {
    public Object[][] getPast(UserAccount userAccount, Date date){
        return new Bid(userAccount.getUsername(), date).getPastWork();
    }
}
