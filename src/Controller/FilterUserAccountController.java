package Controller;

import Entity.UserAccount;

import java.util.ArrayList;

public class FilterUserAccountController {
    private UserAccount userAccount;

    public FilterUserAccountController() {
        this.userAccount = new UserAccount();
    }
    public ArrayList<UserAccount> FilterUserAccount(String profileName) {
        return userAccount.selectByProfileName(profileName);
    }
}
