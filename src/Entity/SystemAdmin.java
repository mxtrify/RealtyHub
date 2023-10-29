package Entity;

import Config.DBConfig;
import com.mysql.cj.protocol.Resultset;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemAdmin extends UserAccount {
    public SystemAdmin() {
        super();
    }

    public SystemAdmin(String username, String password) {
        super(username, password);
    }

    // Get list of all user account
    public ArrayList<UserAccount> selectAll() {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, f_name, l_name, profile_name FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profileName = resultSet.getString("profile_name");
                UserAccount userAccount = new UserAccount(username, fName, lName, profileName);
                userAccounts.add(userAccount);
            }
            return userAccounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Get profile name
    public List<String> getProfileByName() {
        String query = "SELECT profile_name FROM profile";
        List<String> profileNameList = new ArrayList<>();

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

    // Get role name
    public List<String> getRoleByName() {
        String query = "SELECT role_name FROM role";
        List<String> roleNameList = new ArrayList<>();

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

    public void insertAccount(UserAccount newUser) {
        String query = "INSERT INTO user_account VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getPassword());
            preparedStatement.setString(3, newUser.getFirstName());
            preparedStatement.setString(4, newUser.getLastName());
            preparedStatement.setString(5, newUser.getEmail());
            preparedStatement.setInt(7, newUser.getProfile());
            if(newUser.getProfile() == 4) {
                preparedStatement.setNull(6, Types.INTEGER);
                preparedStatement.setInt(8, newUser.getRole());
            } else {
                preparedStatement.setNull(6, Types.INTEGER);
                preparedStatement.setNull(8, Types.INTEGER);
            }
            preparedStatement.setBoolean(9, true);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void selectAllProfile(DefaultTableModel model) {
        String query = "SELECT profile_name, profile_desc FROM profile";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
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

    public void selectByProfileName(String profileName, DefaultTableModel model) {
        String query = "SELECT username, f_name, l_name, profile_name FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id WHERE profile_name = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            model.setRowCount(0);
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profile = resultSet.getString("profile_name");
                model.addRow(new Object[]{username, fName, lName, profile});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insertProfile(String profileName, String profileDesc) {
        String query = "INSERT INTO profile (profile_name, profile_desc) VALUES (?, ?)";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, profileName);
            preparedStatement.setString(2, profileDesc);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteProfile(String profileName) {
        String query = "DELETE FROM profile WHERE profile_name = ?";
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
            UserAccount userAccount = new UserAccount();
            while(resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("f_name");
                String lastName = resultSet.getString("l_name");
                String email = resultSet.getString("email");
                int profileID = resultSet.getInt("profile_id");
                int roleID = resultSet.getInt("role_id");
                userAccount = new UserAccount(userName, password, firstName, lastName, email, profileID, roleID);
            }
            return userAccount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
}
