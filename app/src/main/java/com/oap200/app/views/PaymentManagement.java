package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder; // Import the ButtonBuilder class

public class PaymentManagement extends JFrame {

    private JTextField customerNumberField;
    private JTextField checkNumberField; // Legg til et felt for produktkoden
    private JTextField paymentDateField; // Legg til et felt for produktskalaen
    private JTextField amountField; // Legg til et felt for produktselgeren


    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    public PaymentManagement() {
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

        // Set up the layout for the frame
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel labelPanel = new JPanel(new GridLayout(0, 1)); // 0 rader, 1 kolonne
        labelPanel.setPreferredSize(new Dimension(150, 200));

        JPanel fieldPanel = new JPanel(new GridLayout(0, 1)); // 0 rader, 1 kolonne
        fieldPanel.setPreferredSize(new Dimension(350, 200));

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

            // Create the first tab for viewing products.
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(viewButton, BorderLayout.CENTER);  // Add the viewButton to panel1
        tabbedPane.addTab("View Payments", null, panel1, "Click to view");

        // Create the second tab for adding products.
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(addButton, BorderLayout.CENTER);  // Add the addButton to panel2
        tabbedPane.addTab("Add Payments", null, panel2, "Click to add");
         
        // Create the first tab for viewing payments.
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.add(updateButton, BorderLayout.CENTER);  // Add the addButton to panel2
        tabbedPane.addTab("Update Payments", null, panel3, "Click to Update");

        JPanel panel4 = new JPanel(new BorderLayout());
        panel4.add(deleteButton, BorderLayout.CENTER);  // Add the addButton to panel2
        tabbedPane.addTab("Delete Payments", null, panel4, "Click to Delete");
              
        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaymentManagement frame = new PaymentManagement();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600); // Set the frame size
            frame.setVisible(true); // Display the frame
        });
    }
}