package Boundary;

import Entity.UserAccount;

import javax.swing.*;

public class BidStatusGUI {
    private JFrame frame;

    public BidStatusGUI(UserAccount userAccount) {
        displayBidStatus(userAccount);
    }

    public void displayBidStatus(UserAccount userAccount) {
        frame = new JFrame("Bid Status");
        JPanel panel = new JPanel();
        panel.setLayout(null);




        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
