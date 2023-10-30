package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;

import java.util.ArrayList;

public class FilterUserAccountController {
    private SystemAdmin systemAdmin;

    public FilterUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }
    public ArrayList<UserAccount> FilterUserAccount(String profileName) {
        return systemAdmin.selectByProfileName(profileName);
    }
}
