package Controller;

import Entity.SystemAdmin;

public class UpdateUserProfileController {
    private SystemAdmin systemAdmin;

    public UpdateUserProfileController() {
        this.systemAdmin = new SystemAdmin();
    }

    public boolean UpdateUserProfile(String profileName, String newProfileDesc) {
        return systemAdmin.updateUserProfile(profileName, newProfileDesc);
    }
}
