package Testing;

import Boundary.SystemAdminGUI;
import Boundary.CafeOwnerGUI;
import Boundary.CafeManagerGUI;
import Boundary.CafeStaffGUI;

import Entity.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogoutTest {
    private SystemAdminGUI adminLogout;
    private CafeOwnerGUI ownerLogout;
    private CafeManagerGUI managerLogout;
    private CafeStaffGUI staffLogout;

    @Before
    public void setUp() throws Exception {
        UserAccount u = new UserAccount();
        this.adminLogout = new SystemAdminGUI(u);
        this.ownerLogout = new CafeOwnerGUI(u);
        this.managerLogout = new CafeManagerGUI(u);
        this.staffLogout = new CafeStaffGUI(u);
    }

    @After
    public void tearDown() {
        adminLogout = null;
        ownerLogout = null;
        managerLogout = null;
        staffLogout = null;
    }

    @Test
    public void testAdminLogout() {
        adminLogout.logout();
    }

    @Test
    public void testOwnerLogout() {
        ownerLogout.logout();
    }

    @Test
    public void testManagerLogout() {
        managerLogout.logout();
    }

    @Test
    public void testStaffLogout() {
        staffLogout.logout();
    }
}
