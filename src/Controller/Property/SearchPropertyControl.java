package Controller.Property;

import Entity.Property;

import java.util.ArrayList;

public class SearchPropertyControl {
    private Property property;

    public SearchPropertyControl() {
        this.property = new Property();
    }

    public ArrayList<Property> SearchProperty(String search) {
        return property.getPropertyBySearch(search);
    }
}
