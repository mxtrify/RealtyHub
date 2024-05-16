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
}