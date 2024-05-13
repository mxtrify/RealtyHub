package Controller;

import Entity.UserAccount;

public class SysAdminControl {
    private UserAccount userAccount;

    public SysAdminControl(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    // Get the full name of the logged-in user
    public String getLoggedInUserFullName() {
        return userAccount.getFullName();
    }
}