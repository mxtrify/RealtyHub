package Controller.Property;

import Entity.Property;

import java.util.ArrayList;

public class CreatePropertyControl {
    private Property property;

    public CreatePropertyControl() {
        this.property = new Property();
    }

    public ArrayList<Integer> getSellerList() {
        return property.getSellerIDList();
    }

    public boolean addProperty(Property newProperty) {
        return property.insertProperty(newProperty);
    }
}