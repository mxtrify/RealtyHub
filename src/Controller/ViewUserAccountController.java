package Controller;

import Entity.SystemAdmin;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ViewUserAccountController {
    public void getAccountList(DefaultTableModel model) {
        new SystemAdmin().selectAll(model);
    }

    public List<String> getProfileList() {
        return new SystemAdmin().getProfileByName();
    }
}
