package Testing;

import Boundary.AgentUI;
import Boundary.BuyerUI;
import Boundary.SellerUI;
import Boundary.SysAdminUI;

import Entity.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogoutTest {
    private SysAdminUI adminLogout;
    private AgentUI agentLogout;
    private BuyerUI buyerLogout;
    private SellerUI sellerLogout;

    @Before
    public void setUp() throws Exception {
        UserAccount sa = new UserAccount("SystemAdmin", "SystemAdmin", "System", "Admin", null, true);
        UserAccount a = new UserAccount("REAgent", "REAgent", "Real Estate", "Agent", null, true);
        UserAccount b = new UserAccount("Buyer", "Buyer", "Buyer", "Buyer", null, true);
        UserAccount s = new UserAccount("Seller", "Seller", "Seller", "Seller", null, true);

        this.adminLogout = new SysAdminUI(sa);
        this.agentLogout = new AgentUI(a);
        this.buyerLogout = new BuyerUI(b);
        this.sellerLogout = new SellerUI(s);

    }

    @After
    public void tearDown() {
        adminLogout = null;
        agentLogout = null;
        buyerLogout = null;
        sellerLogout = null;
    }

    @Test
    public void testAdminLogout() {
        adminLogout.performLogout();
        assertFalse(adminLogout.isVisible());
    }

    @Test
    public void testAgentLogout() {
        agentLogout.performLogout();
        assertFalse(agentLogout.isVisible());
    }

    @Test
    public void testBuyerLogout() {
        buyerLogout.performLogout();
        assertFalse(buyerLogout.isVisible());
    }

    @Test
    public void testSellerLogout() {
        sellerLogout.performLogout();
        assertFalse(sellerLogout.isVisible());
    }
}
