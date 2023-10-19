package Entity;

import Config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    private String username;
    private String password;

    public UserAccount() {
        this.username = "";
        this.password = "";
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Function for validating login
    public UserAccount validateLogin(String username, String password) {
        String query = "SELECT * FROM user_account WHERE username = ? AND password = ?";
        try{
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                if(resultSet.getInt("profile_id") == 1) {
                    return new SystemAdmin(username, password);
                } else if (resultSet.getInt("profile_id") == 2) {
                    return new CafeOwner(username, password);
                } else if (resultSet.getInt("profile_id") == 3) {
                    return new CafeManager(username, password);
                } else if (resultSet.getInt("profile_id") == 4) {
                    return new CafeStaff();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}