package Entity;

public class Property {
    private int id;
    String propertytitle;
    String seller;
    String location;
    double price;
    String status;
    String describe;

    public Property( String propertytitle, String seller,String describe, double price, String location, String status ) {
        this.id = id;
        this.propertytitle = propertytitle;
        this.seller = seller;
        this.location = location;
        this.price = price;
        this.status = status;
        this.describe = describe;
    }
    public Property(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropertytitle() {
        return propertytitle;
    }

    public void setPropertytitle(String propertytitle) {
        this.propertytitle = propertytitle;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
