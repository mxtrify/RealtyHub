package Controller;

import Entity.UserProfile;

public class CreateUserProfileControl {
    private UserProfile userProfile;

    public CreateUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public boolean createUserProfile(String profileName, String profileDesc) {
        return userProfile.createUserProfile(profileName, profileDesc);
    }

}