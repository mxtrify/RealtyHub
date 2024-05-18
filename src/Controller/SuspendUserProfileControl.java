package Controller;

import Entity.UserProfile;

public class SuspendUserProfileControl {
    private UserProfile userProfile;

    public SuspendUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public boolean suspendUserProfile(String profileName) {
        return userProfile.suspendUserProfile(profileName);
    }
}
