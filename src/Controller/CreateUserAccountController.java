package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;

import java.util.List;

public class CreateUserAccountController {
    private SystemAdmin UA;
    public List<String> getProfileList() {
        return new SystemAdmin().getProfileByName();
    }

    public List<String> getRoleList() {
        return new SystemAdmin().getRoleByName();
    }

    public void addAccount(UserAccount newUser) {
        new SystemAdmin().insertAccount(newUser);
    }
}
