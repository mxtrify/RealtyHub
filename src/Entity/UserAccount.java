package Entity;

import Config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    protected String name;
    protected Connection conn;
    protected String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int profile;
    private String profileName;
    private int role;
    private int maxSlot;
    private boolean status;

    public UserAccount() {
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.profile = 0;
        this.role = 0;
        this.maxSlot = 0;
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


        try{
            this.conn = new DBConfig().getConnection();

            // Get user account name from database
            String query = "SELECT CONCAT(`f_name`, ' ', `l_name`) AS `Name`" +
                    "FROM `user_account` WHERE `username` = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, this.username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                this.name = resultSet.getString("Name");
            }else{
                this.name = "";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public UserAccount(String username, String firstName, String lastName, String profileName, boolean status) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileName = profileName;
        this.status = status;
    }

    public UserAccount(String username, String password, String firstName, String lastName, String email, int profile, int role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profile = profile;
        this.role = role;
    }
    public UserAccount(String username, String password, String firstName, String lastName, String email, int profile, int role, int maxSlot, boolean status) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profile = profile;
        this.role = role;
        this.maxSlot = maxSlot;
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

    public int getProfile() {
        return profile;
    }

    public int getRole() {
        return role;
    }

    public int getMaxSlot() {
        return maxSlot;
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

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setMaxSlot(int maxSlot) {
        this.maxSlot = maxSlot;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public boolean isStatus() {
        return status;
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