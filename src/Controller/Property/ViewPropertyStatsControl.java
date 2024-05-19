package Controller.Property;

import Entity.Property;

import java.util.ArrayList;

public class ViewPropertyStatsControl {
    private Property property;

    public ViewPropertyStatsControl() {
        this.property = new Property();
    }

    public ArrayList<Property> getViewsList(int sellerID) {
        return property.selectViewsList(sellerID);
    }

    public ArrayList<Property> getFavouritesList(int sellerID) {
        return property.selectFavouriteList(sellerID);
    }
}
