package Controller;

import Entity.SystemAdmin;

public class SuspendUserAccountController {
    private SystemAdmin systemAdmin;

    public SuspendUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }

    public boolean suspendUserAccount(String username) {
        return systemAdmin.suspendUserAccount(username);
    }
}
