package Entity;

import java.util.Date;

public class Property {
    private String name;
    private String location;
    private double price;
    private PropertyStatus status;
    private Date soldDate; 

    // Constructor for unsold properties
    public Property(String name, String location, double price, PropertyStatus status) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.status = status;
    }

    // Constructor for sold properties
    public Property(String name, String location, double price, PropertyStatus status, Date soldDate) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.status = status;
        this.soldDate = soldDate;
    }

    // Getters and setters
    public String getPropertyName() {
        return name;
    }

    public void setPropertyName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }
    
}
