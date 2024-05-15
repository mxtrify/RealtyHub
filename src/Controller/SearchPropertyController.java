package Controller;

import Entity.Property;
import java.util.ArrayList;

public class SearchPropertyController {
    private PropertyController propertyController;

    public SearchPropertyController() {
        this.propertyController = new PropertyController(); // Or inject it if needed
    }

    public ArrayList<Property> searchProperties(String searchTerm) {
        // Perform the search based on the provided search term
        ArrayList<Property> allProperties = propertyController.getPropertiesFromDatabase();
        ArrayList<Property> filteredProperties = new ArrayList<>();

        // Filter properties based on the search query
        for (Property property : allProperties) {
            // Check if the property name or location contains the search query (case-insensitive)
            if (property.getPropertyName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                property.getLocation().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredProperties.add(property);
            }
        }
        
        return filteredProperties;
    }
}


