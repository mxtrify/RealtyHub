package Boundary;

import Controller.BuyerControl;
import Controller.Property.*;
import Entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BuyerUI extends JFrame{
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel landingPanel;
    private JPanel newPropertyPanel;
    private JPanel soldPropertyPanel;
    private JPanel favouritesPanel;
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
        favouritesPanel = createFavPropertyPanel(u);
        mortgagePanel = createMortgagePanel();

        tabbedPane.addTab("Welcome", landingPanel);
        tabbedPane.addTab("View New Properties", newPropertyPanel);
        tabbedPane.addTab("View Sold Properties", soldPropertyPanel);
        tabbedPane.addTab("View Favourites", favouritesPanel);
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
    private JPanel createFavPropertyPanel(UserAccount u) {
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

    // Buyer Mortgage Calculator
    private JPanel createMortgagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
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

        addFieldAndLabel("Loan Amount:", loanAmountField, panel, gbc);
        addFieldAndLabel("Interest Rate (%):", interestRateField, panel, gbc);
        addFieldAndLabel("Loan Term (years):", loanTermField, panel, gbc);

        // Button panel for Calculate and Clear
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
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

        return panel;
    }

    private void addFieldAndLabel(String labelText, JComponent field, Container pane, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = 0;
        pane.add(label, gbc);

        gbc.gridx = 1;
        pane.add(field, gbc);

        gbc.gridy++;
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
