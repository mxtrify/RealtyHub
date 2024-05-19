package Controller.Property;

import Entity.Property;

public class TrackViewsControl {
    private Property property;

    public TrackViewsControl() {
        this.property = new Property();
    }

    public boolean addViews(int listingID) {
        return property.insertViews(listingID);
    }
}
