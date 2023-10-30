package Entity;

import Config.DBConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CafeManager extends UserAccount {
    public CafeManager() {
        super();
    }

    public CafeManager(String username, String password) {
        super(username, password);
    }

    public ArrayList<String> getRoles(){
        ArrayList<String> roles = new ArrayList<>();
        roles.add("-- Select --");

        try{
            conn = new DBConfig().getConnection();
            String query = "SELECT `role_name` from `role`";

            // Execute query
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String roleName = resultSet.getString("role_name");
                roles.add(roleName);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return roles;
    }

}
