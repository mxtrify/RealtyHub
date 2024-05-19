package Controller;

import Entity.UserAccount;

import java.util.ArrayList;

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

    // Get the profileType of the logged-in user
    public int getLoggedInUserType() {
        return userAccount.getProfileID();
    }

    // Get a list of agentIDs
    public ArrayList<Integer> getAgentList() {
        return userAccount.getAgentIDList();
    }
}
