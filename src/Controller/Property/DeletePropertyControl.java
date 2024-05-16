package Controller.Property;

import Entity.Property;

public class DeletePropertyControl {
    private Property property;

    public DeletePropertyControl() {
        this.property = new Property();
    }

    public boolean deleteProperty(int ListingID) {
        return property.deleteProperty(ListingID);
    }
}
