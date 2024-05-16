package Controller;

import Entity.UserAccount;

public class AgentControl {
    private UserAccount userAccount;

    public AgentControl(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    // Get the full name of the logged-in user
    public String getLoggedInUserFullName() {
        return userAccount.getFullName();
    }
}
