package Controller.RateReview;

import Entity.RealEstateAgent;

import java.util.ArrayList;

public class ViewRatingsControl {
    private RealEstateAgent realEstateAgent;

    public ViewRatingsControl() {
        this.realEstateAgent = new RealEstateAgent();
    }

    public ArrayList<RealEstateAgent> getRatingsList(int agentID) {
        return realEstateAgent.fetchAgentRatings(agentID);
    }
}
