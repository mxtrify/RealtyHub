package Entity;

import Config.DBConfig;

import java.sql.*;
import java.util.ArrayList;

public class UserAccount {
    protected Connection conn;

    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected UserProfile userProfile;
    protected boolean status;
    private int role_id;
    private String role_name;
    private int max_slot;


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

    public UserAccount(String username, String password, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
    }

    public UserAccount(String username, String password, UserProfile userProfile, boolean status) {
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
        this.status = status;
    }

    public UserAccount(String username, String password, String firstName, String lastName, UserProfile userProfile, boolean status) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userProfile = userProfile;
        this.status = status;
    }


    public UserAccount(String username, String password, UserProfile userProfile, boolean status, int max_slot) {
        this.username = username;
        this.password = password;
        this.userProfile = userProfile;
        this.status = status;
        this.max_slot = max_slot;
    }

    public UserAccount(String username, String password, String firstName, String lastName, UserProfile userProfile, boolean status, int max_slot) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userProfile = userProfile;
        this.status = status;
        this.max_slot = max_slot;
    }

    public UserAccount(String username, String password, String firstName, String lastName, UserProfile userProfile, boolean status, int max_slot, int role_id) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userProfile = userProfile;
        this.status = status;
        this.max_slot = max_slot;
        this.role_id = role_id;
    }


    public UserAccount(String username, String password, String firstName, String lastName, String email, UserProfile userProfile) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userProfile = userProfile;
    }

    public UserAccount(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, int role_id, int max_slot) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userProfile = userProfile;
        this.role_id = role_id;
        this.max_slot = max_slot;
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

    public UserAccount(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, int role_id) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userProfile = userProfile;
        this.status = status;
        this.role_id = role_id;
    }

    public UserAccount(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status, int role_id, int max_slot) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userProfile = userProfile;
        this.status = status;
        this.role_id = role_id;
        this.max_slot = max_slot;
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
    public String getFullName() {return String.format("%s %s", getFirstName(), getLastName());}

    public String getEmail() {
        return email;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public boolean isStatus() {
        return status;
    }

    public int getRole_id() {
        return role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public int getMax_slot() {
        return max_slot;
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

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public void setMax_slot(int max_slot) {
        this.max_slot = max_slot;
    }

    // Login Validation
    public UserAccount validateLogin(String username, String password) {
        String query = "SELECT username, password, f_name, l_name, profile_name, max_slot, status, profile_status, role_id FROM user_account INNER JOIN profile ON  user_account.profile_id = profile.profile_id WHERE username = ? AND password = ?";
        try{
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String fName = resultSet.getString("f_name");
                String lName = resultSet.getString("l_name");
                String profileName = resultSet.getString("profile_name");
                boolean status = resultSet.getBoolean("status");
                boolean profileStatus = resultSet.getBoolean("profile_status");
                int roleId = resultSet.getInt("role_id");
                int maxSlot;
                if (resultSet.getObject("max_slot") == null) {
                    maxSlot = -1; // Assign some default value if max_slot is NULL
                } else {
                    maxSlot = resultSet.getInt("max_slot");
                }
                UserProfile userProfile = new UserProfile(profileName, profileStatus);
                if(resultSet.getString("profile_name").equals("System Admin")) {
                    return new UserAccount(username, password, fName, lName, userProfile, status);
                } else if (resultSet.getString("profile_name").equals("Cafe Owner")) {
                    return new UserAccount(username, password, fName, lName, userProfile, status);
                } else if (resultSet.getString("profile_name").equals("Cafe Manager")) {
                    return new UserAccount(username, password, fName, lName, userProfile, status);
                } else if (resultSet.getString("profile_name").equals("Cafe Staff")) {
                    return new UserAccount(username, password, fName, lName, userProfile, status, maxSlot, roleId);
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
                preparedStatement.setInt(7, newUser.getRole_id());
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

    // Read (View)
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

    // Update
    public boolean updateUserAccount(UserAccount updatedUser) {
        String query = "UPDATE user_account SET password = ?, f_name = ?, l_name = ?, email = ?, profile_id = ?, role_id = ? WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, updatedUser.getPassword());
            preparedStatement.setString(2, updatedUser.getFirstName());
            preparedStatement.setString(3, updatedUser.getLastName());
            preparedStatement.setString(4, updatedUser.getEmail());
            preparedStatement.setInt(5, updatedUser.getUserProfile().getProfileID()); // Set profile_id
            if(updatedUser.getUserProfile().getProfileID() == 4) {
                preparedStatement.setInt(6, updatedUser.getRole_id());
            }
            if (updatedUser.getUserProfile().getProfileID() != 4) {
                preparedStatement.setNull(6, Types.INTEGER); // Also set role_id to NULL
            }
            preparedStatement.setString(7, updatedUser.getUsername());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
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

    // Unsuspend
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

    // Search
    public ArrayList<UserAccount> getUserAccountByUsername(String search) {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        String query = "SELECT username, f_name, l_name, profile_name, status FROM user_account INNER JOIN profile ON user_account.profile_id = profile.profile_id WHERE username LIKE ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, search + "%");
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
                UserProfile userProfile = new UserProfile(profile);
                UserAccount userAccount = new UserAccount(username, fName, lName, userProfile, status);
                userAccounts.add(userAccount);
            }
            return userAccounts;
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
                    userAccount = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profileID));
                } else if(profileID == 2) {
                    userAccount = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profileID));
                } else if(profileID == 3) {
                    userAccount = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profileID));
                } else if(profileID == 4) {
                    userAccount = new UserAccount(username, password, firstName, lastName, email, new UserProfile(profileID), roleID, 0);
                } else {
                    userAccount = new UserAccount();
                }
            }
            return userAccount;
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

    public void setMaxSlot(UserAccount u) {
        String query = "UPDATE user_account SET max_slot = ? WHERE username = ?";
        try {
            Connection conn = new DBConfig().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, u.getMax_slot());
            preparedStatement.setString(2, u.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Staff Object
//    public CafeStaff(String username, String password) {
//        super(username, password);
//        try{
//            // Get Staff details (Role name and max slot)
//            super.conn = new DBConfig().getConnection();
//            String query = "SELECT `user_account`.`max_slot` AS `Max`, `role`.`role_name` AS `RName` FROM `user_account` " +
//                    "JOIN `role` ON `user_account`.`role_id` = `role`.`role_id` " +
//                    "JOIN `profile` ON `user_account`.`profile_id` = `profile`.`profile_id` " +
//                    "WHERE `user_account`.`username` = ? AND `profile`.`profile_name` = \"Cafe Staff\" ";
//
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//            preparedStatement.setString(1, super.username);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if(resultSet.next()){
//                this.role_name = resultSet.getString("RName");
//                this.max_slot = resultSet.getInt("Max");
//            }else{
//                this.role_name = "";
//                this.max_slot = 0;
//            }
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
}