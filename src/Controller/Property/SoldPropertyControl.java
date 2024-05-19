package Controller.Property;

import Entity.Property;

public class SoldPropertyControl {
    private Property property;

    public SoldPropertyControl() {
        this.property = new Property();
    }

    public boolean soldProperty(int ListingID) {
        return property.soldProperty(ListingID);
    }
}
