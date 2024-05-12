package Controller.UserAccount;

import Entity.UserAccount;

public class SuspendUserAccountControl {
    private UserAccount userAccount;

    public SuspendUserAccountControl() {
        this.userAccount = new UserAccount();
    }

    public boolean suspendUserAccount(String username) {
        return userAccount.suspendUserAccount(username);
    }
}
