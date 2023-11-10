package Controller;

import Entity.UserAccount;

public class SetRoleController {
    private UserAccount userAccount;

    public SetRoleController() {
        this.userAccount = new UserAccount();
    }

    public void setRole(UserAccount u) {
        userAccount.setRole(u);
    }
}
