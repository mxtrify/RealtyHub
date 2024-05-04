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

    public UserAccount() {
        this.username = "";
        this.password = "";
        this.fName = "";
        this.lName = "";
        this.userProfile = new UserProfile();
        this.status = false;
    }

    public UserAccount(String username, String password, String fName, String lName, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.userProfile = userProfile;
    }

    public UserAccount(String username, String password, String fName, String lName, UserProfile userProfile, boolean status) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.userProfile = userProfile;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
    public String getFullName() {return String.format("%s %s", getfName(), getlName());}

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // Login Validation
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

    // Create User Account
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

    // View User Account
    public ArrayList<UserAccount> selectAll() {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, password, fName, lName, profileType, accountStatus FROM user_account INNER JOIN user_profile ON user_account.profileID = profile.profileID";
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

    // Update User Account
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
            // Ensure the connection is closed even if an exception occurs
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

    // Suspend User Account
    public boolean suspendUserAccount(String username) {
        String query = "UPDATE user_account SET status = 0 WHERE username = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
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
    public boolean activateUserAccount(String username) {
        String query = "UPDATE user_account SET status = 1 WHERE username = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
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

    // Search User Account
    public ArrayList<UserAccount> getUserAccountByUsername(String search) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, password, f_name, l_name, profileType, accountStatus FROM user_account INNER JOIN user_profile ON user_account.profileID = profile.profileID WHERE username LIKE ?";
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
                boolean status = resultSet.getBoolean("status");
                UserProfile userProfile = new UserProfile(profileType);
                UserAccount userAccount = new UserAccount(username, password, fName, lName, userProfile, status);
                userAccounts.add(userAccount);
            }
            return userAccounts;
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

    // Filter User Accounts
    public ArrayList<UserAccount> selectByProfileName(String profileName) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, password, f_name, l_name, profileType, accountStatus FROM user_account INNER JOIN user_profile ON user_account.profileID = profile.profileID WHERE profile_name = ?";
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

    // Profile dropdown
    public ArrayList<String> getProfileByName() {
        String query = "SELECT profileType FROM profile";
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