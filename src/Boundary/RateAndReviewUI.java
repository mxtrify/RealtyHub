package Boundary;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class RateAndReviewUI extends JFrame {
    private JTextField agentNameField;
    private JRadioButton[] ratingButtons;
    private JTextArea reviewArea;
    private JButton backButton, saveButton;

    public RateAndReviewUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Rate and Review");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentsToPane(getContentPane());
        setVisible(true);
    }

    private void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Rate and Review");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(pane, titleLabel, 0, 0, 2, 1, GridBagConstraints.CENTER);

        JLabel agentNameLabel = new JLabel("Agent Name:");
        agentNameField = new JTextField();
        agentNameField.setEditable(false); // Make the text field uneditable
        addComponent(pane, agentNameLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
        addComponent(pane, agentNameField, 1, 1, 1, 1, GridBagConstraints.EAST);

        JLabel ratingLabel = new JLabel("Rating:");
        addComponent(pane, ratingLabel, 0, 2, 1, 1, GridBagConstraints.WEST);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup ratingGroup = new ButtonGroup();
        ratingButtons = new JRadioButton[5];
        for (int i = 1; i <= 5; i++) {
            ratingButtons[i - 1] = new JRadioButton(String.valueOf(i));
            ratingGroup.add(ratingButtons[i - 1]);
            ratingPanel.add(ratingButtons[i - 1]);
        }
        addComponent(pane, ratingPanel, 1, 2, 1, 1, GridBagConstraints.EAST);

        JLabel reviewLabel = new JLabel("Review:");
        reviewArea = new JTextArea(5, 20);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(reviewArea);
        addComponent(pane, reviewLabel, 0, 3, 1, 1, GridBagConstraints.WEST);
        addComponent(pane, scrollPane, 1, 3, 1, 1, GridBagConstraints.EAST);

        backButton = new JButton("Back");


        saveButton = new JButton("Save");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        addComponent(pane, buttonPanel, 1, 4, 1, 1, GridBagConstraints.EAST);

        setupActionListeners();
    }

    private void addComponent(Container container, Component component, int x, int y, int width, int height, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = anchor;
        gbc.insets = new Insets(10, 10, 10, 10);
        container.add(component, gbc);
    }

    private void setupActionListeners() {
        backButton.addActionListener(e -> {
            // Handle back action
            System.out.println("Back button clicked");
            dispose();
        });

        saveButton.addActionListener(e -> {
            String agentName = agentNameField.getText();
            int rating = getSelectedRating();
            String review = reviewArea.getText();
            if (review.isEmpty() || rating <= 0) {
                JOptionPane.showMessageDialog(this, "Review cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Implement saving logic here
                System.out.println("Agent: " + agentName + ", Rating: " + rating + ", Review: " + review);
                JOptionPane.showMessageDialog(this, "Review successfully saved", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
    }

    private int getSelectedRating() {
        for (int i = 0; i < ratingButtons.length; i++) {
            if (ratingButtons[i].isSelected()) {
                return i + 1;
            }
        }
        return 0; // No rating selected
    }

    // Method to update the agent name field with the selected agent's name
    public void setAgentName(String agentName) {
        agentNameField.setText(agentName);
    }
}
