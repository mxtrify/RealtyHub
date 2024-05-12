package Controller.UserProfile;

import Entity.UserProfile;

public class CreateUserProfileControl {
    private UserProfile userProfile;

    public CreateUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public boolean createUserProfile(String profileName, String profileInfo) {
        return userProfile.createUserProfile(profileName, profileInfo);
    }

}