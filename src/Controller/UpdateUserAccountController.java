package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;

public class UpdateUserAccountController {
    private SystemAdmin systemAdmin;

    public UpdateUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }

    public UserAccount getSelectedAccount(String username) {
        return systemAdmin.getSelectedAccount(username);
    }

    public UserAccount UpdateUserAccount() {
        return null;
    }
}
