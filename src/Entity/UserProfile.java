package Entity;

import Database.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfile {
    private String userId;
    private String username;
    private String password;

    public UserProfile(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Database operations
    public static void addUserProfile(String username, String password) throws SQLException {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        }
    }

    public static void updateUserProfile(String userId, String username, String password) throws SQLException {
        String sql = "UPDATE user SET username = ?, password = ? WHERE userid = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, userId);
            pstmt.executeUpdate();
        }
    }

    public static void suspendUserProfile(String userId, boolean isActive) throws SQLException {
        String sql = "UPDATE user SET account_status = ? WHERE userid = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isActive ? "active" : "suspended");
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet searchUserProfiles(String criteria) throws SQLException {
        String sql = "SELECT * FROM user WHERE username LIKE ? OR userid LIKE ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + criteria + "%");
            pstmt.setString(2, "%" + criteria + "%");
            return pstmt.executeQuery();
        }
    }
}