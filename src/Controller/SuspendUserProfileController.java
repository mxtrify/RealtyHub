package Controller;

import Entity.UserProfile;

public class SuspendUserProfileController {
    private UserProfile userProfile;

    public SuspendUserProfileController() {
        this.userProfile = new UserProfile();
    }
    public boolean deleteUserProfile(String profileName) {
        return userProfile.suspendUserProfile(profileName);
    }
}
