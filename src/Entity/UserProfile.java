package Entity;

import Config.DBConfig;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class UserProfile {
    private UserProfile userProfile;
    private int profileID;
    private String profileName;
    private String profileDesc;
    private boolean profileStatus;

    public UserProfile() {
        this.profileID = 0;
        this.profileName = "";
        this.profileDesc = "";
        this.profileStatus = false;
    }

    public UserProfile(int profileID, String profileName, String profileDesc, boolean status) {
        this.profileID = profileID;
        this.profileName = profileName;
        this.profileDesc = profileDesc;
        this.profileStatus = status;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public boolean isProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(boolean status) {
        this.profileStatus = status;
    }

    // Create
    public boolean createUserProfile(String profileName, String profileDesc) {
        String query = "INSERT INTO profile (profile_name, profile_desc, profile_status) VALUES (?, ?, ?)";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileName);
            preparedStatement.setString(2, profileDesc);
            preparedStatement.setBoolean(3, true);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Read (View)
    public ArrayList<UserProfile> selectAllProfile() {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        String query = "SELECT * FROM profile";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int profileID = resultSet.getInt("profile_id");
                String profileName = resultSet.getString("profile_name");
                String profileDesc = resultSet.getString("profile_desc");
                boolean profileStatus = resultSet.getBoolean("profile_status");
                userProfile = new UserProfile(profileID, profileName, profileDesc, profileStatus);
                userProfiles.add(userProfile);
            }
            return userProfiles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update (Edit)
    public boolean updateUserProfile(String profileName, String newProfileDesc) {
        String query = "UPDATE profile SET profile_desc = ? WHERE profile_name = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newProfileDesc);
            preparedStatement.setString(2, profileName);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete (Suspend)
    public boolean suspendUserProfile(String profileName) {
        String query = "UPDATE profile SET profile_status = 0 WHERE profile_name = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileName);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Search
    public void getProfileName(String search, DefaultTableModel model) {
        String query = "SELECT profile_name, profile_desc FROM profile WHERE profile_name LIKE ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            model.setRowCount(0);
            while(resultSet.next()) {
                String profileName = resultSet.getString("profile_name");
                String profileDesc = resultSet.getString("profile_desc");
                model.addRow(new Object[]{profileName, profileDesc});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
