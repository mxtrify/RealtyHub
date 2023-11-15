package Testing;

import Boundary.LoginGUI;
import Controller.LoginController;
import Entity.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class LoginTest {
    private LoginController testLogin;
    private JFrame frame;
    //private boolean errorMessageDisplayed;

    @Before
    public void setUp() throws Exception {
        this.testLogin = new LoginController();
        //errorMessageDisplayed = false;
        //System.setProperty("java.awt.headless", "true");
        //GraphicsEnvironment.getLocalGraphicsEnvironment();
    }

    @After
    public void tearDown() throws Exception {
        testLogin = null;
    }

    @Test
    public void testCorrectLoginDetails() {
        String correctUsername = "admin";
        String correctPassword = "admin";

        assertEquals(correctPassword, testLogin.login(correctUsername, correctPassword).getUsername());
        assertEquals(correctPassword, testLogin.login(correctUsername, correctPassword).getPassword());
    }

    @Test
    public void testWrongLoginDetails() {
        String wrongUsername = "wrongAdmin";
        String wrongPassword = "wrongAdmin";
        assertNull(testLogin.login(wrongUsername, wrongPassword));

    }
}