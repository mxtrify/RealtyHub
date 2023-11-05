package Controller;

import Entity.Bid;
import Entity.UserAccount;

public class ViewAllPastController {
    public Object[][] getAllPast(UserAccount userAccount){
        return new Bid(userAccount.getUsername(), null).getAllPastWork();
    }
}
