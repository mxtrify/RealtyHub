package Entity;

import Config.DBConfig;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class Bid {
    private Connection conn;
    private int bid_id;
    private String name;
    private String role;
    private Date date;

    public Bid(){
        this.bid_id = 0;
        this.name = "";
        this.role = "";
        this.date = null;

        try{
            this.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public Bid(Date date){
        this.bid_id = 0;
        this.name = "";
        this.role = "";
        this.date = date;

        try{
            this.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Bid(int bid_id, String name, String role, Date date){
        this.bid_id = bid_id;
        this.name = name;
        this.role = role;
        this.date = date;

        try{
            this.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public int getBidId(){return bid_id;}
    public String getName(){return name;}
    public String getRole(){return role;}
    public Date getDate(){return date;}

    public void setBid_id(int bid_id){this.bid_id = bid_id;}
    public void setName(String name){this.name = name;}
    public void setRole(String role){this.role = role;}
    public void setDate(Date date){this.date = date;}

    public String dateToString(){
        // Convert date object into string display "01 Oct, 2023"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");

        return dateFormat.format(getDate());
    }

    public Object[][] getBids(){
        Object[][] data = null;
        try {
            // Connect db
            this.conn = new DBConfig().getConnection();

            String query = "SELECT `bid`.`bid_id` AS `ID`,"+
                    "CONCAT(`user_account`.`f_name`, ' ' ,`user_account`.`l_name`) AS `Name`,"+
                    "`role`.`role_name` as `Role`"+
                    "FROM `user_account`"+
                    "JOIN `role` ON `user_account`.`role_id` = `role`.`role_id`"+
                    "JOIN `bid` ON `user_account`.`username` = `bid`.`username`"+
                    "JOIN `work_slot` ON `bid`.`date` = `work_slot`.`date` "+
                    "WHERE `work_slot`.`date` = ? AND `bid`.`bid_status` = 'Pending' " +
                    "ORDER BY `bid`.`bid_id`, `role`.`role_name`";


            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, getDate());



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



    public boolean approveBid(){
        try{
            // Change bid status to 'Rejected'
            String query = "UPDATE `bid` SET `bid_status` = ? WHERE `bid_id` = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setInt(2, getBidId());
            preparedStatement.execute();

            // close resources
            preparedStatement.close();
            conn.close();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectBid(){
        try{
            // Change bid status to 'Rejected'
            String query = "UPDATE `bid` SET `bid_status` = ? WHERE `bid_id` = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Rejected");
            preparedStatement.setInt(2, getBidId());
            preparedStatement.execute();

            // close resources
            preparedStatement.close();
            conn.close();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean assignAvailableStaff(String username){
        try{

            // Check if he has submitted a bid
            String query = "SELECT * FROM `bid` WHERE `username` = ? AND `date` = ? AND (`bid_status` = 'Pending' OR `bid_status` = 'Rejected')";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Bid bid = new Bid();
                bid.setBid_id(resultSet.getInt("bid_id"));

                return bid.approveRejectBid("Approved");
            }else{
                // Assign staff
                query = "INSERT INTO `bid`(`username`, `date`, `bid_status`) " +
                        "VALUES (?, ?, ?)";
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setDate(2, getDate());
                preparedStatement.setString(3, "Assigned");
                preparedStatement.execute();

                preparedStatement.close();

                return true;


            }








        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean approveRejectBid(String action){
        try{
            // Change bid status to 'Rejected'
            String query = "UPDATE `bid` SET `bid_status` = ? WHERE `bid_id` = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, action);
            preparedStatement.setInt(2, getBidId());
            preparedStatement.execute();

            // close resources
            preparedStatement.close();
            conn.close();

            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
