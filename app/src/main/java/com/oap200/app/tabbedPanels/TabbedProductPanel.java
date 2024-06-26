/**
 * TabbedProductPanel is a JFrame with a JTabbedPane containing multiple tabs for product-related actions.
 * Each tab allows viewing, adding, updating, and deleting products.
 *
 * @author Sebastian
 */
package com.oap200.app.tabbedPanels;

import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

/**
 * The TabbedProductPanel class represents a JFrame with a tabbed layout for managing products.
 */
public class TabbedProductPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    /**
     * Constructs a new TabbedProductPanel with tabs for viewing, adding, updating, and deleting products.
     */
    public TabbedProductPanel() {
        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(TabbedCustomerPanel.class);
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

        // Create the first tab.
        JPanel panel1 = new JPanel();
        // Add components to panel1...
        tabbedPane.addTab("View Product", null, panel1, "Click to view");

        // Create the second tab.
        JPanel panel2 = new JPanel();
        // Add components to panel2...
        tabbedPane.addTab("Add Product", null, panel2, "Click to add ");

        // Create the third tab.
        JPanel panel3 = new JPanel();
        // Add components to panel3...
        tabbedPane.addTab("Update Product", null, panel3, "Click to update ");

        // Create the fourth tab.
        JPanel panel4 = new JPanel();
        // Add components to panel4...
        tabbedPane.addTab("Delete Product", null, panel4, "Click to delete ");

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

    /**
     * The main method to launch the TabbedProductPanel application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TabbedProductPanel frame = new TabbedProductPanel();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 600); // Set the frame size
                frame.setVisible(true); // Display the frame
            }
        });
    }
}
