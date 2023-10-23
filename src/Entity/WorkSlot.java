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
    private int workSlotID;
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

    public int getWorkSlotID() {return workSlotID;}

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

    public void setWorkSlotID(int workSlotID) {
        this.workSlotID = workSlotID;
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
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        int workSlotId;  // Declare the variable outside the block
                        if (generatedKeys.next()) {
                            workSlotId = generatedKeys.getInt(1);
                        } else {
                            throw new SQLException("Failed to get work slot ID.");
                        }

                        // Insert into role_amount table
                        insertRoleAmount(workSlotId, CHEF, chefAmount);
                        insertRoleAmount(workSlotId, CASHIER, cashierAmount);
                        insertRoleAmount(workSlotId, STAFF, staffAmount);
                    }
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertRoleAmount(int workSlotId, int roleId, int amount) {
        try {
            Connection conn = new DBConfig().getConnection();
            String roleAmountQuery = "INSERT INTO role_amount (work_slot_id, role_id, amount) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(roleAmountQuery)) {
                preparedStatement.setInt(1, workSlotId);
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
                    workSlot.setWorkSlotID(resultSet.getInt("work_slot_id"));
                    workSlot.setDate(resultSet.getString("date"));
                    workSlot.setChefAmount(getRoleAmount(workSlot.getWorkSlotID(), CHEF));
                    workSlot.setCashierAmount(getRoleAmount(workSlot.getWorkSlotID(), CASHIER));
                    workSlot.setStaffAmount(getRoleAmount(workSlot.getWorkSlotID(), STAFF));

                    workSlots.add(workSlot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workSlots;
    }

    private int getRoleAmount(int workSlotId, int roleId) {
        int amount = 0;

        try {
            Connection conn = new DBConfig().getConnection();
            String query = "SELECT amount FROM role_amount WHERE work_slot_id = ? AND role_id = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, workSlotId);
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
