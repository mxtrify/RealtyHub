package Controller.UserProfile;

import Entity.UserProfile;

public class UpdateUserProfileControl {
    private UserProfile userProfile;

    public UpdateUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public boolean UpdateUserProfile(String profileType, String newProfileInfo) {
        return userProfile.updateUserProfile(profileType, newProfileInfo);
    }
}
