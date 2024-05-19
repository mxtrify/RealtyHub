package Controller.RateReview;

import Entity.RealEstateAgent;

import java.util.ArrayList;

public class ViewReviewControl {
    private RealEstateAgent realEstateAgent;

    public ViewReviewControl() {
        this.realEstateAgent = new RealEstateAgent();
    }

    public ArrayList<RealEstateAgent> getReviewList(int agentID) {
        return realEstateAgent.fetchAgentReviews(agentID);
    }
}
