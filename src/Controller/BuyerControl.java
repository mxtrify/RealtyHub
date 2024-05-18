package Controller;

import Entity.UserAccount;

public class BuyerControl {
    private UserAccount userAccount;

    public BuyerControl(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    // Get the full name of the logged-in user
    public String getLoggedInUserFullName() {
        return userAccount.getFullName();
    }

    // Get the ID of the logged-in user
    public int getLoggedInUserID() {
        return userAccount.getAccountID();
    }
}
