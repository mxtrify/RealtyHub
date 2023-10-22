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

    public DefaultTableModel selectAll(DefaultTableModel model) {
        String query = "SELECT username, f_name, l_name, profile_name FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            model.addColumn("Username");
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Profile");
            while(resultSet.next()) {
                String username = resultSet.getString("username");
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profileName = resultSet.getString("profile_name");
                model.addRow(new Object[]{username, fName, lName, profileName});
            }

            return model;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
}
