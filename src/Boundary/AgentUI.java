package Boundary;

import Controller.AgentControl;
import Controller.Property.*;
import Controller.ViewRatingsControl;
import Entity.Property;
import Entity.RealEstateAgent;
import Entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class AgentUI extends JFrame {
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private JPanel propertyPanel;
    private JPanel ratingsPanel;
    private JPanel reviewsPanel;

    private AgentControl control;

    private DefaultTableModel propertyModel;
    private JTextField searchProperty;
    private ArrayList<Property> properties;
    private String[] propertyColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "SellerID", "Sale Status"};

    private DefaultTableModel ratingsModel;
    private ArrayList<RealEstateAgent> ratings;
    private String[] ratingsColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "SellerID", "Sale Status"};

    // Constructor
    public AgentUI(UserAccount u) {
        control = new AgentControl(u);
        initializeUI(u);
    }

    // Initializes the main user interface components
    private void initializeUI(UserAccount u) {
        setTitle("Real Estate Agent Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        landingPanel = createLandingPanel();
        propertyPanel = createPropertyPanel(u);
        ratingsPanel = createRatingPanel(u);

        tabbedPane.addTab("Welcome", landingPanel);
        tabbedPane.addTab("Property Management", propertyPanel);
        tabbedPane.addTab("Agent Ratings", ratingsPanel);

        add(tabbedPane);
        setVisible(true);
    }

    // SysAdmin Landing Panel
    private JPanel createLandingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Container panel to hold multiple labels and the logout button
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Display welcome message for the logged-in user using controller method
        JLabel titleLabel = new JLabel("Welcome, " + control.getLoggedInUserFullName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // Test label below the welcome message
        JLabel testLabel = new JLabel("Click on a tab to start!", JLabel.CENTER);
        testLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        // Add labels to the container panel with constraints
        containerPanel.add(titleLabel, gbc);
        containerPanel.add(testLabel, gbc);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH); // Add the logout button panel to the bottom right corner

        // Add container panel to the main panel
        panel.add(containerPanel, BorderLayout.CENTER);

        return panel;
    }

    // Real Estate Agent Property Panel
    private JPanel createPropertyPanel(UserAccount u) {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchProfile here if not initialized in initializeUI
        searchProperty = new JTextField(20); // Ensure it is instantiated here
        searchProperty.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchProperty.getPreferredSize().height));
        searchProperty.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProperties();
                }
            }
        });

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Property Management", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Button Panel configured with BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Panel for Create, Edit, Suspend buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createPropertyButton = new JButton("Create Listing");
        JButton editButton = new JButton("Update Listing");
        JButton soldButton = new JButton("Mark as Sold");
        JButton deleteButton = new JButton("Delete Listing");
        actionButtonPanel.add(createPropertyButton);
        actionButtonPanel.add(editButton);
        actionButtonPanel.add(soldButton);
        actionButtonPanel.add(deleteButton);

        // Search and Buttons Panel
        JPanel searchAndButtonsPanel = new JPanel();
        searchAndButtonsPanel.setLayout(new BoxLayout(searchAndButtonsPanel, BoxLayout.LINE_AXIS));
        searchAndButtonsPanel.add(searchProperty);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProperties());
        searchAndButtonsPanel.add(searchButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchProperty.setText("");
            searchProperties();
        });
        searchAndButtonsPanel.add(clearButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));

        // Add panels to buttonPanel
        buttonPanel.add(actionButtonPanel, BorderLayout.WEST);
        buttonPanel.add(searchAndButtonsPanel, BorderLayout.EAST);

        // Combine header and buttons in a single panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(combinedPanel, BorderLayout.NORTH);

        // Create a table propertyModel with non-editable cells
        propertyModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        propertyModel.setColumnIdentifiers(propertyColumnNames);
        getPropertyList();

        // Create the table using the extracted method
        JTable table = createProfileTable(propertyModel);
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        // Listener to open Create User Profile UI
        createPropertyButton.addActionListener(e -> {
            dispose();
            new CreatePropertyUI(u);
        });

        // Listener to open Edit Property UI
        editButton.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(propertyPanel, "Please select a property to edit", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                int ListingID = (Integer) propertyModel.getValueAt(table.getSelectedRow(), 0);
                Property property = new UpdatePropertyControl().getSelectedProperty(ListingID);
                new UpdatePropertyUI(u, property);
            }
        });

        // Listener to mark property as sold
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (table.getSelectedRow() != -1) {
                    soldButton.setEnabled(true);
                    String status = propertyModel.getValueAt(table.getSelectedRow(), 6).toString();
                    if (status.equals("Available")) {
                        soldButton.setText("Mark as Sold");
                    } else {
                        soldButton.setText("Mark as Available");
                    }
                }
            }
        });

        soldButton.addActionListener(e -> {
            String status = propertyModel.getValueAt(table.getSelectedRow(), 6).toString();
            int ListingID = (Integer) propertyModel.getValueAt(table.getSelectedRow(), 0);
            if (status.equals("Available")) {
                if (new SoldPropertyControl().soldProperty(ListingID)) {
                    getPropertyList();
                    JOptionPane.showMessageDialog(propertyPanel, "Successfully marked property as sold", "Success", JOptionPane.PLAIN_MESSAGE);
                    soldButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(propertyPanel, "Failed to mark property as sold", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if(new AvailablePropertyControl().availableProperty(ListingID)) {
                    getPropertyList();
                    JOptionPane.showMessageDialog(propertyPanel, "Successfully marked property as available", "Success", JOptionPane.PLAIN_MESSAGE);
                    soldButton.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(propertyPanel, "Failed to mark property as available", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener to delete listing
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                deleteButton.setEnabled(table.getSelectedRow() != -1);
            }
        });

        deleteButton.addActionListener(e -> {
            int ListingID = (Integer) propertyModel.getValueAt(table.getSelectedRow(), 0);
            if (new DeletePropertyControl().deleteProperty(ListingID)) {
                getPropertyList(); // Refresh the property list
                JOptionPane.showMessageDialog(propertyPanel, "Property deleted successfully", "Success", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(propertyPanel, "Failed to delete property", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    // Property Panel Methods
    private JTable createProfileTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getPropertyList() {
        propertyModel.setRowCount(0);
        properties = new ViewPropertyControl().getPropertyList();
        for (Property property : properties) {
            String saleStatus;
            if (property.isSaleStatus()) {
                saleStatus = "Available";
            } else {
                saleStatus = "Sold";
            }
            propertyModel.addRow(new Object[]{
                    property.getListingID(),
                    property.getName(),
                    property.getLocation(),
                    property.getInfo(),
                    property.getPrice(),
                    property.getUserAccount().getAccountID(),
                    saleStatus
            });
        }
    }

    public void searchProperties() {
        String search = searchProperty.getText();

        if (search.isEmpty()) {
            getPropertyList();
        } else {
            propertyModel.setRowCount(0);
            properties = new SearchPropertyControl().SearchProperty(search);
            for (Property property : properties) {
                String saleStatus;
                if (property.isSaleStatus()) {
                    saleStatus = "Available";
                } else {
                    saleStatus = "Sold";
                }
                propertyModel.addRow(new Object[]{
                        property.getListingID(),
                        property.getName(),
                        property.getLocation(),
                        property.getInfo(),
                        property.getPrice(),
                        property.getUserAccount().getAccountID(),
                        saleStatus
                });
            }
        }
    }

    // Global Methods
    // Create a scroll pane for the table
    private JScrollPane createTableScrollPane(JTable table) {
        return new JScrollPane(table);
    }

    // Logout Procedure
    private void performLogout() {
        dispose();
        new LoginUI();
    }
}