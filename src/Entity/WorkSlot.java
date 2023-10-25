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


}
