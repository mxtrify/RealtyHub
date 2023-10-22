package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;

public class SystemAdminController {
    public void getList(DefaultTableModel model) {
        new SystemAdmin().selectAll(model);
    }
}
