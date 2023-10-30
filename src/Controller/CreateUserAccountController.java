package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;

import java.util.ArrayList;

public class CreateUserAccountController {
    private SystemAdmin systemAdmin;

    public CreateUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }
    public ArrayList<String> getProfileList() {
        return systemAdmin.getProfileByName();
    }

    public ArrayList<String> getRoleList() {
        return systemAdmin.getRoleByName();
    }

    public boolean addAccount(UserAccount newUser) {
        return systemAdmin.insertAccount(newUser);
    }
}
