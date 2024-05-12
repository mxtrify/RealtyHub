package Entity;

import Database.DBConn;

import java.sql.*;
import java.util.ArrayList;

public class UserProfile {
    private Connection conn;

    private int profileID;
    private String profileType;
    private String profileInfo;
    private boolean profileStatus;

    public UserProfile() {
        this.profileID = 0;
        this.profileType = "";
        this.profileInfo = "";
        this.profileStatus = false;
    }

    public UserProfile(int profileID) {
        this.profileID = profileID;
    }

    public UserProfile(String profileType) {
        this.profileType = profileType;
    }

    public UserProfile(String profileType, boolean status) {
        this.profileType = profileType;
        this.profileStatus = status;
    }

    public UserProfile(String profileType, String profileInfo, boolean status) {
        this.profileType = profileType;
        this.profileStatus = status;
        this.profileInfo = profileInfo;
    }

    public UserProfile(int profileID, String profileType, String profileInfo, boolean status) {
        this.profileID = profileID;
        this.profileType = profileType;
        this.profileStatus = status;
        this.profileInfo = profileInfo;
    }


    public int getProfileID() { return profileID; }
    public String getProfileType() { return profileType; }
    public String getProfileInfo() { return profileInfo; }
    public boolean isProfileStatus() { return profileStatus; }

    // Create User Profile
    public boolean createUserProfile(String profileType, String profileInfo) {
        try {
            conn = new DBConn().getConnection();

            String query = "SELECT * FROM user_profile WHERE profileType = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileType);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return false;
            }else {
                query = "INSERT INTO user_profile (profileType, profileInfo, profileStatus) VALUES (?, ?, ?)";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, profileType);
                preparedStatement.setString(2, profileInfo);
                preparedStatement.setBoolean(3, true);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // View User Profile
    public ArrayList<UserProfile> selectAllProfile() {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        String query = "SELECT * FROM user_profile";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int profileID = resultSet.getInt("profileID");
                String profileType = resultSet.getString("profileType");
                String profileInfo = resultSet.getString("profileInfo");
                boolean profileStatus = resultSet.getBoolean("profileStatus");
                UserProfile userProfile = new UserProfile(profileID, profileType, profileInfo, profileStatus);
                userProfiles.add(userProfile);
            }
            return userProfiles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Update User Profile
    public boolean updateUserProfile(String profileType, String newProfileInfo) {
        String query = "UPDATE user_profile SET profileInfo = ? WHERE profileType = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newProfileInfo);
            preparedStatement.setString(2, profileType);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Suspend User Profile
    public boolean suspendUserProfile(String profileType) {
        String query = "UPDATE user_profile SET profileStatus = 0 WHERE profileType = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileType);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Activate
    public boolean activateUserProfile(String profileType) {
        String query = "UPDATE user_profile SET profileStatus = 1 WHERE profileType = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileType);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Search User Profile
    public ArrayList<UserProfile> searchProfiles(String search) {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        String query = "SELECT profileType, profileStatus, profileInfo FROM user_profile WHERE profileType LIKE ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String profileType = resultSet.getString("profileType");
                String profileInfo = resultSet.getString("profileInfo");
                boolean profileStatus = resultSet.getBoolean("profileStatus");
                UserProfile userProfile = new UserProfile(profileType, profileInfo, profileStatus);
                userProfiles.add(userProfile);
            }
            return userProfiles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Closes the database connection to release resources.
    private void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}