package Entity;

import Config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    protected Connection conn;

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected UserProfile userProfile;
    protected boolean status;

    public UserAccount() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.userProfile = new UserProfile();
        this.status = false;

        try{
            this.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserAccount(String username, String password, String firstName, String lastName, String email, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userProfile = userProfile;
    }

    public UserAccount(String username, String firstName, String lastName, UserProfile userProfile, boolean status) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userProfile = userProfile;
        this.status = status;
    }

    public UserAccount(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userProfile = userProfile;
        this.status = status;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
                    return new CafeStaff(username, password);
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