package Controller.UserAccount;

import Entity.UserAccount;

import java.util.ArrayList;

public class FilterUserAccountControl {
    private UserAccount userAccount;

    public FilterUserAccountControl() {
        this.userAccount = new UserAccount();
    }
    public ArrayList<String> getProfileList() {
        return userAccount.getProfileByType();
    }
    public ArrayList<UserAccount> FilterUserAccount(String profileType) {
        return userAccount.selectByProfileType(profileType);
    }
}
