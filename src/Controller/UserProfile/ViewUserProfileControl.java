package Controller.UserProfile;

import Entity.UserProfile;

import java.util.ArrayList;

public class ViewUserProfileControl {
    private UserProfile userProfile;

    public ViewUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public ArrayList<UserProfile> getProfileList() {
        return userProfile.selectAllProfile();
    }
}
