package Controller;

import Entity.UserProfile;

import java.util.ArrayList;

public class UserProfileControl {
    private UserProfile userProfile;

    public UserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public ArrayList<UserProfile> getProfileList() {
        return userProfile.selectAllProfile();
    }
}