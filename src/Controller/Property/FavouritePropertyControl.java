package Controller.Property;

import Entity.Property;

import java.util.ArrayList;

public class FavouritePropertyControl {
    private Property property;

    public FavouritePropertyControl() {
        this.property = new Property();
    }

    public boolean favouriteProperty(int ListingID, int SaverID) {
        return property.insertPropertySave(ListingID, SaverID);
    }

    public ArrayList<Property> getFavouriteProperty(int buyerID) {
        return property.selectFavouriteProperty(buyerID);
    }
}