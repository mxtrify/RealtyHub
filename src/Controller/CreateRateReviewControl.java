package Controller;

import Entity.RealEstateAgent;

import java.util.ArrayList;

public class CreateRateReviewControl {
    private RealEstateAgent realEstateAgent;

    public CreateRateReviewControl() {
        this.realEstateAgent = new RealEstateAgent();
    }

    public boolean addRateReview(RealEstateAgent newRateReview) {
        return realEstateAgent.insertRateReview(newRateReview);
    }
}
