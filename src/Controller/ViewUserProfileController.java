package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;

public class ViewUserProfileController {
    public void getProfileList(DefaultTableModel model) {
        new SystemAdmin().selectAllProfile(model);
    }
}
