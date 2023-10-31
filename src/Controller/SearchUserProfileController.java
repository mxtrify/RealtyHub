package Controller;

import Entity.UserProfile;

import javax.swing.table.DefaultTableModel;

public class SearchUserProfileController {
    private UserProfile userProfile;

    public SearchUserProfileController() {
        this.userProfile = new UserProfile();
    }
    public void SearchUserProfile(String search, DefaultTableModel model) {
        userProfile.getProfileName(search, model);
    }
}
