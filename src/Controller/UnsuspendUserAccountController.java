package Controller;

import Entity.SystemAdmin;

public class UnsuspendUserAccountController {
    private SystemAdmin systemAdmin;

    public UnsuspendUserAccountController() {
        this.systemAdmin = new SystemAdmin();
    }

    public boolean unsuspendUserAccount(String username) {
        return systemAdmin.unsuspendUserAccount(username);
    }
}
