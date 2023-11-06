package Controller;

import Entity.UserAccount;

public class SetMaxSlotController {
    private UserAccount userAccount;

    public SetMaxSlotController() {
        this.userAccount = new UserAccount();
    }

    public void setMaxSlot(UserAccount u) {
        userAccount.setMaxSlot(u);
    }
}
