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

    public ArrayList<Property> SearchNewProperty(String search) {
        return property.getNewPropertyBySearch(search);
    }

    public ArrayList<Property> SearchSoldProperty(String search) {
        return property.getSoldPropertyBySearch(search);
    }
}
