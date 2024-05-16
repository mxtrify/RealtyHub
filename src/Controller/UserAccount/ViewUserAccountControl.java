package Controller.UserAccount;

import Entity.UserAccount;

import java.util.ArrayList;

public class ViewUserAccountControl {
    private UserAccount userAccount;

    public ViewUserAccountControl() {
        this.userAccount = new UserAccount();
    }

    public ArrayList<UserAccount> getAccountList() {
        return userAccount.selectAll();
    }
}
