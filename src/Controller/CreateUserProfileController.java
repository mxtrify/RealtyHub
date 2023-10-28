package Controller;

import Entity.SystemAdmin;

public class CreateUserProfileController {
    private SystemAdmin systemAdmin;

    public CreateUserProfileController() {
        this.systemAdmin = new SystemAdmin();
    }
    public boolean createUserProfile(String profileName, String profileDesc) {
        return systemAdmin.insertProfile(profileName, profileDesc);
    }

}
