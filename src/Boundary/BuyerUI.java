package Boundary;

import Controller.PropertyController;
import Controller.SoldPropertyController;
import Entity.Property;
import Entity.RealEstateAgent;
import Entity.UserAccount;
import Entity.UserProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BuyerUI extends JFrame {
    // Declare Variables
    private JTabbedPane tabbedPane;
    private JPanel propertyPanel;
    private JPanel soldPropertyPanel;
    private JPanel agentRatingPanel;
    private JPanel mortgageCalculatorPanel;

    private DefaultTableModel propertyTableModel;
    private DefaultTableModel soldPropertyTableModel;
    private DefaultTableModel agentRatingTableModel;

    private JTextField searchTextField;
    private JButton searchButton;

    private ArrayList<Property> properties;
    private ArrayList<Property> soldProperties;
    private ArrayList<RealEstateAgent> agents;

    private String[] propertyColumnNames = {"Property Name", "Location", "Price", "Status"};
    private String[] soldPropertyColumnNames = {"Property Name", "Location", "Price", "Sold Date"};
    private String[] agentRatingColumnNames = {"Agent Name", "Rating", "Review"};


    public BuyerUI(UserAccount u) {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Buyer dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        propertyPanel = createPropertyPanel();
        soldPropertyPanel = createSoldPropertyPanel();
        agentRatingPanel = createAgentRatingPanel();
        mortgageCalculatorPanel = createMortgageCalculatorPanel();

        tabbedPane.addTab("New Properties", propertyPanel);
        tabbedPane.addTab("Sold Properties", soldPropertyPanel);
        tabbedPane.addTab("Agent Ratings", agentRatingPanel);
        tabbedPane.addTab("Mortgage Calculator", mortgageCalculatorPanel);

        add(tabbedPane);
        setVisible(true);
    }
    private JPanel createPropertyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Initialize the searchTextField here if not initialized in initializeUI
        searchTextField = new JTextField(20); // Ensure it is instantiated here
        searchTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchTextField.getPreferredSize().height));
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProperties();
                }
            }
        });
    
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("New Properties", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProperties());
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        // Combine header and search in a single panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(searchPanel, BorderLayout.CENTER);

        // Create a table model with non-editable cells
        propertyTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        propertyTableModel.setColumnIdentifiers(propertyColumnNames);
        getProperties();

        // Create the table
        JTable propertyTable = createTable(propertyTableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = createScrollPane(propertyTable);

        // Add components to the main panel
        panel.add(combinedPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    private JPanel createSoldPropertyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Initialize the searchTextField here if not initialized in initializeUI
        searchTextField = new JTextField(20); // Ensure it is instantiated here
        searchTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchTextField.getPreferredSize().height));
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchSoldProperties();
                }
            }
        });
    
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Sold Properties", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
    
        // Search panel
        JPanel searchPanel = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchSoldProperties());
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
    
        // Combine header and search in a single panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(searchPanel, BorderLayout.CENTER);
    
        // Create a table model with non-editable cells
        soldPropertyTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
    
        soldPropertyTableModel.setColumnIdentifiers(soldPropertyColumnNames);
        getSoldProperties();
    
        // Create the table
        JTable soldPropertyTable = createTable(soldPropertyTableModel);
    
        // Add the table to a scroll pane
        JScrollPane scrollPane = createScrollPane(soldPropertyTable);
    
        // Add components to the main panel
        panel.add(combinedPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        return panel;
    }
    private JPanel createAgentRatingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Initialize the searchTextField here if not initialized in initializeUI
        searchTextField = new JTextField(20); // Ensure it is instantiated here
        searchTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchTextField.getPreferredSize().height));
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchAgentRatings();
                }
            }
        });
    
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Agent Ratings", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
    
        // Search panel
        JPanel searchPanel = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchAgentRatings());
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
    
        // Combine header and search in a single panel
        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(headerPanel, BorderLayout.NORTH);
        combinedPanel.add(searchPanel, BorderLayout.CENTER);
    
        // Create a table model with non-editable cells
        agentRatingTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
    
        agentRatingTableModel.setColumnIdentifiers(agentRatingColumnNames);
        getAgentRatings();
    
        // Create the table
        JTable agentRatingTable = createTable(agentRatingTableModel);
    
        // Add the table to a scroll pane
        JScrollPane scrollPane = createScrollPane(agentRatingTable);
    
        // Add components to the main panel
        panel.add(combinedPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        return panel;
    }
    private void searchAgentRatings() {
        // Clear the existing table data
        agentRatingTableModel.setRowCount(0);
    
        // Get the search term from the search text field
        String searchTerm = searchTextField.getText().toLowerCase();
    
        // Perform the search based on the provided search term
        for (RealEstateAgent agent : agents) {
            // Check if the agent name or any other relevant information matches the search term
            if (agent.getName().toLowerCase().contains(searchTerm)) {
                // Add the matching agent to the table model
                agentRatingTableModel.addRow(new Object[]{
                        agent.getName(),
                        agent.getRating(),
                        agent.getReview()
                });
            }
        }
    }
    private JPanel createMortgageCalculatorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Mortgage Calculator", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
    
        // Create input fields for loan amount, interest rate, and loan term
        JLabel loanAmountLabel = new JLabel("Loan Amount:");
        JTextField loanAmountField = new JTextField(10);
    
        JLabel interestRateLabel = new JLabel("Interest Rate (%):");
        JTextField interestRateField = new JTextField(10);
    
        JLabel loanTermLabel = new JLabel("Loan Term (years):");
        JTextField loanTermField = new JTextField(10);
    
        // Create button to calculate mortgage payment
        JButton calculateButton = new JButton("Calculate");
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
    
        calculateButton.addActionListener(e -> {
            // Get values from input fields
            double loanAmount = Double.parseDouble(loanAmountField.getText());
            double interestRate = Double.parseDouble(interestRateField.getText());
            int loanTerm = Integer.parseInt(loanTermField.getText());
    
            // Calculate mortgage payment using formula
            double monthlyInterestRate = interestRate / 1200.0; // Monthly interest rate
            int numberOfPayments = loanTerm * 12; // Total number of payments
            double mortgagePayment = loanAmount * monthlyInterestRate /
                    (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
    
            // Display result
            resultLabel.setText("Monthly Mortgage Payment: $" + String.format("%.2f", mortgagePayment));
        });
    
        // Create panel to hold input fields and button
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(loanAmountLabel);
        inputPanel.add(loanAmountField);
        inputPanel.add(interestRateLabel);
        inputPanel.add(interestRateField);
        inputPanel.add(loanTermLabel);
        inputPanel.add(loanTermField);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(calculateButton);
    
        // Add header and input panel to the main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(resultLabel, BorderLayout.SOUTH);
    
        return panel;
    }
    private void searchProperties() {
        String searchQuery = searchTextField.getText().trim(); // Get the search query from the text field
    
        if (searchQuery.isEmpty()) {
            // If the search query is empty, display all properties
            getProperties();
        } else {
            // If there is a search query, clear the table model
            propertyTableModel.setRowCount(0);
    
            // Filter properties based on the search query
            ArrayList<Property> filteredProperties = new ArrayList<>();
            for (Property property : properties) {
                // Check if the property name or location contains the search query (case-insensitive)
                if (property.getPropertyName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    property.getLocation().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredProperties.add(property);
                }
            }
    
            // Add filtered properties to the table model
            for (Property property : filteredProperties) {
                propertyTableModel.addRow(new Object[]{
                        property.getPropertyName(),
                        property.getLocation(),
                        property.getPrice(),
                        property.getStatus()
                });
            }
        }
    }
    private void searchSoldProperties() {
        String searchQuery = searchTextField.getText().trim(); // Get the search query from the text field
        
        if (searchQuery.isEmpty()) {
            getSoldProperties();
        } else {
            soldPropertyTableModel.setRowCount(0); // Clear existing rows
            ArrayList<Property> filteredProperties = new ArrayList<>();
            // Assuming soldProperties is a list of Property objects containing sold properties
            for (Property property : soldProperties) {
                // Perform search based on property attributes like name, location, etc.
                if (property.getPropertyName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    property.getLocation().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredProperties.add(property);
                }
            }
            // Add filtered properties to the table model
            for (Property property : filteredProperties) {
                soldPropertyTableModel.addRow(new Object[]{
                        property.getPropertyName(),
                        property.getLocation(),
                        property.getPrice(),
                        property.getSoldDate()
                });
            }
        }
    }
    private void getProperties() {
        // Assuming there's a controller method to retrieve properties
        PropertyController propertyController = new PropertyController();
    
        // Fetch properties from the database or other data source
        properties = propertyController.getProperties();
    
        // Clear existing rows from the table model
        propertyTableModel.setRowCount(0);
    
        // Populate the table model with fetched properties
        for (Property property : properties) {
            // Assuming Property object has getters for name, location, price, and status
            propertyTableModel.addRow(new Object[]{
                    property.getPropertyName(),
                    property.getLocation(),
                    property.getPrice(),
                    property.getStatus()
            });
        }
    }
    private void getSoldProperties() {
        // Assuming you have a method in your controller or service layer to retrieve sold properties
        // This method may vary depending on how you fetch data in your application architecture
        
        // Example: Fetch sold properties from a controller
        SoldPropertyController soldPropertyController = new SoldPropertyController();
        soldProperties = soldPropertyController.getSoldProperties();
        
        // Clear existing rows in the table model
        soldPropertyTableModel.setRowCount(0);
        
        // Populate the table model with sold properties
        for (Property property : soldProperties) {
            soldPropertyTableModel.addRow(new Object[]{
                    property.getPropertyName(),
                    property.getLocation(),
                    property.getPrice(),
                    property.getSoldDate()
            });
        }
    }
    private void getAgentRatings() {
        // Clear existing data in the table model
        agentRatingTableModel.setRowCount(0);
    
        // Fetch agent ratings from the database or any other data source
        agents = new ArrayList<>(); // Assuming agents are fetched from a data source
    
        // Iterate through the list of agents
        for (RealEstateAgent agent : agents) {
            // Extract agent information
            String agentName = agent.getName();
            double rating = agent.getRating();
            String review = agent.getReview(); // Assuming the review is stored as a String
    
            // Add agent information to the table model
            agentRatingTableModel.addRow(new Object[]{agentName, rating, review});
        }
    }


    private JTable createTable(DefaultTableModel model) {
        // Create a table with single selection mode
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    private JScrollPane createScrollPane(JTable table) {
        // Create a scroll pane for the table
        return new JScrollPane(table);
    }


    // Logout Procedure
    private void performLogout() {
        dispose();
        new LoginUI();
    }
}