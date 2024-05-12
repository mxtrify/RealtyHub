package Controller.UserAccount;

import Entity.UserAccount;

import java.util.ArrayList;

public class CreateUserAccountControl {
    private UserAccount userAccount;

    public CreateUserAccountControl() {
        this.userAccount = new UserAccount();
    }
    public ArrayList<String> getProfileList() {
        return userAccount.getProfileByName();
    }

    public boolean addAccount(UserAccount newUser) {
        return userAccount.insertAccount(newUser);
    }
}
