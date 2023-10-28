package Controller;

import Entity.SystemAdmin;

public class CreateUserProfileController {
    public boolean createUserProfile(String profileName, String profileDesc) {
        return new SystemAdmin().insertProfile(profileName, profileDesc);
    }

}
