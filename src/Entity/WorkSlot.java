package Entity;

import Config.DBConfig;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class WorkSlot {
    public static final int CHEF = 3;
    public static final int CASHIER = 2;
    public static final int WAITER = 1;
    private Connection conn;
    private Date date;
    private int chefAmount;
    private int cashierAmount;
    private int waiterAmount;

    public Date getDate() {
        return date;
    }

    public int getChefAmount() {
        return chefAmount;
    }

    public int getCashierAmount() {
        return cashierAmount;
    }

    public int getWaiterAmount() {
        return waiterAmount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setChefAmount(int chefAmount) {
        this.chefAmount = chefAmount;
    }

    public void setCashierAmount(int cashierAmount) {
        this.cashierAmount = cashierAmount;
    }

    public void setWaiterAmount(int waiterAmount) {
        this.waiterAmount = waiterAmount;
    }

    public String dateToString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(getDate());
    }

    public WorkSlot() {
        this.date = new Date(2000, 1, 1);
        this.chefAmount = 0;
        this.cashierAmount = 0;
        this.waiterAmount = 0;
        try {
            this.conn = new DBConfig().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WorkSlot(Date date) {
        this.date = date;
        try {
            this.conn = new DBConfig().getConnection();

            String query = "SELECT ra.amount " +
                    "FROM role_amount ra JOIN role r " +
                    "ON ra.role_id = r.role_id " +
                    "WHERE r.role_name = ? AND ra.date = ?";

            // Set Chef Amount
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Chef");
            preparedStatement.setDate(2, getDate());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                this.chefAmount = resultSet.getInt("amount");
            }
            else {
                this.chefAmount = 0;
            }

            // Set Cashier Amount
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Cashier");
            preparedStatement.setDate(2, getDate());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                this.cashierAmount = resultSet.getInt("amount");
            }
            else {
                this.cashierAmount = 0;
            }

            // Set Waiter Amount
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Waiter");
            preparedStatement.setDate(2, getDate());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                this.waiterAmount = resultSet.getInt("amount");
            }
            else {
                this.waiterAmount = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WorkSlot(Date date, int chefAmount, int cashierAmount, int waiterAmount) {
        this.date = date;
        this.chefAmount = chefAmount;
        this.cashierAmount = cashierAmount;
        this.waiterAmount = waiterAmount;
        try {
            this.conn = new DBConfig().getConnection();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getAllWS(){
        Object[][] data = null;
        Vector<Vector<Object>> allDataVector = new Vector<>();
        try{
            // Select all workslots
            String query = "SELECT `date` FROM `work_slot` WHERE `date` >= CURRENT_DATE ORDER BY `date`";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                // Get work slots data for each date
                WorkSlot ws_row = new WorkSlot(resultSet.getDate("date"));


                Vector<Object> row = new Vector<>();

                // Fetch data for each date
                for(int i = 0; i < 5; i ++){
                    // Temporary use "data" variable
                    data = ws_row.getWS();
                    row.add(data[0][i]);
                }

                // Store each workslot data to vector
                allDataVector.add(row);

            }

            // Convert Vector<Vector<Object>> to Object[][]
            data = new Object[allDataVector.size()][];
            for (int i = 0; i < allDataVector.size(); i++){
                data[i] = allDataVector.get(i).toArray();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;

    }

    public Object[][] getWS(){

        // Initial data
        Object[][] data = null;
        int waiterLeft = 0;
        int cashierLeft = 0;
        int chefLeft = 0;

        try{
            // Check for amount of staff left for each role
            // By calculating the total amount of staff for each role
            // subtract by the total approved staff on specific date for specific role
            // * Return null if no work slot made *
            String query = "SELECT COUNT(`bid`.`bid_id`) as `bids` FROM `bid` " +
                    "JOIN `user_account` ON `bid`.`username` = `user_account`.`username` " +
                    "JOIN `role` ON `user_account`.`role_id` = `role`.`role_id` " +
                    "WHERE `role`.`role_name` = ? AND `bid`.`date` = ? AND (`bid`.`bid_status` = \"Approved\" OR `bid`.`bid_status` = \"Assigned\")";

            // Get number of chef approved
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Chef");
            preparedStatement.setDate(2, getDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next() && getChefAmount() >= 1){
                // Get number of chef left
                chefLeft = getChefAmount() - resultSet.getInt("bids");
            }else{
                return null;
            }


            // Get number of cashier approved
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Cashier");
            preparedStatement.setDate(2, getDate());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && getCashierAmount() >= 1){
                // Get number of cashier left
                cashierLeft = getCashierAmount() - resultSet.getInt("bids");
            }else{
                return null;
            }


            // Get number of waiter approved
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Waiter");
            preparedStatement.setDate(2, getDate());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && getWaiterAmount() >= 1){
                // Get number of waiter left
                waiterLeft = getWaiterAmount() - resultSet.getInt("bids");
            }else {
                return null;
            }

            // Store in 2D array
            data = new Object[1][5];
            data[0][0] = dateToString();
            data[0][1] = chefLeft;
            data[0][2] = cashierLeft;
            data[0][3] = waiterLeft;

            // Determine slot availability
            if(chefLeft == 0 && cashierLeft == 0 && waiterLeft == 0){
                data[0][4] = "Not Available";
            }else{
                data[0][4] = "Available";
            }

            // Close resources
            preparedStatement.close();
            resultSet.close();


            // Return the data
            return data;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }



    public WorkSlot createWorkSlot(String dateString, int chefAmount, int cashierAmount, int waiterAmount) {
        String workSlotQuery = "INSERT INTO work_slot (date) VALUES (?)";
        try {
            Connection conn = new DBConfig().getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement(workSlotQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

                java.util.Date parsedDate = inputFormat.parse(dateString);
                java.sql.Date date = new java.sql.Date(parsedDate.getTime());

                preparedStatement.setDate(1, date);

                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    // Insert into role_amount table
                    insertRoleAmount(date, CHEF, chefAmount);
                    insertRoleAmount(date, CASHIER, cashierAmount);
                    insertRoleAmount(date, WAITER, waiterAmount);

                    return new WorkSlot(date, chefAmount, cashierAmount, waiterAmount);
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertRoleAmount(Date date, int roleId, int amount) {
        try {
            Connection conn = new DBConfig().getConnection();
            String roleAmountQuery = "INSERT INTO role_amount (date, role_id, amount) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(roleAmountQuery)) {
                preparedStatement.setDate(1, date);
                preparedStatement.setInt(2, roleId);
                preparedStatement.setInt(3, amount);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Role amount created successfully.");
                } else {
                    System.out.println("Failed to create role amount.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WorkSlot> getAllWorkSlots() {
        List<WorkSlot> workSlots = new ArrayList<>();

        try {
            Connection conn = new DBConfig().getConnection();
            String query = "SELECT * FROM work_slot";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    WorkSlot workSlot = new WorkSlot();

                    workSlot.setDate(resultSet.getDate("date"));
                    workSlot.setChefAmount(getRoleAmount(workSlot.getDate(), CHEF));
                    workSlot.setCashierAmount(getRoleAmount(workSlot.getDate(), CASHIER));
                    workSlot.setWaiterAmount(getRoleAmount(workSlot.getDate(), WAITER));
                    workSlots.add(workSlot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workSlots;
    }

    private int getRoleAmount(Date date, int roleId) {
        int amount = 0;

        try {
            Connection conn = new DBConfig().getConnection();
            String query = "SELECT amount FROM role_amount WHERE date = ? AND role_id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setDate(1, date);
                preparedStatement.setInt(2, roleId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        amount = resultSet.getInt("amount");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }


    public boolean updateRoleAmount(Date date, int roleID, int newAmount) {
        String updateRoleAmountQuery = "UPDATE role_amount SET amount = ? WHERE date = ? AND role_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateRoleAmountQuery)) {
            preparedStatement.setInt(1, newAmount);
            preparedStatement.setDate(2, date);
            preparedStatement.setInt(3, roleID);

            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println(newAmount);
            System.out.println(date);
            System.out.println(roleID);

            System.out.println("Rows updated: " + rowsUpdated);
            System.out.println("SQL Query: " + updateRoleAmountQuery);

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean deleteWorkSlot(Date date) {
        deleteRoleAmountTableEntry(date);
        deleteWorkSlotEntry(date);
        return true;
    }

    private void deleteRoleAmountTableEntry(Date date) {
        String delRoleAmountQuery = "DELETE FROM role_amount WHERE date = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(delRoleAmountQuery)) {
            preparedStatement.setDate(1, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void deleteWorkSlotEntry(Date date) {
        String delWorkSlotQuery = "DELETE FROM work_slot WHERE date = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(delWorkSlotQuery)) {
            preparedStatement.setDate(1, date);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getAvailStaffs(){
        Object[][] data = null;

        try {
            // Get available staff in the selected month and year
            // Available staff is calculated by
            // Total approved bids made in the selected month and year
            // Then, compare whether it's less than the maximum work slot preferred
            // Also ignore staffs who have accepted at that date (slot)
            String query = "SELECT " +
                    "    COALESCE(COUNT(`bid`.`bid_id`), 0) AS `num`, " +
                    "    CONCAT(`user_account`.`f_name`, ' ' ,`user_account`.`l_name`) AS `name`," +
                    "    `user_account`.`username` as `username`, " +
                    "    `role`.`role_name` as `role`, " +
                    "    `user_account`.`max_slot` as `max` " +
                    "FROM `user_account` " +
                    "LEFT JOIN `bid` ON `user_account`.`username` = `bid`.`username`  " +
                    "    AND YEAR(`bid`.`date`) = ? " +
                    "    AND MONTH(`bid`.`date`) = ? " +
                    "    AND (`bid`.`bid_status` = \"Pending\" OR `bid`.`bid_status` = \"Rejected\") " +
                    "LEFT JOIN `profile` ON `user_account`.`profile_id` = `profile`.`profile_id`  " +
                    "LEFT JOIN `role` ON `user_account`.`role_id` = `role`.`role_id` " +
                    "WHERE `profile`.`profile_name` = \"Cafe Staff\" " +
                    "AND `user_account`.`username` NOT IN ( " +
                    "    SELECT `username` FROM `bid` " +
                    "    WHERE YEAR(`date`) = ? " +
                    "    AND MONTH(`date`) = ? " +
                    "    AND (`bid_status` = \"Approved\" OR `bid_status` = \"Assigned\") " +
                    "    AND `date` = ? " +
                    ") " +
                    "GROUP BY `user_account`.`username` " +
                    "HAVING `num` < `max` " +
                    "ORDER BY `role`.`role_name`; ";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, getDate().getYear() + 1900);
            preparedStatement.setInt(2, getDate().getMonth() + 1);
            preparedStatement.setInt(3, getDate().getYear() + 1900);
            preparedStatement.setInt(4, getDate().getMonth() + 1);
            preparedStatement.setDate(5, getDate());


            ResultSet resultSet = preparedStatement.executeQuery();

            // Get the ResultSet metadata to determine the number of columns
            ResultSetMetaData metaData = resultSet.getMetaData();
            int col = metaData.getColumnCount();


            // To hold data
            Vector<Vector<Object>> dataVector = new Vector<>();

            // Collect data
            while (resultSet.next()){
                Vector<Object> row = new Vector<>();
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("username"));
                row.add(resultSet.getString("role"));
                dataVector.add(row);
            }

            // Convert into 2D Object array
            data = new Object[dataVector.size()][col];
            for (int i = 0; i < dataVector.size(); i++){

                data[i] = dataVector.get(i).toArray();

            }

            // close resources
            resultSet.close();
            preparedStatement.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;
    }



}




/*

public class WorkSlot {
    public static final int CHEF = 1;
    public static final int CASHIER = 2;
    public static final int STAFF = 3;
    private String date;
    private int chefAmount;
    private int cashierAmount;
    private int staffAmount;

    public WorkSlot() {
        this.date = "";
        this.chefAmount = 1;
        this.cashierAmount = 1;
        this.staffAmount = 1;
    }

    public WorkSlot(String date, int chefAmount, int cashierAmount, int staffAmount) {
        this.date = date;
        this.chefAmount = chefAmount;
        this.cashierAmount = cashierAmount;
        this.staffAmount = staffAmount;
    }

    public String getDate() {
        return date;
    }

    public int getChefAmount() {
        return chefAmount;
    }

    public int getCashierAmount() {
        return cashierAmount;
    }

    public int getStaffAmount() {
        return staffAmount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setChefAmount(int chefAmount) {
        this.chefAmount = chefAmount;
    }

    public void setCashierAmount(int cashierAmount) {
        this.cashierAmount = cashierAmount;
    }

    public void setStaffAmount(int staffAmount) {
        this.staffAmount = staffAmount;
    }

    public WorkSlot createWorkSlot(String dateString) {
        String workSlotQuery = "INSERT INTO work_slot (date) VALUES (?)";
        try {
            Connection conn = new DBConfig().getConnection();
            try (PreparedStatement preparedStatement = conn.prepareStatement(workSlotQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date parsedDate = sdf.parse(dateString);
                java.sql.Date date = new java.sql.Date(parsedDate.getTime());

                preparedStatement.setDate(1, date);

                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    // Insert into role_amount table
                    insertRoleAmount(date, CHEF, chefAmount);
                    insertRoleAmount(date, CASHIER, cashierAmount);
                    insertRoleAmount(date, STAFF, staffAmount);
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertRoleAmount(Date date, int roleId, int amount) {
        try {
            Connection conn = new DBConfig().getConnection();
            String roleAmountQuery = "INSERT INTO role_amount (date, role_id, amount) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(roleAmountQuery)) {
                preparedStatement.setDate(1, date);
                preparedStatement.setInt(2, roleId);
                preparedStatement.setInt(3, amount);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Role amount created successfully.");
                } else {
                    System.out.println("Failed to create role amount.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<WorkSlot> getAllWorkSlots() {
        List<WorkSlot> workSlots = new ArrayList<>();

        try {
            Connection conn = new DBConfig().getConnection();
            String query = "SELECT * FROM work_slot";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    WorkSlot workSlot = new WorkSlot();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = resultSet.getDate("date");
                    String dateString = sdf.format(parsedDate);
                    workSlot.setDate(dateString);

                    workSlot.setChefAmount(getRoleAmount(workSlot.getDate(), CHEF));
                    workSlot.setCashierAmount(getRoleAmount(workSlot.getDate(), CASHIER));
                    workSlot.setStaffAmount(getRoleAmount(workSlot.getDate(), STAFF));

                    workSlots.add(workSlot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workSlots;
    }

    private int getRoleAmount(String date, int roleId) {
        int amount = 0;

        try {
            Connection conn = new DBConfig().getConnection();
            String query = "SELECT amount FROM role_amount WHERE date = ? AND role_id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, date);
                preparedStatement.setInt(2, roleId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        amount = resultSet.getInt("amount");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return amount;
    }
*/
