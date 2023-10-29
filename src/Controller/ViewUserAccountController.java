package Controller;

import Entity.SystemAdmin;
import Entity.UserAccount;
import java.util.ArrayList;
import java.util.List;

public class ViewUserAccountController {
    public ArrayList<UserAccount> getAccountList() {
        return new SystemAdmin().selectAll();
    }

    public List<String> getProfileList() {
        return new SystemAdmin().getProfileByName();
    }
}
