package Boundary;

import Controller.Property.ViewPropertyStatsControl;
import Controller.SellerControl;
import Controller.RateReview.CreateRateReviewControl;
import Entity.Property;
import Entity.RealEstateAgent;
import Entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class SellerUI extends JFrame{
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private JPanel viewsPanel;
    private JPanel favouritesPanel;
    private JPanel rateReviewPanel;

    private SellerControl control;

    private DefaultTableModel viewPropertyModel;
    private ArrayList<Property> viewProperties;
    private String[] viewPropertyColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "Sale Status", "Number of Views"};

    private DefaultTableModel favPropertyModel;
    private ArrayList<Property> favProperties;
    private String[] favPropertyColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "Sale Status", "Number of Favourites"};


    // Constructor
    public SellerUI(UserAccount u) {
        control = new SellerControl(u);
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
        viewsPanel = createPropertyViewsPanel();
        favouritesPanel = createPropertyFavsPanel();
        rateReviewPanel = createRateReviewPanel(u);

        tabbedPane.addTab("Welcome", landingPanel);
        tabbedPane.addTab("Views Statistics", viewsPanel);
        tabbedPane.addTab("Favourites Statistics", favouritesPanel);
        tabbedPane.addTab("Rate and Review Agent", rateReviewPanel);

        add(tabbedPane);
        setVisible(true);
    }

    // Buyer Landing Panel
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

    // Seller Property Views Panel
    private JPanel createPropertyViewsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Property Views Statistics", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Create a table propertyModel with non-editable cells
        viewPropertyModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        viewPropertyModel.setColumnIdentifiers(viewPropertyColumnNames);
        getPropertyViewsList(control.getLoggedInUserID());

        // Create the table using the extracted method
        JTable table = createNewPropertyTable(viewPropertyModel);
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Property Views Panel Methods
    private JTable createNewPropertyTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getPropertyViewsList(int sellerid) {
        viewPropertyModel.setRowCount(0);
        viewProperties = new ViewPropertyStatsControl().getViewsList(sellerid);
        for (Property viewProperty : viewProperties) {
            String saleStatus;
            if (viewProperty.isSaleStatus()) {
                saleStatus = "Available";
            } else {
                saleStatus = "Sold";
            }
            viewPropertyModel.addRow(new Object[]{
                    viewProperty.getListingID(),
                    viewProperty.getName(),
                    viewProperty.getLocation(),
                    viewProperty.getInfo(),
                    viewProperty.getPrice(),
                    saleStatus,
                    viewProperty.getFavouritesCount()
            });
        }
    }

    // Seller Property Favourites Panel
    private JPanel createPropertyFavsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Property Favourites Statistics", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Create a table reviewsModel with non-editable cells
        favPropertyModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        favPropertyModel.setColumnIdentifiers(favPropertyColumnNames);
        getFavouritesList(control.getLoggedInUserID());

        // Create the table using the extracted method
        JTable table = createFavouritesTable(favPropertyModel);
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Property Favourites Panel Methods
    private JTable createFavouritesTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getFavouritesList(int sellerid) {
        favPropertyModel.setRowCount(0);
        favProperties = new ViewPropertyStatsControl().getFavouritesList(sellerid);
        for (Property favProperty : favProperties) {
            String saleStatus;
            if (favProperty.isSaleStatus()) {
                saleStatus = "Available";
            } else {
                saleStatus = "Sold";
            }
            favPropertyModel.addRow(new Object[]{
                    favProperty.getListingID(),
                    favProperty.getName(),
                    favProperty.getLocation(),
                    favProperty.getInfo(),
                    favProperty.getPrice(),
                    saleStatus,
                    favProperty.getFavouritesCount()
            });
        }
    }

    // Buyer Rate and Review Agent
    private JPanel createRateReviewPanel(UserAccount u) {
        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel with BorderLayout
        JPanel panel = new JPanel(new GridBagLayout()); // Sub-panel with GridBagLayout
        mainPanel.add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Rate and Review Agent");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Fields and Labels
        JComboBox<Byte> rateField = new JComboBox<>();
        rateField.addItem(null); // Adds a blank option
        for (byte i = 1; i <= 5; i++) {
            rateField.addItem(i); // Adds numbers 1 to 5
        }
        JTextField reviewField = new JTextField(10);

        // Retrieve user ID and user type
        int userID = control.getLoggedInUserID();
        int userType = control.getLoggedInUserType();

        String UserType = "";
        if (userType == 3) {
            UserType = "Buyer";
        } else if (userType == 4) {
            UserType =  "Seller";
        }

        // Add User ID and User Type labels
        BiConsumer<String, String> addLabel = (labelText, value) -> {
            JLabel label = new JLabel(labelText + value);
            gbc.gridx = 0;
            panel.add(label, gbc);

            gbc.gridx = 1; // Optionally add an empty label or component to align grid
            panel.add(new JLabel(""), gbc);

            gbc.gridy++;
        };

        addLabel.accept("UserID: ", Integer.toString(userID));
        addLabel.accept("User Type: ", UserType);

        // Profile ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel profileLabel = new JLabel("AgentID:");
        panel.add(profileLabel, gbc);

        gbc.gridx = 1;
        ArrayList<Integer> agentList = control.getAgentList();
        Integer[] agentArray = agentList.toArray(new Integer[0]);  // Convert ArrayList<Integer> to Integer[]
        JComboBox<Integer> agentComboBox = new JComboBox<>(new DefaultComboBoxModel<>(agentArray));
        panel.add(agentComboBox, gbc);
        gbc.gridy++;

        // Local function to add fields and labels
        BiConsumer<String, JComponent> addFieldAndLabel = (labelText, field) -> {
            JLabel label = new JLabel(labelText);
            gbc.gridx = 0;
            panel.add(label, gbc);

            gbc.gridx = 1;
            panel.add(field, gbc);

            gbc.gridy++;
        };
        addFieldAndLabel.accept("Give Agent Rating:", rateField);
        addFieldAndLabel.accept("Give Agent Review:", reviewField);

        // Button panel for Calculate and Clear
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton submitButton = new JButton("Submit Rating and Review");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);
        panel.add(buttonPanel, gbc);

        // Setup action listeners for Calculate
        submitButton.addActionListener(e -> {
            int ReviewID = userID;
            int ReviewType = userType;
            Byte rate = (Byte) rateField.getSelectedItem();
            String review = reviewField.getText();

            if (rate == null || review.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please don't leave any field empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Integer agentId = (Integer) agentComboBox.getSelectedItem();
                RealEstateAgent newRateReview = new RealEstateAgent(ReviewID, ReviewType, agentId, rate, review);
                if (new CreateRateReviewControl().addRateReview(newRateReview)) {
                    JOptionPane.showMessageDialog(panel, "Successfully left a review.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to leave a review.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Setup action listeners for Clear
        clearButton.addActionListener(e -> {
            rateField.setSelectedItem(null);
            reviewField.setText("");
        });

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    // Global Methods
    // Create a scroll pane for the table
    private JScrollPane createTableScrollPane(JTable table) {
        return new JScrollPane(table);
    }

    // Logout Procedure
    public void performLogout() {
        dispose();
        new LoginUI();
    }
}
