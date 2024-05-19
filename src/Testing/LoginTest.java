package Testing;

import Controller.LoginControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.swing.*;

import static org.junit.Assert.*;

public class LoginTest {
    private LoginControl testLogin;
    private JFrame frame;

    @Before
    public void setUp() throws Exception {
        this.testLogin = new LoginControl();
    }

    @After
    public void tearDown() throws Exception {
        testLogin = null;
    }

    @Test
    public void testCorrectLoginDetails() {
        String correctUsername = "admin1";
        String correctPassword = "admin1";

        // testLogin.login() will return UserName Object
        assertEquals(correctPassword, testLogin.processLogin(correctUsername, correctPassword).getUsername());
        assertEquals(correctPassword, testLogin.processLogin(correctUsername, correctPassword).getPassword());
    }

    @Test
    public void testWrongLoginDetails() {
        String wrongUsername = "wrongAdmin";
        String wrongPassword = "wrongAdmin";

        assertEquals(null, testLogin.processLogin(wrongUsername, wrongPassword));

    }
}