package Entity;

import Config.DBConfig;

import java.sql.*;
import java.util.Vector;

public class Bid {
    private Connection conn;
    private int bid_id;
    private String username;
    private int work_slot_id;
    private String bid_status;

    public Bid(){
        this.bid_id = 0;
        this.username = "";
        this.work_slot_id = 0;
        this.bid_status = "";
    }

    public Bid(int bid_id, String username, int work_slot_id, String bid_status){
        this.bid_id = bid_id;
        this.username = username;
        this.work_slot_id = work_slot_id;
        this.bid_status = bid_status;
    }

    public Object[][] getBids(String value){
        Object[][] data = null;
        try {
            // Connect db
            this.conn = new DBConfig().getConnection();

            String query = "SELECT `bid`.`bid_id` AS `ID`,"+
                    "CONCAT(`user_account`.`f_name`, ' ' ,`user_account`.`l_name`) AS `Name`,"+
                    "`role`.`role_name` as `Role`,"+
                    "`work_slot`.`date` as `Date`"+
                    "FROM `user_account`"+
                    "JOIN `role` ON `user_account`.`role_id` = `role`.`role_id`"+
                    "JOIN `bid` ON `user_account`.`username` = `bid`.`username`"+
                    "JOIN `work_slot` ON `bid`.`work_slot_id` = `work_slot`.`work_slot_id`";


            PreparedStatement preparedStatement;
            if (!value.equals("-- Select --")){
                // Show specific row bids;
                query += "WHERE `role`.`role_name` = ?";
                query += "ORDER BY `bid`.`bid_id`";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, value);
            }else {
                // Show all role bids
                query += "ORDER BY `bid`.`bid_id`";
                preparedStatement = conn.prepareStatement(query);
            }



            // Execute statement
            ResultSet result = preparedStatement.executeQuery();

            // Get the ResultSet metadata to determine the number of columns
            ResultSetMetaData metaData = result.getMetaData();
            int col = metaData.getColumnCount();

            // To hold data
            Vector<Vector<Object>> dataVector = new Vector<>();

            // Collect data
            while (result.next()){
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= col; i++){
                    row.add(result.getObject(i));
                }
                row.add("");
                dataVector.add(row);
            }

            // Convert into 2D Object array
            data = new Object[dataVector.size()][col];
            for (int i = 0; i < dataVector.size(); i++){
                data[i] = dataVector.get(i).toArray();
            }

            // close resources
            result.close();
            preparedStatement.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }
}
