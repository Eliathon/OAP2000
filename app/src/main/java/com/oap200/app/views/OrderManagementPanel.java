// Created by Patrik
package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import com.oap200.app.tabbedPanels.TabbedOrderPanel;
import com.oap200.app.utils.ButtonBuilder;

public class OrderManagementPanel extends JFrame {
    private JTextField orderNumberField; // Textfield for Order Number
    private JTextField orderDateField; // Textfield for Order Date
    private JTextField requiredDateField; // Textfield for Required Date
    private JTextField shippedDateField; // Textfield for Shipped Date
    private JTextField statusField; // Textfield for status
    private JTextArea  commentsDescriptionArea; // DescriptionArea for Comments
    private JTextField customerNumberField; // Textfield for Customer Number
    

   private static final String PREF_X = "window_x";
   private static final String PREF_Y = "window_y";

   public OrderManagementPanel() {
   // Load the last window position
      Preferences prefs = Preferences.userNodeForPackage(TabbedOrderPanel.class);
      int x = prefs.getInt(PREF_X, 50); // Default x position
      int y = prefs.getInt(PREF_Y, 50); // Default y position
      setLocation(x, y);
      addWindowListener(new WindowAdapter() {
      @Override
    public void windowClosing(WindowEvent e) {
       // Save the current position
       prefs.putInt(PREF_X, getLocation().x);
       prefs.putInt(PREF_Y, getLocation().y);
     
    }
 });

// Set up the layout for the frame
    setLayout(new BorderLayout());

    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    JPanel topPanel = new JPanel(new BorderLayout());

    JPanel labelPanel = new JPanel(new GridLayout(0, 1)); // 0 rows, 1 column
    labelPanel.setPreferredSize(new Dimension(150, 200));

    JPanel fieldPanel = new JPanel(new GridLayout(0, 1)); // 0 rows, 1 column
    fieldPanel.setPreferredSize(new Dimension(350, 200));

    //Defining labels for the text inputs and size
    orderNumberField = new JTextField();
    orderNumberField.setPreferredSize(new Dimension(200, 30));

    orderDateField = new JTextField();
    orderDateField.setPreferredSize(new Dimension(200, 30));
    

    requiredDateField = new JTextField();
    

    shippedDateField = new JTextField();
    

    commentsDescriptionArea = new JTextArea();
    commentsDescriptionArea.setLineWrap(true);
    JScrollPane descriptionScrollPane = new JScrollPane(commentsDescriptionArea);


    statusField = new JTextField();


    customerNumberField = new JTextField();




     // Create and add the 'Back' button
    JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
    // Define the action to be performed when the 'Back' button is clicked
    // Example: System.out.println("Back button clicked");
    });
    // Create and add the Logout button
    JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
    // Define the action to be performed when the 'Back' button is clicked
    // Example: System.out.println("Logout button clicked");      
    });
    
    JButton viewButton = ButtonBuilder.createViewButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      

    });
        // Create and add the View button
    JButton addButton = ButtonBuilder.createAddButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
    });
        // Create and add the View button
    JButton deleteButton = ButtonBuilder.createDeleteButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
     });
        // Create and add the View button
    JButton updateButton = ButtonBuilder.createUpdateButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
    });


     // Create the JTabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();

    // Create the first tab.
    JPanel panel1 = new JPanel(new BorderLayout());
    panel1.add(viewButton, BorderLayout.CENTER);
    // Add components to panel1...
    tabbedPane.addTab("View Order", null, panel1, "Click to view");

    // Create the second tab.
    JPanel panel2 = new JPanel(new BorderLayout());
    
    labelPanel.add(new JLabel("Order Number:"));
    fieldPanel.add(orderNumberField);

    labelPanel.add(new JLabel("Order Date:"));
    fieldPanel.add(orderDateField);

    labelPanel.add(new JLabel("Required Date:"));
    fieldPanel.add(requiredDateField);

    labelPanel.add(new JLabel("Shipped Date:"));
    fieldPanel.add(shippedDateField);

    labelPanel.add(new JLabel("Status:"));
    fieldPanel.add(statusField);
    
    labelPanel.add(new JLabel("Comments:"));
    fieldPanel.add(descriptionScrollPane);

    labelPanel.add(new JLabel("Customer Number:"));
    fieldPanel.add(customerNumberField);

    // Adding labels and textfields in panel2
    panel2.add(labelPanel, BorderLayout.WEST);
    panel2.add(fieldPanel, BorderLayout.CENTER);
    
    panel2.add(addButton, BorderLayout.SOUTH);  
    
    tabbedPane.addTab("Add Order", null, panel2, "Click to add ");

    // Create the third tab.
    JPanel panel3 = new JPanel(new BorderLayout());

    
    panel3.add(updateButton, BorderLayout.CENTER);
    // Add components to panel3...
    tabbedPane.addTab("Update Order", null, panel3, "Click to update ");

    // Create the fourth tab.
    JPanel panel4 = new JPanel(new BorderLayout());
    panel4.add(deleteButton, BorderLayout.CENTER);
    // Add components to panel4...
    tabbedPane.addTab("Delete Order", null, panel4, "Click to delete ");
     
    // Create a panel to hold the back button
    buttonPanel.setOpaque(false); // Set panel transparent
    buttonPanel.add(backButton);
    buttonPanel.add(logoutButton);
    // Add the button panel to the top panel
    topPanel.add(buttonPanel, BorderLayout.EAST);
    topPanel.add(tabbedPane, BorderLayout.CENTER);

    // Add the top panel to the frame
    getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OrderManagementPanel frame = new OrderManagementPanel();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 600); // Set the frame size
                frame.setVisible(true); // Display the frame
            }
        });
    }
}
