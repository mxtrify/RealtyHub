package Controller.Property;

import Entity.Property;

public class UpdatePropertyControl {
    private Property property;

    public UpdatePropertyControl() {
        this.property = new Property();
    }

    public Property getSelectedProperty(int ListingID) {
        return property.getSelectedProperty(ListingID);
    }

    public boolean UpdateProperty(Property updatedProperty) {
        return property.updateProperty(updatedProperty);
    }
}