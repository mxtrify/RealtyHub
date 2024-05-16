package Controller.Property;

import Entity.Property;

import java.util.ArrayList;

public class CreatePropertyControl {
    private Property userAccount;

    public CreatePropertyControl() {
        this.userAccount = new Property();
    }

    public ArrayList<Integer> getSellerList() {
        return userAccount.getSellerIDList();
    }

    public boolean addProperty(Property newProperty) {
        return userAccount.insertProperty(newProperty);
    }
}