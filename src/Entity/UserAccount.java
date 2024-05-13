package Entity;

import Database.DBConn;
import java.sql.*;
import java.util.ArrayList;

public class UserAccount {
    private Connection conn;
    private String username;
    private String password;
    private String fName;
    private String lName;
    private UserProfile userProfile;
    private boolean status;

    // Default constructor: Initializes a new user account with empty or default values.
    public UserAccount() {
        this.username = "";
        this.password = "";
        this.fName = "";
        this.lName = "";
        this.userProfile = new UserProfile();
        this.status = false;
    }

    // Parameterized constructor: Initializes a new user account with specific values for basic user information and profile.
    public UserAccount(String username, String password, String fName, String lName, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.userProfile = userProfile;
    }

    // Extended parameterized constructor: Includes account status along with basic user and profile information.
    public UserAccount(String username, String password, String fName, String lName, UserProfile userProfile, boolean status) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.userProfile = userProfile;
        this.status = status;
    }

    // Getters and setters: Provide access and update capabilities for the user account properties.
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getfName() { return fName; }
    public String getlName() { return lName; }
    public UserProfile getUserProfile() { return userProfile; }
    public boolean isStatus() { return status; }

    // Combines and returns the full name of the user.
    public String getFullName() {
        return String.format("%s %s", getfName(), getlName());
    }

    // Validates login credentials against the database and returns the corresponding user account if successful.
    public UserAccount validateLogin(String username, String password) {
        String query = "SELECT username, password, fName, lName, profileType, accountStatus, profileStatus FROM user_account INNER JOIN user_profile ON  user_account.profileID = user_profile.profileID WHERE username = ? AND password = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                String profileType = resultSet.getString("profileType");
                boolean accountStatus = resultSet.getBoolean("accountStatus");
                boolean profileStatus = resultSet.getBoolean("profileStatus");
                UserProfile userProfile = new UserProfile(profileType, profileStatus);
                if(resultSet.getString("profileType").equals("System Admin")) {
                    return new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                } else if (resultSet.getString("profileType").equals("Real Estate Agent")) {
                    return new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                } else if (resultSet.getString("profileType").equals("Buyer")) {
                    return new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                } else if (resultSet.getString("profileType").equals("Seller")) {
                    return new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                } else {
                    return new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Inserts a new user account into the database if the username does not already exist.
    public boolean insertAccount(UserAccount newUser) {
        try {
            conn = new DBConn().getConnection();

            String query = "SELECT * FROM user_account WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newUser.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return false;
            }else{
                query = "INSERT INTO user_account (username, password, fName, lName, profileID, accountStatus) VALUES (?, ?, ?, ?, ?, ?)";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, newUser.getUsername());
                preparedStatement.setString(2, newUser.getPassword());
                preparedStatement.setString(3, newUser.getfName());
                preparedStatement.setString(4, newUser.getlName());
                preparedStatement.setInt(5, newUser.getUserProfile().getProfileID());
                preparedStatement.setBoolean(6, true);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Retrieves all user accounts from the database.
    public ArrayList<UserAccount> selectAll() {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, password, fName, lName, profileType, accountStatus FROM user_account INNER JOIN user_profile ON user_account.profileID = user_profile.profileID";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                String profileType = resultSet.getString("profileType");
                boolean accountStatus = resultSet.getBoolean("accountStatus");
                UserProfile userProfile = new UserProfile(profileType);
                UserAccount userAccount = new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                userAccounts.add(userAccount);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Updates details for an existing user account in the database.
    public boolean updateUserAccount(UserAccount updatedUser) {
        Connection conn = null;
        try {
            conn = new DBConn().getConnection();
            String query = "UPDATE user_account SET password = ?, fname = ?, lname = ?, profileID = ? WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, updatedUser.getPassword());
            preparedStatement.setString(2, updatedUser.getfName());
            preparedStatement.setString(3, updatedUser.getlName());
            preparedStatement.setInt(4, updatedUser.getUserProfile().getProfileID()); // Set profile_id based on UserProfile
            preparedStatement.setString(5, updatedUser.getUsername());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Suspend User Account
    public boolean suspendUserAccount(String username) {
        String query = "UPDATE user_account SET accountStatus = 0 WHERE username = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Activate
    public boolean activateUserAccount(String username) {
        String query = "UPDATE user_account SET accountStatus = 1 WHERE username = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Search User Account
    public ArrayList<UserAccount> getUserAccountByUsername(String search) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, password, fName, lName, profileType, accountStatus FROM user_account INNER JOIN user_profile ON user_account.profileID = user_profile.profileID WHERE username LIKE ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                String profileType = resultSet.getString("profileType");
                boolean status = resultSet.getBoolean("accountStatus");
                UserProfile userProfile = new UserProfile(profileType);
                UserAccount userAccount = new UserAccount(username, password, fName, lName, userProfile, status);
                userAccounts.add(userAccount);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Filter User Accounts
    public ArrayList<UserAccount> selectByProfileType(String profileName) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, password, fName, lName, profileType, accountStatus FROM user_account INNER JOIN user_profile ON user_account.profileID = user_profile.profileID WHERE profileType = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String password =resultSet.getString("password");
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                String profileType = resultSet.getString("profileType");
                boolean accountStatus = resultSet.getBoolean("accountStatus");
                UserProfile userProfile = new UserProfile(profileType);
                UserAccount userAccount = new UserAccount(username, password, fName, lName, userProfile, accountStatus);
                userAccounts.add(userAccount);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    public UserAccount getSelectedAccount(String username) {
        String query = "SELECT * FROM user_account WHERE username = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserAccount userAccount = null;
            while(resultSet.next()) {
                String password = resultSet.getString("password");
                String fName = resultSet.getString("fName");
                String lName = resultSet.getString("lName");
                int profileID = resultSet.getInt("profileID");
                userAccount = new UserAccount(username, password, fName, lName, new UserProfile(profileID));
            }
            return userAccount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Profile dropdown
    public ArrayList<String> getProfileByType() {
        String query = "SELECT profileType FROM user_profile";
        ArrayList<String> profileTypeList = new ArrayList<>();

        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String profileType = resultSet.getString("profileType");
                profileTypeList.add(profileType);
            }
            return profileTypeList;
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