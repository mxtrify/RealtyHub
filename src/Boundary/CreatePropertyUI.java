package Boundary;

import Controller.Property.*;
import Entity.Property;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CreatePropertyUI extends JFrame {
    // Declare Variables
    private JTextField nameField, locationField, infoField, priceField;
    private JComboBox<Integer> sellerComboBox;

    private JButton backButton, createButton;

    public CreatePropertyUI(UserAccount userAccount) {
        initializeUI(userAccount);
    }

    // Sets up the main UI components of the Create User Account window
    private void initializeUI(UserAccount userAccount) {
        setTitle("Create New Property Listing");
        setSize(1200, 800); // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane(), userAccount);
        setVisible(true);
    }

    // Adds Create User Account Form components to the panel
    private void addComponentsToPane(Container pane, UserAccount userAccount) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Create New Property");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30)); // Updated font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        addFieldAndLabel("Name:", nameField = new JTextField(20), pane, gbc);
        addFieldAndLabel("Location:", locationField = new JTextField(20), pane, gbc);
        addFieldAndLabel("Information:", infoField = new JTextField(20), pane, gbc);
        addFieldAndLabel("Price:", priceField = new JTextField(20), pane, gbc);

        // Profile ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel profileLabel = new JLabel("Seller ID:");
        pane.add(profileLabel, gbc);

        ArrayList<Integer> sellerList = new CreatePropertyControl().getSellerList();
        Integer[] sellerArray = sellerList.toArray(new Integer[0]);  // Convert ArrayList<Integer> to Integer[]
        sellerComboBox = new JComboBox<>(new DefaultComboBoxModel<>(sellerArray));
        gbc.gridx = 1;
        pane.add(sellerComboBox, gbc);

        // Back and Create Buttons
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        backButton = new JButton("Back");
        createButton = new JButton("Create");

        int buttonWidth = priceField.getPreferredSize().width / 2 - 5;
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        createButton.setPreferredSize(new Dimension(buttonWidth, 25));

        buttonPanel.add(backButton);
        buttonPanel.add(createButton);
        pane.add(buttonPanel, gbc);

        setupActionListeners(userAccount);
    }

    private void addFieldAndLabel(String labelText, JComponent field, Container pane, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        pane.add(label, gbc);

        gbc.gridx = 1;
        pane.add(field, gbc);

        gbc.gridy++;
    }

    // Configure action listeners for buttons
    private void setupActionListeners(UserAccount userAccount) {
        backButton.addActionListener(e -> {
            dispose(); // Closes the window
            new SysAdminUI(userAccount); // Assumes SysAdminUI is another JFrame that needs to be shown
        });

        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String location = locationField.getText();
            String info = infoField.getText();
            String priceText = priceField.getText();
            int price = 0;
            try {
                price = Integer.parseInt(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.isEmpty() || location.isEmpty() || info.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please don't leave any field empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Integer sellerId = (Integer) sellerComboBox.getSelectedItem();
                UserAccount sellerAccount = new UserAccount(sellerId);
                Property newProperty = new Property(name, location, info, price, sellerAccount);
                if (new CreatePropertyControl().addProperty(newProperty)) {
                    JOptionPane.showMessageDialog(this, "Property listing successfully created", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new AgentUI(userAccount);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create property listing", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}