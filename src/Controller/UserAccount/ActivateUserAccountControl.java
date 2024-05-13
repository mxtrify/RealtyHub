package Controller.UserAccount;

import Entity.UserAccount;

public class ActivateUserAccountControl {
    private UserAccount userAccount;

    public ActivateUserAccountControl() {
        this.userAccount = new UserAccount();
    }

    public boolean activateUserAccount(String username) {
        return userAccount.activateUserAccount(username);
    }
}
