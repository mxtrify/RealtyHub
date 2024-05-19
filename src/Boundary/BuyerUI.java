package Boundary;

import Controller.BuyerControl;
import Controller.RateReview.CreateRateReviewControl;
import Controller.Property.*;
import Entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class BuyerUI extends JFrame{
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private JPanel newPropertyPanel;
    private JPanel soldPropertyPanel;
    private JPanel favouritesPanel;
    private JPanel rateReviewPanel;
    private JPanel mortgagePanel;

    private BuyerControl control;

    private DefaultTableModel newPropertyModel;
    private JTextField searchNewProperty;
    private ArrayList<Property> newProperties;
    private String[] newPropertyColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "Sale Status"};

    private DefaultTableModel soldPropertyModel;
    private JTextField searchSoldProperty;
    private ArrayList<Property> soldProperties;
    private String[] soldPropertyColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "Sale Status"};

    private DefaultTableModel favPropertyModel;
    private ArrayList<Property> favProperties;
    private String[] favPropertyColumnNames = {"ListingID", "Name", "Location", "Information", "Price", "Sale Status"};

    // Constructor
    public BuyerUI(UserAccount u) {
        control = new BuyerControl(u);
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
        newPropertyPanel = createNewPropertyPanel(u);
        soldPropertyPanel = createSoldPropertyPanel(u);
        favouritesPanel = createFavPropertyPanel();
        rateReviewPanel = createRateReviewPanel(u);
        mortgagePanel = createMortgagePanel();

        tabbedPane.addTab("Welcome", landingPanel);
        tabbedPane.addTab("View New Properties", newPropertyPanel);
        tabbedPane.addTab("View Sold Properties", soldPropertyPanel);
        tabbedPane.addTab("View Favourites", favouritesPanel);
        tabbedPane.addTab("Rate and Review Agent", rateReviewPanel);
        tabbedPane.addTab("Mortgage Calculator", mortgagePanel);

        // Add the ChangeListener to the JTabbedPane
        tabbedPane.addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            String title = sourceTabbedPane.getTitleAt(index);
            if (title.equals("View Favourites")) {
                getFavouritesList(control.getLoggedInUserID());
                createFavouritesTable(favPropertyModel);
            }
        });

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

    // Buyer New Property Panel
    private JPanel createNewPropertyPanel(UserAccount u) {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchProfile here if not initialized in initializeUI
        searchNewProperty = new JTextField(20); // Ensure it is instantiated here
        searchNewProperty.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchNewProperty.getPreferredSize().height));
        searchNewProperty.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchNewProperties();
                }
            }
        });

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("New Property Management", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Button Panel configured with BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Panel for Save buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton favoriteButton = new JButton("Save as Favourites");
        actionButtonPanel.add(favoriteButton);

        // Search and Buttons Panel
        JPanel searchAndButtonsPanel = new JPanel();
        searchAndButtonsPanel.setLayout(new BoxLayout(searchAndButtonsPanel, BoxLayout.LINE_AXIS));
        searchAndButtonsPanel.add(searchNewProperty);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchNewProperties());
        searchAndButtonsPanel.add(searchButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchNewProperty.setText("");
            searchNewProperties();
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
        newPropertyModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        newPropertyModel.setColumnIdentifiers(newPropertyColumnNames);
        getNewPropertyList();

        // Create the table using the extracted method
        JTable table = createNewPropertyTable(newPropertyModel);
        // Listener to Track Views
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int listingID = (Integer) newPropertyModel.getValueAt(selectedRow, 0);
                    if (new TrackViewsControl().addViews(listingID)) {
                        System.out.println("View Added");
                    } else {
                        System.out.println("View failed to be added");
                    }
                }
            }
        });
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        // Listener to Save Property
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                favoriteButton.setEnabled(table.getSelectedRow() != -1);
            }
        });

        favoriteButton.addActionListener(e -> {
            int ListingID = (Integer) newPropertyModel.getValueAt(table.getSelectedRow(), 0);
            int SaverID = control.getLoggedInUserID();
            if (new FavouritePropertyControl().favouriteProperty(ListingID, SaverID)) {
                getNewPropertyList(); // Refresh the property list
                JOptionPane.showMessageDialog(newPropertyPanel, "Property saved successfully", "Success", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(newPropertyPanel, "Failed to save property", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    // New Property Panel Methods
    private JTable createNewPropertyTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getNewPropertyList() {
        newPropertyModel.setRowCount(0);
        newProperties = new ViewPropertyControl().getNewPropertyList();
        for (Property property : newProperties) {
            newPropertyModel.addRow(new Object[]{
                    property.getListingID(),
                    property.getName(),
                    property.getLocation(),
                    property.getInfo(),
                    property.getPrice(),
                    "Available"
            });
        }
    }

    public void searchNewProperties() {
        String search = searchNewProperty.getText();

        if (search.isEmpty()) {
            getNewPropertyList();
        } else {
            newPropertyModel.setRowCount(0);
            newProperties = new SearchPropertyControl().SearchNewProperty(search);
            for (Property property : newProperties) {
                newPropertyModel.addRow(new Object[]{
                        property.getListingID(),
                        property.getName(),
                        property.getLocation(),
                        property.getInfo(),
                        property.getPrice(),
                        "Available"
                });
            }
        }
    }

    // Buyer Sold Property Panel
    private JPanel createSoldPropertyPanel(UserAccount u) {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchProfile here if not initialized in initializeUI
        searchSoldProperty = new JTextField(20); // Ensure it is instantiated here
        searchSoldProperty.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchSoldProperty.getPreferredSize().height));
        searchSoldProperty.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchSoldProperties();
                }
            }
        });

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Sold Property Management", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Button Panel configured with BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Panel for Save buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton favoriteButton = new JButton("Save as Favourites");
        actionButtonPanel.add(favoriteButton);

        // Search and Buttons Panel
        JPanel searchAndButtonsPanel = new JPanel();
        searchAndButtonsPanel.setLayout(new BoxLayout(searchAndButtonsPanel, BoxLayout.LINE_AXIS));
        searchAndButtonsPanel.add(searchSoldProperty);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchSoldProperties());
        searchAndButtonsPanel.add(searchButton);
        searchAndButtonsPanel.add(Box.createHorizontalStrut(5));
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            searchSoldProperty.setText("");
            searchSoldProperties();
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
        soldPropertyModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        soldPropertyModel.setColumnIdentifiers(soldPropertyColumnNames);
        getSoldPropertyList();

        // Create the table using the extracted method
        JTable table = createSoldPropertyTable(soldPropertyModel);
        // Listener to Track Views
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int listingID = (Integer) soldPropertyModel.getValueAt(selectedRow, 0);
                    if (new TrackViewsControl().addViews(listingID)) {
                        System.out.println("View Added");
                    } else {
                        System.out.println("View failed to be added");
                    }
                }
            }
        });
        JScrollPane scrollPane = createTableScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for the logout button
        logoutPanel.add(logoutButton);
        panel.add(logoutPanel, BorderLayout.SOUTH);

        // Listener to Save Property
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                favoriteButton.setEnabled(table.getSelectedRow() != -1);
            }
        });

        favoriteButton.addActionListener(e -> {
            int ListingID = (Integer) soldPropertyModel.getValueAt(table.getSelectedRow(), 0);
            int SaverID = control.getLoggedInUserID();
            if (new FavouritePropertyControl().favouriteProperty(ListingID, SaverID)) {
                getSoldPropertyList(); // Refresh the property list
                JOptionPane.showMessageDialog(soldPropertyPanel, "Property saved successfully", "Success", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(soldPropertyPanel, "Failed to save property", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    // Sold Property Panel Methods
    private JTable createSoldPropertyTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getSoldPropertyList() {
        soldPropertyModel.setRowCount(0);
        soldProperties = new ViewPropertyControl().getSoldPropertyList();
        for (Property property : soldProperties) {
            soldPropertyModel.addRow(new Object[]{
                    property.getListingID(),
                    property.getName(),
                    property.getLocation(),
                    property.getInfo(),
                    property.getPrice(),
                    "Sold"
            });
        }
    }

    public void searchSoldProperties() {
        String search = searchSoldProperty.getText();

        if (search.isEmpty()) {
            getSoldPropertyList();
        } else {
            soldPropertyModel.setRowCount(0);
            soldProperties = new SearchPropertyControl().SearchSoldProperty(search);
            for (Property property : soldProperties) {
                soldPropertyModel.addRow(new Object[]{
                        property.getListingID(),
                        property.getName(),
                        property.getLocation(),
                        property.getInfo(),
                        property.getPrice(),
                        "Sold"
                });
            }
        }
    }

    // Buyer Favourite Property Panel
    private JPanel createFavPropertyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("My Favourites", JLabel.LEFT);
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

    // Favourite Methods
    private JTable createFavouritesTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    public void getFavouritesList(int buyerid) {
        favPropertyModel.setRowCount(0);
        favProperties = new FavouritePropertyControl().getFavouriteProperty(buyerid);
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
                    saleStatus
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

    // Buyer Mortgage Calculator
    private JPanel createMortgagePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel with BorderLayout
        JPanel panel = new JPanel(new GridBagLayout()); // Sub-panel with GridBagLayout
        mainPanel.add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        MortgageControl mortgageControl = new MortgageControl();

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Mortgage Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Fields and Labels
        JTextField loanAmountField = new JTextField(10);
        JTextField interestRateField = new JTextField(10);
        JTextField loanTermField = new JTextField(10);

        // Local function to add fields and labels
        BiConsumer<String, JComponent> addFieldAndLabel = (labelText, field) -> {
            JLabel label = new JLabel(labelText);
            gbc.gridx = 0;
            panel.add(label, gbc);

            gbc.gridx = 1;
            panel.add(field, gbc);

            gbc.gridy++;
        };

        addFieldAndLabel.accept("Loan Amount:", loanAmountField);
        addFieldAndLabel.accept("Interest Rate (%):", interestRateField);
        addFieldAndLabel.accept("Loan Term (years):", loanTermField);

        // Button panel for Calculate and Clear
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(clearButton);
        buttonPanel.add(calculateButton);
        panel.add(buttonPanel, gbc);

        // Result label
        gbc.gridy++;
        JLabel resultLabel = new JLabel("Your monthly payment will appear here.", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(resultLabel, gbc);

        // Setup action listeners for Calculate
        calculateButton.addActionListener(e -> {
            try {
                double loanAmount = Double.parseDouble(loanAmountField.getText());
                double interestRate = Double.parseDouble(interestRateField.getText());
                int loanTerm = Integer.parseInt(loanTermField.getText());

                // Validation checks
                if (loanAmount >= 230000000) {
                    JOptionPane.showMessageDialog(panel, "Loan amount must be less than $230,000,000.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (interestRate >= 10) {
                    JOptionPane.showMessageDialog(panel, "Interest rate must be less than 10%.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (loanTerm < 10 || loanTerm > 35) {
                    JOptionPane.showMessageDialog(panel, "Loan term must be between 10 and 35 years.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double mortgagePayment = mortgageControl.getMortgage(loanAmount, interestRate, loanTerm);
                resultLabel.setText(String.format("Monthly Mortgage Payment: $%.2f", mortgagePayment));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Setup action listeners for Clear
        clearButton.addActionListener(e -> {
            loanAmountField.setText("");
            interestRateField.setText("");
            loanTermField.setText("");
            resultLabel.setText("Your monthly payment will appear here.");
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
    private void performLogout() {
        dispose();
        new LoginUI();
    }
}
