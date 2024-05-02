package Controller;

import Entity.UserProfile;
import Entity.UserAccount;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SysAdminControl {

    // Methods related to UserProfile operations
    public void addUserProfile(String username, String password) {
        try {
            UserProfile.addUserProfile(username, password);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }

    public void updateUserProfile(String userId, String username, String password) {
        try {
            UserProfile.updateUserProfile(userId, username, password);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }

    public void suspendUserProfile(String userId, boolean isActive) {
        try {
            UserProfile.suspendUserProfile(userId, isActive);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }

    public ResultSet searchUserProfiles(String criteria) {
        try {
            return UserProfile.searchUserProfiles(criteria);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            return null;
        }
    }

    // Methods related to UserAccount operations
    public List<UserAccount> searchUserAccounts(String criteria) {
        try {
            return UserAccount.searchUserAccounts(criteria);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
            return null;
        }
    }

    /*public void addUserAccount(String userId, String fname, String lname, String accType, String accStatus) {
        try {
            UserAccount.addUserAccount(userId, fname, lname, accType, accStatus);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }

    public void updateUserAccount(String userId, String fname, String lname, String accType, String accStatus) {
        try {
            UserAccount.updateUserAccount(userId, fname, lname, accType, accStatus);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }

    public void deleteUserAccount(String userId) {
        try {
            UserAccount.deleteUserAccount(userId);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }*/
}