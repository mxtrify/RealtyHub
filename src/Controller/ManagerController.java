package Controller;

import Entity.Bid;

public class ManagerController {
    public Object[][] viewBidData(String value){
        return new Bid().getBids(value);
    }
}
