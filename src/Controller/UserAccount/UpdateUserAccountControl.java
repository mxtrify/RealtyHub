package Controller.UserAccount;

import Entity.UserAccount;

import java.util.ArrayList;

public class UpdateUserAccountControl {
    private UserAccount userAccount;

    public UpdateUserAccountControl() {
        this.userAccount = new UserAccount();
    }

    public UserAccount getSelectedAccount(String username) {
        return userAccount.getSelectedAccount(username);
    }

    public ArrayList<String> getProfileList() {
        return userAccount.getProfileByType();
    }

    public boolean UpdateUserAccount(UserAccount updatedUser) {
        return userAccount.updateUserAccount(updatedUser);
    }
}
