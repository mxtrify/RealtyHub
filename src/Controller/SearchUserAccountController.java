package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;

public class SearchUserAccountController {
    public void searchUserAccount(String search, DefaultTableModel model) {
        new SystemAdmin().getUserAccountByUsername(search, model);
    }
}
