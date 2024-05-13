package Controller;

import Entity.UserProfile;

import java.util.ArrayList;

public class SearchUserProfileControl {
    private UserProfile userProfile;

    public SearchUserProfileControl() {
        this.userProfile = new UserProfile();
    }

    public ArrayList<UserProfile> SearchUserProfile(String search) {
        return userProfile.searchProfiles(search);
    }
}