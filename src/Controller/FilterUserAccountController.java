package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;

public class FilterUserAccountController {
    public void FilterUserAccount(String profileName, DefaultTableModel model) {
        new SystemAdmin().selectByProfileName(profileName, model);
    }
}
