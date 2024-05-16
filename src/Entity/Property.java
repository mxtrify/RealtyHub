package Entity;

import Database.DBConn;
import java.sql.*;
import java.util.ArrayList;

public class Property {
    private Connection conn;
    private int listingID;
    private String name;
    private String location;
    private String info;
    private int price;
    private UserAccount userAccount;
    private boolean saleStatus;

    // Default constructor: Initializes a new user account with empty or default values.
    public Property() {
        this.name = "";
        this.location = "";
        this.info = "";
        this.price = 0;
        this.userAccount = new UserAccount();
        this.saleStatus = false;
    }

    // Parameterized constructor: Initializes a new property with specific values for basic property information.
    public Property(String name, String location, String info, int price, UserAccount userAccount ) {
        this.name = name;
        this.location = location;
        this.info = info;
        this.price = price;
        this.userAccount = userAccount;
    }

    // Parameterized constructor: Initializes a new property with specific values for basic property information.
    public Property(int listingID, String name, String location, String info, int price) {
        this.listingID = listingID;
        this.name = name;
        this.location = location;
        this.info = info;
        this.price = price;
    }

    // Extended parameterized constructor: Includes account status along with basic user and profile information.
    public Property(int listingID, String name, String location, String info, int price, UserAccount userAccount, boolean saleStatus) {
        this.listingID = listingID;
        this.name = name;
        this.location = location;
        this.info = info;
        this.price = price;
        this.userAccount = userAccount;
        this.saleStatus = saleStatus;
    }

    // Getters: Provide access for the property properties.
    public int getListingID() {
        return listingID;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getInfo() {
        return info;
    }

    public int getPrice() {
        return price;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public boolean isSaleStatus() {
        return saleStatus;
    }

    // Retrieves all user accounts from the database.
    public ArrayList<Property> selectAllProperty() {
        ArrayList<Property> properties = new ArrayList<>();
        String query = "SELECT listingID, name, location, info, price, accountID, saleStatus FROM property INNER JOIN user_account ON property.sellerID = user_account.accountID";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int listingID = resultSet.getInt("listingID");
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                String info = resultSet.getString("info");
                int price = resultSet.getInt("price");
                int accountID = resultSet.getInt("accountID");
                boolean saleStatus = resultSet.getBoolean("saleStatus");
                UserAccount userAccount = new UserAccount();
                userAccount.setAccountID(accountID);
                Property property = new Property(listingID, name, location, info, price, userAccount, saleStatus);
                properties.add(property);
            }
            return properties;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // SellerID dropdown
    public ArrayList<Integer> getSellerIDList() {
        String query = "SELECT accountID FROM user_account WHERE profileID = 4";
        ArrayList<Integer> sellerIDList = new ArrayList<>();

        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int accountID = resultSet.getInt("accountID");
                sellerIDList.add(accountID);
            }
            return sellerIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Inserts a new property listing into the database.
    public boolean insertProperty(Property newProperty) {
        Connection conn = null;
        try {
            // Get database connection
            conn = new DBConn().getConnection();

            // Query to check if the seller with the given ID exists
            String sellerQuery = "SELECT accountID FROM user_account WHERE accountID = ?";
            PreparedStatement sellerStatement = conn.prepareStatement(sellerQuery);
            sellerStatement.setInt(1, newProperty.getUserAccount().getAccountID());
            ResultSet sellerResult = sellerStatement.executeQuery();

            if (!sellerResult.next()) {
                // If seller does not exist, we cannot add the property
                return false;
            } else {
                // Insert the new property listing into the property table
                String query = "INSERT INTO property (name, location, info, price, sellerID, saleStatus) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, newProperty.getName());
                preparedStatement.setString(2, newProperty.getLocation());
                preparedStatement.setString(3, newProperty.getInfo());
                preparedStatement.setInt(4, newProperty.getPrice());
                preparedStatement.setInt(5, newProperty.getUserAccount().getAccountID());
                preparedStatement.setBoolean(6, true);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // Ensure connection is closed after operation.
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Get selected property to update
    public Property getSelectedProperty(int ListingID) {
        String query = "SELECT * FROM property WHERE listingID = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ListingID);
            ResultSet resultSet = preparedStatement.executeQuery();
            Property property = null;
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("location");
                String info = resultSet.getString("info");
                int price = resultSet.getInt("price");
                property = new Property(ListingID, name, location, info, price);
            }
            return property;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Updates details for an existing property listing in the database using listingID as the identifier.
    public boolean updateProperty(Property updatedProperty) {
        Connection conn = null;
        try {
            conn = new DBConn().getConnection();
            String query = "UPDATE property SET location = ?, info = ?, price = ? WHERE listingID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, updatedProperty.getLocation());
            preparedStatement.setString(2, updatedProperty.getInfo());
            preparedStatement.setInt(3, updatedProperty.getPrice());
            preparedStatement.setInt(4, updatedProperty.getListingID());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Mark property as sold
    public boolean soldProperty(int ListingID) {
        String query = "UPDATE property SET saleStatus = 0 WHERE listingID = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ListingID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Mark property as available
    public boolean availableProperty(int ListingID) {
        String query = "UPDATE property SET saleStatus = 1 WHERE listingID = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ListingID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Delete property
    public boolean deleteProperty(int ListingID) {
        String query = "DELETE FROM property WHERE listingID = ?";
        try {
            conn = new DBConn().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ListingID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(); // Ensure connection is closed after operation.
        }
    }

    // Search Property
    public ArrayList<Property> getPropertyBySearch(String search) {
        ArrayList<Property> properties = new ArrayList<>();
        String query = "SELECT listingID, name, location, info, price, accountID, saleStatus " +
                "FROM property INNER JOIN user_account ON property.sellerID = user_account.accountID " +
                "WHERE name LIKE ? OR location LIKE ? OR info LIKE ?";
        try (Connection conn = new DBConn().getConnection(); // Use try-with-resources for connection and prepared statement
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            // Set the same search string for name, location, and info
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) { // Use try-with-resources for result set
                while (resultSet.next()) {
                    int listingID = resultSet.getInt("listingID");
                    String name = resultSet.getString("name");
                    String location = resultSet.getString("location");
                    String info = resultSet.getString("info");
                    int price = resultSet.getInt("price");
                    int accountID = resultSet.getInt("accountID");
                    boolean saleStatus = resultSet.getBoolean("saleStatus");
                    UserAccount userAccount = new UserAccount(accountID);
                    Property property = new Property(listingID, name, location, info, price, userAccount, saleStatus);
                    properties.add(property);
                }
            }
            return properties;
        } catch (SQLException e) {
            // Handle or log the SQLException appropriately
            throw new RuntimeException("Failed to retrieve user accounts by search criteria", e);
        }
    }

    // Closes the database connection to release resources.
    private void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}