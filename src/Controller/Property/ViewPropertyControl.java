package Controller.Property;

import Entity.Property;

import java.util.ArrayList;

public class ViewPropertyControl {
    private Property property;

    public ViewPropertyControl() {
        this.property = new Property();
    }

    public ArrayList<Property> getPropertyList() {
        return property.selectAllProperty();
    }

    public ArrayList<Property> getNewPropertyList() {
        return property.selectNewProperty();
    }

    public ArrayList<Property> getSoldPropertyList() {
        return property.selectSoldProperty();
    }
}