package Boundary;

import Entity.UserAccount;

import javax.swing.*;

public class CreateUserProfileGUI {
    private JFrame frame;

    public CreateUserProfileGUI() {

    }

    public CreateUserProfileGUI(UserAccount u) {
        displayCreateUserProfile(u);
    }

    private void displayCreateUserProfile(UserAccount u) {
        frame = new JFrame("Create User Profile");
        JPanel panel = new JPanel();
        panel.setLayout(null);




        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
