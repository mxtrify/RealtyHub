package Controller;

import Entity.Bid;

public class rejectBidController {
    public boolean processReject(Bid bid){
        return new Bid(bid.getBidId(), bid.getName(), bid.getRole(), bid.getDate()).rejectBid();
    }
}
