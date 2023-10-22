package Entity;

import Config.DBConfig;
import com.mysql.cj.protocol.Resultset;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
