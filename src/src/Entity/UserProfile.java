package Entity;

import java.sql.*;
import java.util.ArrayList;

import Database.DBConfig;

public class UserProfile {
    private Connection conn;

    private int profileID;
    private String profileType;
    private boolean profileStatus;
    private String profileInfo;

    public UserProfile() {
        this.profileID = 0;
        this.profileType = "";
        this.profileStatus = false;
        this.profileInfo = "";
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

    public UserProfile(String profileType, boolean status, String profileInfo) {
        this.profileType = profileType;
        this.profileStatus = status;
        this.profileInfo = profileInfo;
    }

    public UserProfile(int profileID, String profileType, boolean status, String profileInfo) {
        this.profileID = profileID;
        this.profileType = profileType;
        this.profileStatus = status;
        this.profileInfo = profileInfo;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public boolean isProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(boolean status) {
        this.profileStatus = status;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    // Create User Profile
    public boolean createUserProfile(String profileType, String profileInfo) {
        try {
            conn = new DBConfig().getConnection();

            String query = "SELECT * FROM user_profile WHERE profileType = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileType);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return false;
            }else {
                query = "INSERT INTO user_profile (profileType, profileStatus, profileInfo) VALUES (?, ?, ?)";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, profileType);
                preparedStatement.setBoolean(2, true);
                preparedStatement.setString(3, profileInfo);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            // Close the connection in a final block to ensure it happens even if an exception occurs.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException during closing.
            }
        }
    }

    // View User Profile
    public ArrayList<UserProfile> selectAllProfile() {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        String query = "SELECT * FROM user_profile";
        try {
            conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int profileID = resultSet.getInt("profileID");
                String profileType = resultSet.getString("profileType");
                boolean profileStatus = resultSet.getBoolean("profileStatus");
                String profileInfo = resultSet.getString("profileInfo");
                UserProfile userProfile = new UserProfile(profileID, profileType, profileStatus, profileInfo);
                userProfiles.add(userProfile);
            }
            return userProfiles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the connection in a final block to ensure it happens even if an exception occurs.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException during closing.
            }
        }
    }

    // Update User Profile
    public boolean updateUserProfile(String profileType, String newProfileInfo) {
        String query = "UPDATE user_profile SET profileInfo = ? WHERE profileType = ?";
        try {
            conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newProfileInfo);
            preparedStatement.setString(2, profileType);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the connection in a final block to ensure it happens even if an exception occurs.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException during closing.
            }
        }
    }

    // Suspend User Profile
    public boolean suspendUserProfile(String profileType) {
        String query = "UPDATE user_profile SET profileStatus = 0 WHERE profileType = ?";
        try {
            conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileType);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the connection in a final block to ensure it happens even if an exception occurs.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException during closing.
            }
        }
    }

    // Activate
    public boolean activateUserProfile(String profileType) {
        String query = "UPDATE user_profile SET profileStatus = 1 WHERE profileType = ?";
        try {
            conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileType);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the connection in a final block to ensure it happens even if an exception occurs.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException during closing.
            }
        }
    }

    // Search User Profile
    public ArrayList<UserProfile> searchProfiles(String search) {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        String query = "SELECT profileType, profileStatus, profileInfo FROM user_profile WHERE profileType LIKE ?";
        try {
            conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String profileType = resultSet.getString("profileType");
                boolean profileStatus = resultSet.getBoolean("profileStatus");
                String profileInfo = resultSet.getString("profileInfo");
                UserProfile userProfile = new UserProfile(profileType, profileStatus, profileInfo);
                userProfiles.add(userProfile);
            }
            return userProfiles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the connection in a final block to ensure it happens even if an exception occurs.
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException during closing.
            }
        }
    }
}