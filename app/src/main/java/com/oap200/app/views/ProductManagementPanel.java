package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder; // Import the ButtonBuilder class

public class ProductManagementPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    public ProductManagementPanel() {
        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(TabbedPaymentPanel.class);
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

        // Create the JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the first tab for viewing payments.
        JPanel panel1 = new JPanel();
        // Add components to panel1 related to viewing payments...
        tabbedPane.addTab("View Payments", null, panel1, "Click to view");

         // Create the first tab for viewing payments.
        JPanel panel2 = new JPanel();
        // Add components to panel1 related to viewing payments...
        tabbedPane.addTab("Add Payments", null, panel2, "Click to view");
         
        // Create the first tab for viewing payments.
        JPanel panel3 = new JPanel();
        // Add components to panel1 related to viewing payments...
        tabbedPane.addTab("Delete Payments", null, panel3, "Click to view");

 
        JPanel panel4 = new JPanel();
        
        tabbedPane.addTab("Update Payments", null, panel4, "Click to view");

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
        // Create and add the View button
        JButton viewButton = ButtonBuilder.createActionButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
        });
        // Create and add the View button
        JButton addButton = ButtonBuilder.createActionButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
        });
        // Create and add the View button
        JButton deleteButton = ButtonBuilder.createActionButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
        });
        // Create and add the View button
        JButton updateButton = ButtonBuilder.createActionButton(() -> {
        // Define the action to be performed when the 'Back' button is clicked
        // Example: System.out.println("Logout button clicked");      
        });
            

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManagementPanel frame = new ProductManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600); // Set the frame size
            frame.setVisible(true); // Display the frame
        });
    }
}