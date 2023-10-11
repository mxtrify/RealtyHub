import com.mysql.cj.protocol.Resultset;

import javax.xml.transform.Result;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccount {
    String DB_URL = "jdbc:mysql://localhost:3306/buddies";
    String DB_USER = "root";
    String DB_PASSWORD = "";

    public boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM user_account WHERE username = '"+ username +"' AND password = '"+ password + "'";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if there's a matching record
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
//
//
//    String db_url = "jdbc:mysql://localhost:3306/buddies";
//    String db_user = "root";
//    String db_password = "";
//
//    public boolean validateLogin(String username, String password) {
//
//        try {
//            String query = "SELECT * FROM user_account WHERE username = '" + username + "' AND password = '" + password + "'";
//            Connection connection = DriverManager.getConnection(db_url, db_user, db_password);
//            Statement statement = connection.createStatement();
//            Resultset resultset = statement.executeQuery(query);
//
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}


//        String query = "SELECT * FROM user_account WHERE username = 'admin' AND password = 'Admin'";
//        try ;
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return resultSet.next(); // Return true if there's a matching record
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }


//    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/buddies";
//        String user = "root";
//        String password = "";
//        try {
//            Connection connection = DriverManager.getConnection(url, user, password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_account WHERE username = 'admin' AND password = 'Admin'");
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//}