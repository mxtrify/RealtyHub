package Entity;

import Config.DBConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CafeManager extends UserAccount {
    public CafeManager() {
        super();
    }

    public CafeManager(String username, String password) {
        super(username, password);
    }

    public CafeManager(String username, String password, String firstName, String lastName, String email, UserProfile userProfile) {
        super(username, password, firstName, lastName, email, userProfile);
    }

    public CafeManager(String username, String password, String firstName, String lastName, String email, UserProfile userProfile, boolean status) {
        super(username, password, firstName, lastName, email, userProfile, status);
    }

}
