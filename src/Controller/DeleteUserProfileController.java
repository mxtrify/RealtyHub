package Controller;

import Entity.SystemAdmin;

public class DeleteUserProfileController {

    public boolean deleteUserProfile(String profileName) {
        return new SystemAdmin().deleteProfile(profileName);
    }
}
