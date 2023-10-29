package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;

import java.util.List;

public class UpdateUserAccountController {
    private SystemAdmin systemAdmin;

    public UpdateUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }

    public UserAccount getSelectedAccount(String username) {
        return systemAdmin.getSelectedAccount(username);
    }

    public List<String> getProfileList() {
        return systemAdmin.getProfileByName();
    }

    public List<String> getRoleList() {
        return systemAdmin.getRoleByName();
    }

    public boolean UpdateUserAccount(UserAccount updatedUser) {
        return systemAdmin.updateUserAccount(updatedUser);
    }
}
