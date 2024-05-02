package Controller;

import Entity.UserProfile;
import Entity.UserAccount;
import javax.swing.*;

public class SysAdminControl {
    // Methods related to user profiles
    public void createUserProfile(String username, String password) {
        try {
            UserProfile.addUserProfile(username, password);
            JOptionPane.showMessageDialog(null, "User profile created successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to create user profile: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateUserProfile(String userId, String username, String password) {
        try {
            UserProfile.updateUserProfile(userId, username, password);
            JOptionPane.showMessageDialog(null, "User profile updated successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to update user profile: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void suspendUserProfile(String userId, boolean isActive) {
        try {
            UserProfile.suspendUserProfile(userId, isActive);
            String status = isActive ? "reactivated" : "suspended";
            JOptionPane.showMessageDialog(null, "User profile has been " + status + ".");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to suspend user profile: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchUserProfiles(String criteria) {
        // Implementation of search should update the UI based on the result set.
        // For now, it's a placeholder.
    }

    // Methods related to user accounts
    public void createUserAccount(String firstName, String lastName, String accountType, String accountStatus) {
        try {
            UserAccount.addUserAccount(firstName, lastName, accountType, accountStatus);
            JOptionPane.showMessageDialog(null, "User account created successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to create user account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateUserAccount(String userId, String firstName, String lastName, String accountType, String accountStatus) {
        try {
            UserAccount.updateUserAccount(userId, firstName, lastName, accountType, accountStatus);
            JOptionPane.showMessageDialog(null, "User account updated successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to update user account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void suspendUserAccount(String userId, boolean isActive) {
        try {
            UserAccount.suspendUserAccount(userId, isActive);
            String status = isActive ? "reactivated" : "suspended";
            JOptionPane.showMessageDialog(null, "User account has been " + status + ".");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to suspend user account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchUserAccounts(String criteria) {
        // Implementation of search should update the UI based on the result set.
        // For now, it's a placeholder.
    }
}