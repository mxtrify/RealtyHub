package Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DBConn;

public class UserAccount {
    private String userId;
    private String firstName;
    private String lastName;
    private String accountType;
    private String accountStatus;

    // Constructor
    public UserAccount(String userId, String firstName, String lastName, String accountType, String accountStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    // Database operations
    public static void addUserAccount(String firstName, String lastName, String accountType, String accountStatus) throws SQLException {
        String sql = "INSERT INTO user (first_name, last_name, account_type, account_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, accountType);
            pstmt.setString(4, accountStatus);
            pstmt.executeUpdate();
        }
    }

    public static void updateUserAccount(String userId, String firstName, String lastName, String accountType, String accountStatus) throws SQLException {
        String sql = "UPDATE user SET first_name = ?, last_name = ?, account_type = ?, account_status = ? WHERE userid = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, accountType);
            pstmt.setString(4, accountStatus);
            pstmt.setString(5, userId);
            pstmt.executeUpdate();
        }
    }

    public static void suspendUserAccount(String userId, boolean isActive) throws SQLException {
        String sql = "UPDATE user SET account_status = ? WHERE userid = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isActive ? "active" : "suspended");
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet searchUserAccounts(String criteria) throws SQLException {
        String sql = "SELECT * FROM user WHERE first_name LIKE ? OR last_name LIKE ? OR userid LIKE ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + criteria + "%");
            pstmt.setString(2, "%" + criteria + "%");
            pstmt.setString(3, "%" + criteria + "%");
            return pstmt.executeQuery();
        }
    }
}