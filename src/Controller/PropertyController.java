package Controller;

import Config.DBConfig;
import Entity.Property;
import Entity.WorkSlot;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PropertyController {
    public void insertProperty(Property p) {
        if (null != p) {
            String sql = "insert into property (propertytitle,seller,location,price,status,describe1)" +
                    " values(?,?,?,?,?,?)";

            try {
                Connection conn = new DBConfig().getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, p.getPropertytitle());
                preparedStatement.setString(2, p.getSeller());
                preparedStatement.setString(3, p.getLocation());
                preparedStatement.setString(4, p.getPrice()+"");
                preparedStatement.setString(5, p.getStatus());
                preparedStatement.setString(6, p.getDescribe());

                int row = preparedStatement.executeUpdate();
               /* if (row > 0) {
                    // Insert into role_amount table


                   // return new Property;
                }*/
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    public void updateProperty(Property p) {
        if (null != p) {
            String sql = "update  property set propertytitle=?,seller=?,location=?,price=?,status=?,describe1=? where id=?" ;

            try {
                Connection conn = new DBConfig().getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, p.getPropertytitle());
                preparedStatement.setString(2, p.getSeller());
                preparedStatement.setString(3, p.getLocation());
                preparedStatement.setDouble(4, p.getPrice());
                preparedStatement.setString(5, p.getStatus());
                preparedStatement.setString(6, p.getDescribe());
                preparedStatement.setInt(7, p.getId());

                int row = preparedStatement.executeUpdate();
               /* if (row > 0) {
                    // Insert into role_amount table


                   // return new Property;
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
    public boolean deleteProperty(String id) {
        String sql = "delete from property where id = ?";
        Connection conn = new DBConfig().getConnection();
        int row = 0;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
             row=preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        if(row>0){

            return true;
        }else{
            return false;
        }
    }
}