package Controller.UserProfile;

import Entity.UserProfile;

public class ActivateUserProfileControl {
    private UserProfile userProfile;

    public ActivateUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public boolean activateUserProfile(String profileName) {
        return userProfile.activateUserProfile(profileName);
    }
}
