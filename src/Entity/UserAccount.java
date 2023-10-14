package Entity;

import Config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    private String username;
    private String password;
    private String profile;

    public UserAccount() {

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

    public String getProfile() {
        return profile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    // Function for validating login
    public boolean validateLogin(UserAccount userAccount) {
        String query = "SELECT * FROM user_account WHERE username = ? AND password = ?";
        try{
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, userAccount.getUsername());
            preparedStatement.setString(2, userAccount.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if there's a matching record
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function for validating profile
    public String validateProfile(String username) {
        String query = "SELECT profile FROM user_account WHERE username = ?";
        try{
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) { // Return true if there's a record
                return resultSet.getString("profile"); // Get column "profile" value
            } else {
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}