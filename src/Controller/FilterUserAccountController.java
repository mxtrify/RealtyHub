package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;

public class FilterUserAccountController {
    private SystemAdmin systemAdmin;

    public FilterUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }
    public void FilterUserAccount(String profileName, DefaultTableModel model) {
        systemAdmin.selectByProfileName(profileName, model);
    }
}
