package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SystemAdminController {
    public void getAccountList(DefaultTableModel model) {
        new SystemAdmin().selectAll(model);
    }

    public List<String> getProfileList() {
        return new SystemAdmin().getProfileByName();
    }

    public List<String> getRoleList() {
        return new SystemAdmin().getRoleByName();
    }
}
