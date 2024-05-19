package Controller.RateReview;

import Entity.RealEstateAgent;

public class CreateRateReviewControl {
    private RealEstateAgent realEstateAgent;

    public CreateRateReviewControl() {
        this.realEstateAgent = new RealEstateAgent();
    }

    public boolean addRateReview(RealEstateAgent newRateReview) {
        return realEstateAgent.insertRateReview(newRateReview);
    }
}
