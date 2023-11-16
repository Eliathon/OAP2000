// Created by Johnny


package com.oap200.app.tabbedPanels;

import javax.swing.*;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder; // Import the ButtonBuilder class

public class TabbedCustomerPanel extends JFrame {
public TabbedCustomerPanel() {
// Create the JTabbedPane
JTabbedPane tabbedPane = new JTabbedPane();

// Create the first tab.
JPanel panel1 = new JPanel();
// Add components to panel1...
tabbedPane.addTab("View Customers", null, panel1, "Click to view");

// Create the second tab.
JPanel panel2 = new JPanel();
// Add components to panel2...
tabbedPane.addTab("Add Customer", null, panel2, "Click to add ");

// Create the third tab.
JPanel panel3 = new JPanel();
// Add components to panel3...
tabbedPane.addTab("Update Customer", null, panel3, "Click to update ");

// Create the fourth tab.
JPanel panel4 = new JPanel();
// Add components to panel4...
tabbedPane.addTab("Delete Customer", null, panel4, "Click to delete ");

// Set up the layout for the frame
setLayout(new BorderLayout());
JPanel topPanel = new JPanel(new BorderLayout());

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
        
        
// Create a panel to hold the back button
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
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
                TabbedCustomerPanel frame = new TabbedCustomerPanel();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 600); // Set the frame size
                frame.setVisible(true); // Display the frame
            }
        });
    }
}