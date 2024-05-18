package Controller.Property;

import Entity.Property;

public class AvailablePropertyControl {
    private Property property;

    public AvailablePropertyControl() {
        this.property = new Property();
    }

    public boolean availableProperty(int ListingID) {
        return property.availableProperty(ListingID);
    }
}
