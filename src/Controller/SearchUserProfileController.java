package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;

public class SearchUserProfileController {
    public void SearchUserProfile(String search, DefaultTableModel model) {
        new SystemAdmin().getProfileName(search, model);
    }
}
