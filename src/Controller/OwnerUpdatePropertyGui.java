package Boundary;

import Controller.PropertyController;
import Controller.WorkSlotController;
import Entity.Property;
import Entity.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class OwnerUpdatePropertyGui {
    private Calendar current;
    private JButton backButton;
    private JButton createButton;
    private JTextField idTield;
    private JTextField titleTield;
    private JTextField priceField;
    private JTextField describField;
    private JTextField sellerField;
    private JTextField locationField;
    public OwnerUpdatePropertyGui(Property u){
        displayCreateWorkSlotGUI(u);
    }

    public void displayCreateWorkSlotGUI(Property u){
        JFrame frame = new JFrame("Create Property");
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("Create Property");
        titleLabel.setBounds(250, 30,350, 50);
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN,36));
        panel.add(titleLabel);

        // Date Label



        JLabel id = new JLabel("id");
        id.setBounds(225, 425, 235, 50);
        panel.add(id);
        idTield= new JTextField(20);
        idTield.setBounds(325, 425, 235, 50);
        idTield.setFont(new Font("Helvetica", Font.PLAIN,36));
        idTield.setText(u.getId()+"");
        panel.add(idTield);





        // propertytile
        JLabel propertytitle = new JLabel("propertytitle");
        propertytitle.setBounds(225, 175, 235, 50);
        panel.add(propertytitle);
        titleTield = new JTextField(20);
        titleTield.setBounds(325, 175, 235, 50);
        titleTield.setFont(new Font("Helvetica", Font.PLAIN,36));
        titleTield.setText(u.getPropertytitle());
        panel.add(titleTield);

        //seller
        JLabel seller= new JLabel("seller");
        seller.setBounds(225, 225, 235, 50);
        panel.add(seller);
        sellerField = new JTextField(20);
        sellerField.setBounds(325, 225, 235, 50);
        sellerField.setFont(new Font("Helvetica", Font.PLAIN,36));
        sellerField.setText(u.getSeller());
        panel.add(sellerField);


        JLabel describe= new JLabel("describe");
        describe.setBounds(225, 275, 235, 50);
        panel.add(describe);
        describField = new JTextField(20);
        describField.setBounds(325, 275, 400, 50);
        describField.setFont(new Font("Helvetica", Font.PLAIN,36));
        describField.setText(u.getDescribe());
        panel.add(describField);


        //price
        JLabel price= new JLabel("price");
        price.setBounds(225, 325, 235, 50);
        panel.add(price);
        priceField = new JTextField(20);
        priceField.setBounds(325, 325, 235, 50);
        priceField.setFont(new Font("Helvetica", Font.PLAIN,36));
        priceField.setText(u.getPrice()+"");
        panel.add(priceField);

        //locatiom
        JLabel location = new JLabel("location");
        location.setBounds(225, 375, 235, 50);
        panel.add(location);
        locationField = new JTextField(20);
        locationField.setBounds(325, 375, 235, 50);
        locationField.setFont(new Font("Helvetica", Font.PLAIN,36));
        locationField.setText(u.getLocation());
        panel.add(locationField);








       /* // Chef Field
        chefField = new JTextField(20);
        chefField.setBounds(325, 175, 235, 50);
        panel.add(chefField);

        // Cashier Label
        JLabel cashierLabel = new JLabel("Cashier");
        cashierLabel.setBounds(225, 225, 235, 50);
        panel.add(cashierLabel);

        // Cashier Field
        cashierField = new JTextField(20);
        cashierField.setBounds(325, 225, 235, 50);
        panel.add(cashierField);

        // Waiter Label
        JLabel waiterLabel = new JLabel("Waiter");
        waiterLabel.setBounds(225, 275, 235, 50);
        panel.add(waiterLabel);

        // Staff Field
        waiterField = new JTextField(20);
        waiterField.setBounds(325, 275, 235, 50);
        panel.add(waiterField);*/


        // Create Button
        createButton = new JButton("update");
        createButton.setBounds(500, 500, 235, 30);
        panel.add(createButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(100, 500, 235, 30);
        panel.add(backButton);

        // Action for create button
        createButton.addActionListener(e -> {
            // If date field is not selected, prompt user to select
            /*if(dateChooser.getDate() != null) {
                java.util.Date selectedDateUtil = dateChooser.getDate();
                java.sql.Date selectedDate = new java.sql.Date(selectedDateUtil.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(selectedDate);

                // Error message if there is already an existing workslot
                if (workSlotAlreadyExists(selectedDate)) {
                    JOptionPane.showMessageDialog(frame, "Work slot already exists for the selected date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If any of the fields is empty, prompt user to enter valid amount
                if(!chefField.getText().isEmpty() && !cashierField.getText().isEmpty() && !waiterField.getText().isEmpty()) {
                    try{

                        int numOfChef = Integer.parseInt(chefField.getText());
                        int numOfCashier = Integer.parseInt(cashierField.getText());
                        int numOfWaiter = Integer.parseInt(waiterField.getText());

                        if(numOfCashier < 1 || numOfChef < 1 || numOfWaiter < 1) {
                            JOptionPane.showMessageDialog(frame, "Chef, Cashier and Waiter must be more than 0");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Successfully created!");
                            new CreateWorkSlotController().createWorkSlot(date, numOfChef, numOfCashier, numOfWaiter);
                            frame.dispose();
                            new RealEstateAgent(u);
                        }

                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(frame, "Please enter valid value (numbers)");

                    }*/



               /* } else {
                    JOptionPane.showMessageDialog(frame, "Fields must be filled");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a date");
            }*/
            String title ="";
            String sellerName="";
            String description="";
            String priceText="";
            String loc="";
            String id1="";
            //if(null!=titleTield.getText()&&"".equals(titleTield.getText())){
              title=titleTield.getText();
              //JOptionPane.showMessageDialog(frame, "title must be filled");
           // }
                sellerName=sellerField.getText();
                description=describField.getText();
                priceText=priceField.getText();
                loc=locationField.getText();
                id1=idTield.getText();
                Property p=new Property(title,sellerName,description,Double.parseDouble(priceText),loc,"1");
                p.setId(Integer.parseInt(id1));
            new PropertyController().updateProperty(p);

        });

        // Action for back button
        backButton.addActionListener(e -> {
            frame.dispose();
            //new RealEstateAgent(u);
        });

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean workSlotAlreadyExists(Date selectedDate){
        WorkSlotController workSlotController = new WorkSlotController();
        List<Property> workSlots = workSlotController.getWorkSlotsByDate(selectedDate);
        return !workSlots.isEmpty();
    }
}