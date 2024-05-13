package Controller;

import java.util.ArrayList;
import java.util.Date;

import Entity.Property;
import Entity.PropertyStatus;

public class PropertyController {
    private ArrayList<Property> properties;

    public PropertyController() {
        // Initialize the list of properties
        properties = new ArrayList<>();
        // Add some sample properties (for demonstration purposes)
        properties.add(new Property("House 1", "City A", 250000, PropertyStatus.AVAILABLE));
        properties.add(new Property("Apartment 2", "City B", 150000, PropertyStatus.AVAILABLE));
        properties.add(new Property("Villa 3", "City C", 500000, PropertyStatus.SOLD, new Date()));
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Property> searchProperties(String keyword) {
        ArrayList<Property> searchResults = new ArrayList<>();
        for (Property property : properties) {
            if (property.getPropertyName().toLowerCase().contains(keyword.toLowerCase()) ||
                    property.getLocation().toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(property);
            }
        }
        return searchResults;
    }

    public ArrayList<Property> searchSoldProperties(String keyword) {
        ArrayList<Property> soldProperties = new ArrayList<>();
        for (Property property : properties) {
            if (property.getStatus() == PropertyStatus.SOLD &&
                    (property.getPropertyName().toLowerCase().contains(keyword.toLowerCase()) ||
                            property.getLocation().toLowerCase().contains(keyword.toLowerCase()))) {
                soldProperties.add(property);
            }
        }
        return soldProperties;
    }
    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }
}