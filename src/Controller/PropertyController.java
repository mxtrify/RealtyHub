package Controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Config.DBConfig;
import Entity.Property;
import Entity.PropertyStatus;

public class PropertyController {
    private ArrayList<Property> properties;
    private DBConfig dbConfig;

    public PropertyController() {
        properties = new ArrayList<>();
        dbConfig = new DBConfig();
    }

    // Method to retrieve properties from the database
    public ArrayList<Property> getPropertiesFromDatabase() {
        ArrayList<Property> propertiesFromDB = new ArrayList<>();
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM properties");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String location = resultSet.getString("address");
                double price = resultSet.getDouble("price");
                PropertyStatus status = PropertyStatus.valueOf(resultSet.getString("status").toUpperCase());

                // Creating Property objects based on the status
                if (status != PropertyStatus.SOLD) {
                    propertiesFromDB.add(new Property(name, location, price, status));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertiesFromDB;
    }

}
