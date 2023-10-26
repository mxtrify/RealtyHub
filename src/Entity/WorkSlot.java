package Entity;

import Config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

public class WorkSlot {
    public static final int CHEF = 1;
    public static final int CASHIER = 2;
    public static final int WAITER = 3;
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

    public Object[][] getWorkSlotsLeft() {
        Object[][] data = null;
        int chefLeft = 0;
        int cashierLeft = 0;
        int waiterLeft = 0;
        System.out.println(dateToString());

        try {
            String query = "SELECT COUNT(b.bid_id) as bids " +
                    "FROM bid b JOIN user_account ua ON b.username = ua.username " +
                    "JOIN role r ON ua.role_id = r.role_id " +
                    "WHERE r.role_name = ? AND b.date = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Chef");
            preparedStatement.setDate(2, getDate());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && getChefAmount() != 0) {
                chefLeft = getChefAmount() - resultSet.getInt("bids");
            } else {
                return null;
            }

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Cashier");
            preparedStatement.setDate(2, getDate());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() && getCashierAmount() != 0){
                cashierLeft = getCashierAmount() - resultSet.getInt("bids");
            }
            else {
                return null;
            }

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Waiter");
            preparedStatement.setDate(2, getDate());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() && getWaiterAmount() != 0){
                waiterLeft = getWaiterAmount() - resultSet.getInt("bids");
            }
            else {
                return null;
            }

            data = new Object[1][5];
            data[0][0] = dateToString();
            data[0][1] = chefLeft;
            data[0][2] = cashierLeft;
            data[0][3] = waiterLeft;

            if(chefLeft == 0 && cashierLeft == 0 && waiterLeft == 0) {
                data[0][4] = "Not Available";
            }
            else {
                data[0][4] = "Available";
            }

            return data;
        } catch (SQLException e) {
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
