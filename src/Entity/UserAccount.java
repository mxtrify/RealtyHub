package Entity;

import Database.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private String userId;
    private String firstName;
    private String lastName;
    private String accountType;
    private String accountStatus;

    public UserAccount(String userId, String firstName, String lastName, String accountType, String accountStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

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
    public static List<UserAccount> searchUserAccounts(String criteria) throws SQLException {
        List<UserAccount> accounts = new ArrayList<>();
        String sql = "SELECT userid, fname, lname, accType, accStatus FROM user_accounts WHERE fname LIKE ? OR lname LIKE ? OR userid LIKE ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + criteria + "%");
            pstmt.setString(2, "%" + criteria + "%");
            pstmt.setString(3, "%" + criteria + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(new UserAccount(
                            rs.getString("userid"),
                            rs.getString("fname"),
                            rs.getString("lname"),
                            rs.getString("accType"),
                            rs.getString("accStatus")));
                }
            }
        }
        return accounts;
    }
}
