package Entity;

import Config.DBConfig;
import com.mysql.cj.protocol.Resultset;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void selectAll(DefaultTableModel model) {
        String query = "SELECT username, f_name, l_name, profile_name FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
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
            preparedStatement.setInt(6, newUser.getProfile());
            preparedStatement.setInt(7, newUser.getRole());
            preparedStatement.setInt(8, newUser.getMaxSlot());
            preparedStatement.setBoolean(9, true);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUserAccountByUsername(String search, DefaultTableModel model) {
        String query = "SELECT username, f_name, l_name, profile_name FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search);
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
        String query = "SELECT profile_name, profile_desc FROM profile WHERE profile_name = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search);
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
}
