package Boundary;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegisterView {
    private static RegisterController control = new RegisterController();

    public static void main(String[] args){
        runView();
    }

    private static void runView(){
        JPanel register = new JPanel(new GridLayout(4,1));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Cafe Management System");
        titlePanel.add(title);

        JPanel registerPanel = new JPanel(new GridLayout(2, 2));
        JLabel username = new JLabel("Username");
        JTextField username_txt = new JTextField();
        JLabel pwd = new JLabel("Password");
        JPasswordField pwd_txt = new JPasswordField();
        registerPanel.add(username);
        registerPanel.add(username_txt);
        registerPanel.add(pwd);
        registerPanel.add(pwd_txt);

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] profiles = {"System Admin", "Cafe Owner", "Cafe Manager", "Cafe Staff"};
        JComboBox<String> profile_dropdown = new JComboBox<>(profiles);
        profilePanel.add(profile_dropdown);



        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel error_msg = new JLabel();
        error_msg.add(errorPanel);


        register.add(titlePanel);
        register.add(registerPanel);
        register.add(profilePanel);
        register.add(error_msg);


        int l = JOptionPane.showOptionDialog(null, register, "Register", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Log In"}, JOptionPane.YES_OPTION);

        if (l == JOptionPane.YES_OPTION){
            boolean isRegister = control.register(username_txt.getText(), new String (pwd_txt.getPassword()));

            if(isRegister){
                System.out.println("OK NICE");
            }else{
                System.out.println("Fail liao");
            }
        }

    }
}

class RegisterController{
    private User user = new User();

    public RegisterController(){}

    public boolean register(String username, String password){

        return user.RegisterProcess(username, hashPassword(password));

    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());

            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : bytes) {
                hashedPassword.append(String.format("%02x", b));
            }

            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class User{
    private String username;
    private String password;

    public User(){
        this.username = "";
        this.password = "";
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean RegisterProcess(String username, String password){
        String url = "jdbc:mysql://localhost:3306/test_sql_inj";
        String db_username = "root";
        String db_pwd = "";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, db_username, db_pwd);

            String query = "INSERT INTO user (user_name, user_pwd) VALUES (?, ?)";
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, username);
            prepared.setString(2,password);

            int rowsInserted = prepared.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Boundary.User inserted successfully!");
            }

            prepared.close();
            connection.close();
            return true;



        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return false;


    }
}
