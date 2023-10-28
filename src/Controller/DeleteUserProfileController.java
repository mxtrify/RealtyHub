package Controller;

import Entity.SystemAdmin;

public class DeleteUserProfileController {
    private SystemAdmin systemAdmin;

    public DeleteUserProfileController() {
        this.systemAdmin = new SystemAdmin();
    }
    public boolean deleteUserProfile(String profileName) {
        return systemAdmin.deleteProfile(profileName);
    }
}
