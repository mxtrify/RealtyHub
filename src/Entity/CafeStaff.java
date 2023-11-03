package Entity;

import Config.DBConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CafeStaff extends UserAccount {
    private int role_id;
    private String role_name;
    private int max_slot;

    public CafeStaff() {
        super();
        this.role_id = 0;
        this.role_name = "";
        this.max_slot = 0;
        try{
            super.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public CafeStaff(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, int role_id, int max_slot) {
        super(username, password, firstName, lastName, email, userProfile);
        this.role_id = role_id;
        this.max_slot = max_slot;
    }
    public CafeStaff(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status, int role_id, int max_slot) {
        super(username, password, firstName, lastName, email, userProfile, status);
        this.role_id = role_id;
        this.max_slot = max_slot;
    }

    public CafeStaff(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status, int role_id, String role_name, int max_slot) {
        super(username, password, firstName, lastName, email, userProfile, status);
        this.role_id = role_id;
        this.role_name = role_name;
        this.max_slot = max_slot;
    }

    public CafeStaff(String username, String password) {
        super(username, password);
        try{
            // Get Staff details (Role name and max slot)
            super.conn = new DBConfig().getConnection();
            String query = "SELECT `user_account`.`max_slot` AS `Max`, `role`.`role_name` AS `RName` FROM `user_account` " +
                    "JOIN `role` ON `user_account`.`role_id` = `role`.`role_id` " +
                    "JOIN `profile` ON `user_account`.`profile_id` = `profile`.`profile_id` " +
                    "WHERE `user_account`.`username` = ? AND `profile`.`profile_name` = \"Cafe Staff\" ";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, super.username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                this.role_name = resultSet.getString("RName");
                this.max_slot = resultSet.getInt("Max");
            }else{
                this.role_name = "";
                this.max_slot = 0;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getRole_id() {
        return role_id;
    }
    public String getRoleName(){return this.role_name;}
    public int getMaxSlot(){return this.max_slot;}

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setRoleName(String role_name) {this.role_name = role_name;}
    public void setMaxSlot(int max_slot) {
        this.max_slot = max_slot;
    }


}
