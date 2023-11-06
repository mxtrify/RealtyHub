package Controller;

import Entity.Bid;
import Entity.UserAccount;

import java.sql.Date;

public class ViewUpcomingController {
    public Object[][] getUpcoming(UserAccount userAccount, Date date){
        return new Bid(userAccount.getUsername(), date).getUpcomingWork();
    }
}
