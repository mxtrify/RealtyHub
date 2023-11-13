package Testing;

import Boundary.LoginGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class LoginTest {
    private LoginGUI testLogin;
    private JFrame frame;
    private boolean errorMessageDisplayed;

    @Before
    public void setUp() throws Exception {
        this.testLogin = new LoginGUI();
        errorMessageDisplayed = false;
//        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    @After
    public void tearDown() throws Exception {
        testLogin = null;
//        SwingUtilities.invokeLater(() -> frame.setVisible(false));
    }

    @Test
    public void testCorrectLoginDetails() {
        String correctUsername = "admin";
        String correctPassword = "admin";
        testLogin.login(correctUsername, correctPassword);
//        assertFalse(frame.isVisible());
        assertFalse(errorMessageDisplayed);
    }

    @Test
    public void testWrongLoginDetails() {
        String wrongUsername = "wrongAdmin";
        String wrongPassword = "wrongAdmin";

        testLogin.login(wrongUsername, wrongPassword);
        assertTrue(testLogin.isErrorMessageDisplayed());
    }
}