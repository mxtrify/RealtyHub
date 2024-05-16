package Boundary;

import Controller.Property.*;
import Entity.Property;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;

public class UpdatePropertyUI extends JFrame {
    // Declare Variables
    private JTextField idField, nameField, locationField, infoField, priceField;
    private JButton backButton, updateButton;

    public UpdatePropertyUI(UserAccount user, Property selected) {
        initializeUI(user, selected);
    }

    // Sets up the main UI components of the Create User Account window
    private void initializeUI(UserAccount user, Property selected) {
        setTitle("Update Property Listing");
        setSize(1200, 800); // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane(), user, selected);
        setVisible(true);
    }

    // Adds Update User Account Form components to the panel
    private void addComponentsToPane(Container pane, UserAccount user, Property selected) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Update Property Listing");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30)); // Updated font and size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        idField = new JTextField(String.valueOf(selected.getListingID()), 20);
        idField.setEnabled(false);
        nameField = new JTextField(selected.getName(), 20);
        locationField = new JTextField(selected.getLocation(), 20);
        infoField = new JTextField(selected.getInfo(), 20);
        priceField = new JTextField(String.valueOf(selected.getPrice()), 20);
        addFieldAndLabel("Listing ID:", idField, pane, gbc);
        addFieldAndLabel("Name:", nameField, pane, gbc);
        addFieldAndLabel("Location:", locationField, pane, gbc);
        addFieldAndLabel("Information:", infoField, pane, gbc);
        addFieldAndLabel("Price:", priceField, pane, gbc);

        // Back and Update Buttons
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        backButton = new JButton("Back");
        updateButton = new JButton("Update");

        int buttonWidth = priceField.getPreferredSize().width / 2 - 5;
        backButton.setPreferredSize(new Dimension(buttonWidth, 25));
        updateButton.setPreferredSize(new Dimension(buttonWidth, 25));

        buttonPanel.add(backButton);
        buttonPanel.add(updateButton);
        pane.add(buttonPanel, gbc);

        setupActionListeners(user, selected);
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
    private void setupActionListeners(UserAccount user, Property selected) {
        backButton.addActionListener(e -> {
            dispose(); // Closes the window
            new AgentUI(user);
        });

        updateButton.addActionListener(e -> {
            String idText = idField.getText();
            int id = Integer.parseInt(idText);
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
                Property updatedProperty = new Property(id, name, location, info, price);
                if (new UpdatePropertyControl().UpdateProperty(updatedProperty)) {
                    JOptionPane.showMessageDialog(this, "Property successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new AgentUI(user);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update property", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
}