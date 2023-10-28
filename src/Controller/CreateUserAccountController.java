package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;

import java.util.List;

public class CreateUserAccountController {
    private SystemAdmin systemAdmin;

    public CreateUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }
    public List<String> getProfileList() {
        return systemAdmin.getProfileByName();
    }

    public List<String> getRoleList() {
        return systemAdmin.getRoleByName();
    }

    public void addAccount(UserAccount newUser) {
        systemAdmin.insertAccount(newUser);
    }
}
