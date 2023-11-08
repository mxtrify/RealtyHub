package Entity;

import Config.DBConfig;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

public class Bid {
    private Connection conn;
    private int bid_id;
    private String name;
    private String role;
    private Date date;
    private String bidStatus;

    public Bid(){
        this.bid_id = 0;
        this.name = "";
        this.role = "";
        this.date = null;
        this.bidStatus = "";

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

    public Bid(String name, Date date){
        this.bid_id = 0;
        this.name = name;
        this.role = "";
        this.date = date;

        try{
            this.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Bid(int bid_id, Date date){
        this.bid_id = bid_id;
        this.name = "";
        this.role = "";
        this.date = date;
        this.bidStatus = "";

        try{
            this.conn = new DBConfig().getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public Bid(int bid_id, Date date, String bidStatus){
        this.bid_id = bid_id;
        this.name = "";
        this.role = "";
        this.date = date;
        this.bidStatus = bidStatus;

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

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBid_id(int bid_id){this.bid_id = bid_id;}
    public void setName(String name){this.name = name;}
    public void setRole(String role){this.role = role;}
    public void setDate(Date date){this.date = date;}

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

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

        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public boolean makeBid(String username, Date date) {
        try {
            // Check if Staff has already made a bid
            String checkQuery = "SELECT * FROM bid WHERE username = ? AND date = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            checkStatement.setDate(2, date);

            ResultSet checkResult = checkStatement.executeQuery();
            if (checkResult.next()) {
                JOptionPane.showMessageDialog(null, "You have already submitted a bid for this date.");
                return false;
            }

            // If staff haven't made a bid, make new bid
            String insertQuery = "INSERT INTO bid (username, date, bid_status) VALUES (?, ?, 'Pending')";
            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setString(1, username);
            insertStatement.setDate(2, date);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Bid submitted successfully!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to submit bid. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean approveBid(){
        try{
            // Change bid status to 'Rejected'
            String query = "UPDATE `bid` SET `bid_status` = ? WHERE `bid_id` = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Approved");
            preparedStatement.setInt(2, getBidId());
            preparedStatement.execute();


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


            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Object[][] getUpcomingWork(){
        Object[][] data = null;
        try {
            String query = "SELECT `bid_id`, `date`, `bid_status` FROM `bid` " +
                    "WHERE `username` = ? AND (`bid_status` = \"Approved\" OR `bid_status` = \"Assigned\") AND `date` >= CURRENT_DATE "+
                    "AND `date` = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, getName());
            preparedStatement.setDate(2, getDate());

            ResultSet result = preparedStatement.executeQuery();



            if (result.next()){
                data = new Object[1][3];
                data[0][0] = result.getInt(1);
                data[0][1] = dateToString();
                data[0][2] = result.getString(3);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    public Object[][] getAllUpcomingWork(){
        Object[][] data = null;
        try {
            String query = "SELECT `bid_id`, `date`, `bid_status` FROM `bid` " +
                    "WHERE `username` = ? AND (`bid_status` = \"Approved\" OR `bid_status` = \"Assigned\") AND `date` >= CURRENT_DATE "+
                    "ORDER BY `date`";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, getName());

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

                    if (i == 2){
                        setDate(result.getDate(i));
                        row.add(dateToString());
                    }else{
                        row.add(result.getObject(i));
                    }

                }
                dataVector.add(row);
            }

            // Convert into 2D Object array
            data = new Object[dataVector.size()][col];
            for (int i = 0; i < dataVector.size(); i++){
                data[i] = dataVector.get(i).toArray();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;
    }


    public Object[][] getPastWork(){
        Object[][] data = null;
        try {
            String query = "SELECT `bid_id`, `date`, `bid_status` FROM `bid` " +
                    "WHERE `username` = ? AND (`bid_status` = \"Approved\" OR `bid_status` = \"Assigned\") AND `date` < CURRENT_DATE "+
                    "AND `date` = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, getName());
            preparedStatement.setDate(2, getDate());

            ResultSet result = preparedStatement.executeQuery();



            if (result.next()){
                data = new Object[1][3];
                data[0][0] = result.getInt(1);
                data[0][1] = dateToString();
                data[0][2] = String.format("%s / %s", result.getString(3), "Done");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    public Object[][] getAllPastWork(){
        Object[][] data = null;
        try {
            String query = "SELECT `bid_id`, `date`, `bid_status` FROM `bid` " +
                    "WHERE `username` = ? AND (`bid_status` = \"Approved\" OR `bid_status` = \"Assigned\") AND `date` < CURRENT_DATE "+
                    "ORDER BY `date`";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, getName());

            ResultSet result = preparedStatement.executeQuery();

            // Get the ResultSet metadata to determine the number of columns
            ResultSetMetaData metaData = result.getMetaData();
            int col = metaData.getColumnCount();

            // To hold data
            Vector<Vector<Object>> dataVector = new Vector<>();

            // Collect data
            while (result.next()){
                Vector<Object> row = new Vector<>();

                row.add(result.getInt(1));
                setDate(result.getDate(2));
                row.add(dateToString());
                row.add(String.format("%s / %s", result.getString(3), "Done"));


                dataVector.add(row);
            }

            // Convert into 2D Object array
            data = new Object[dataVector.size()][col];
            for (int i = 0; i < dataVector.size(); i++){
                data[i] = dataVector.get(i).toArray();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;
    }

    public ArrayList<Bid> getAllBidStatus(String username) {
        ArrayList<Bid> bids = new ArrayList<>();
        String query = "SELECT bid_id, date, bid_status FROM bid WHERE username = ? ORDER BY CASE bid_status " +
                "WHEN 'Pending' THEN 1 WHEN 'Approved' THEN 2 ELSE 3 END";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int bidId = resultSet.getInt("bid_id");
                Date date = resultSet.getDate("date");
                String bidStatus = resultSet.getString("bid_status");
                Bid bid = new Bid(bidId, date, bidStatus);
                bids.add(bid);
            }
            return bids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cancelBid(Date date) {
        String query = "DELETE FROM bid WHERE date = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, date);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMonthlySlotLeft(){
        try {
            String query = "SELECT `max_slot` FROM `user_account` WHERE `username` = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            int maxSlot = -1;
            if (resultSet.next()){
                maxSlot = resultSet.getInt("max_slot");
            }

            System.out.println(maxSlot);

            query = "SELECT COUNT(*) as `total` FROM `bid` " +
                    "WHERE `username` = ? and MONTH(`date`) = ? AND YEAR(`date`) = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, getName());
            preparedStatement.setInt(2, getDate().getMonth()+1);
            preparedStatement.setInt(3, getDate().getYear()+1900);
            resultSet = preparedStatement.executeQuery();

            int currentTotal = -1;
            if (resultSet.next()){
                currentTotal = resultSet.getInt("total");
            }
            System.out.println(currentTotal);

            return maxSlot-currentTotal;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Bid> getBidByStatus(String username, String selectedBidStatus) {
        ArrayList<Bid> bids = new ArrayList<>();
        String query = "SELECT bid_id, date, bid_status FROM bid WHERE username = ? AND bid_status = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, selectedBidStatus);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int bidId = resultSet.getInt("bid_id");
                Date date = resultSet.getDate("date");
                String bidStatus = resultSet.getString("bid_status");
                Bid bid = new Bid(bidId, date, bidStatus);
                bids.add(bid);
            }
            return bids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Bid> getBidByDate(String username, Date selectedDate) {
        ArrayList<Bid> bids = new ArrayList<>();
        String query = "SELECT bid_id, date, bid_status FROM bid WHERE username = ? AND date = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setDate(2, selectedDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int bidId = resultSet.getInt("bid_id");
                Date date = resultSet.getDate("date");
                String bidStatus = resultSet.getString("bid_status");
                Bid bid = new Bid(bidId, date, bidStatus);
                bids.add(bid);
            }
            return bids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateBid(int bidId, String username, Date date) {
        String checkQuery = "SELECT * FROM bid WHERE username = ? AND date = ?";
        String insertQuery = "UPDATE bid SET date = ? WHERE bid_id = ?";
        try {
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            checkStatement.setDate(2, date);

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }

            PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
            insertStatement.setDate(1, date);
            insertStatement.setInt(2, bidId);

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
