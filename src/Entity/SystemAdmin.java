package Entity;

import Config.DBConfig;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class SystemAdmin extends UserAccount {
    public SystemAdmin() {
        super();
    }

    public SystemAdmin(String username, String password) {
        super(username, password);
    }
    public SystemAdmin(String username, String password, String firstName, String lastName, String email, UserProfile userProfile) {
        super(username, password, firstName, lastName, email, userProfile);
    }
    public SystemAdmin(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status) {
        super(username, password, firstName, lastName, email, userProfile, status);
    }

    // View
    public ArrayList<UserAccount> selectAll() {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, f_name, l_name, profile_name, status FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profileName = resultSet.getString("profile_name");
                boolean status = resultSet.getBoolean("status");
                UserProfile userProfile = new UserProfile(profileName);
                UserAccount userAccount = new UserAccount(username, fName, lName, userProfile, status);
                userAccounts.add(userAccount);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Profile dropdown
    public ArrayList<String> getProfileByName() {
        String query = "SELECT profile_name FROM profile";
        ArrayList<String> profileNameList = new ArrayList<>();

        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String profileName = resultSet.getString("profile_name");
                profileNameList.add(profileName);
            }
            return profileNameList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Role dropdown
    public ArrayList<String> getRoleByName() {
        String query = "SELECT role_name FROM role";
        ArrayList<String> roleNameList = new ArrayList<>();

        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                String roleName = resultSet.getString("role_name");
                roleNameList.add(roleName);
            }
            return roleNameList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Create
    public boolean insertAccount(UserAccount newUser) {
        String query = "INSERT INTO user_account (username, password, f_name, l_name, email, profile_id, role_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getFirstName());
            preparedStatement.setString(4, newUser.getLastName());
            preparedStatement.setString(5, newUser.getEmail());
            preparedStatement.setInt(6, newUser.getUserProfile().getProfileID());
            if(newUser.getUserProfile().getProfileID() == 4) {
                CafeStaff cafeStaff = (CafeStaff) newUser;
                preparedStatement.setInt(7, cafeStaff.getRole_id());
            } else {
                preparedStatement.setNull(7, Types.INTEGER);
            }
            preparedStatement.setBoolean(8, true);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Search
    public void getUserAccountByUsername(String search, DefaultTableModel model) {
        String query = "SELECT username, f_name, l_name, profile_name FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id WHERE username LIKE ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            model.setRowCount(0);
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profileName = resultSet.getString("profile_name");
                model.addRow(new Object[]{username, fName, lName, profileName});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Filter
    public ArrayList<UserAccount> selectByProfileName(String profileName) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, f_name, l_name, profile_name, status FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id WHERE profile_name = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profile = resultSet.getString("profile_name");
                boolean status = resultSet.getBoolean("status");
                UserProfile userProfile = new UserProfile(profileName);
                UserAccount userAccount = new UserAccount(username, fName, lName, userProfile, status);
                userAccounts.add(userAccount);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Suspend
    public boolean suspendUserAccount(String username) {
        String query = "UPDATE user_account SET status = 0 WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserAccount getSelectedAccount(String username) {
        String query = "SELECT * FROM user_account WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserAccount userAccount = null;
            while(resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("f_name");
                String lastName = resultSet.getString("l_name");
                String email = resultSet.getString("email");
                int profileID = resultSet.getInt("profile_id");
                int roleID = resultSet.getInt("role_id");
                if(profileID == 1) {
                    userAccount = new SystemAdmin(username, password, firstName, lastName, email, new UserProfile(profileID));
                } else if(profileID == 2) {
                    userAccount = new CafeOwner(username, password, firstName, lastName, email, new UserProfile(profileID));
                } else if(profileID == 3) {
                    userAccount = new CafeManager(username, password, firstName, lastName, email, new UserProfile(profileID));
                } else if(profileID == 4) {
                    userAccount = new CafeStaff(username, password, firstName, lastName, email, new UserProfile(profileID), roleID, 0);
                } else {
                    userAccount = new UserAccount();
                }
            }
            return userAccount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update
    public boolean updateUserAccount(UserAccount updatedUser) {
        String query = "UPDATE user_account SET password = ?, f_name = ?, l_name = ?, email = ?, max_slot = ?, profile_id = ?, role_id = ? WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, updatedUser.getPassword());
            preparedStatement.setString(2, updatedUser.getFirstName());
            preparedStatement.setString(3, updatedUser.getLastName());
            preparedStatement.setString(4, updatedUser.getEmail());
            preparedStatement.setNull(5, Types.INTEGER); // Assuming getMaxSlot() returns the max_slot value
            preparedStatement.setInt(6, updatedUser.getUserProfile().getProfileID()); // Set profile_id
            if(updatedUser.getUserProfile().getProfileID() == 4) {
                CafeStaff cafeStaff = (CafeStaff) updatedUser;
                preparedStatement.setInt(7, cafeStaff.getRole_id());
            }
            if (updatedUser.getUserProfile().getProfileID() != 4) {
                preparedStatement.setNull(5, Types.INTEGER); // If profile_id is not 4, set max_slot to NULL
                preparedStatement.setNull(7, Types.INTEGER); // Also set role_id to NULL
            }
            preparedStatement.setString(8, updatedUser.getUsername());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean unsuspendUserAccount(String username) {
        String query = "UPDATE user_account SET status = 1 WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
