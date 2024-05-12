package Controller.UserAccount;

import Entity.UserAccount;

import java.util.ArrayList;

public class SearchUserAccountControl {
    private UserAccount userAccount;

    public SearchUserAccountControl() {
        this.userAccount = new UserAccount();
    }

    public ArrayList<UserAccount> searchUserAccount(String search) {
        return userAccount.getUserAccountByUsername(search);
    }
}
