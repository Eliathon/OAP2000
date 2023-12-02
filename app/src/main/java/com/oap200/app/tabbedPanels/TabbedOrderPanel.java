/**
 * The TabbedOrderPanel class represents a JFrame with a JTabbedPane for managing orders.
 * This class includes tabs for viewing, adding, updating, and deleting orders.
 * The window position is saved and loaded using Java Preferences.
 *
 * @author Patrik
 */
package com.oap200.app.tabbedPanels;

import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

/**
 * The TabbedOrderPanel class extends JFrame and represents the main window of the Order Management application.
 * It includes a JTabbedPane with tabs for different order management functionalities.
 */
public class TabbedOrderPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    /**
     * Constructs a TabbedOrderPanel and initializes its components.
     * The window position is loaded from preferences, and a WindowListener is added to save the position on closing.
     */
    public TabbedOrderPanel() {
        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(TabbedCustomerPanel.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        // Add a WindowListener to save the current window position on closing
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

        // Create the first tab for viewing orders
        JPanel panel1 = new JPanel();
        // Add components to panel1...

        // Add the first tab to the tabbedPane
        tabbedPane.addTab("View Order", null, panel1, "Click to view");

        // Create the second tab for adding orders
        JPanel panel2 = new JPanel();
        // Add components to panel2...

        // Add the second tab to the tabbedPane
        tabbedPane.addTab("Add Order", null, panel2, "Click to add");

        // Create the third tab for updating orders
        JPanel panel3 = new JPanel();
        // Add components to panel3...

        // Add the third tab to the tabbedPane
        tabbedPane.addTab("Update Order", null, panel3, "Click to update");

        // Create the fourth tab for deleting orders
        JPanel panel4 = new JPanel();
        // Add components to panel4...

        // Add the fourth tab to the tabbedPane
        tabbedPane.addTab("Delete Order", null, panel4, "Click to delete");

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
            // Define the action to be performed when the 'Logout' button is clicked
            // Example: System.out.println("Logout button clicked");
        });

        // Create a panel to hold the back and logout buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.setOpaque(false); // Set panel transparent
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Add the button panel and the tabbedPane to the top panel
        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add the top panel to the frame
        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    /**
     * The main method to start the TabbedOrderPanel application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TabbedOrderPanel frame = new TabbedOrderPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600); // Set the frame size
            frame.setVisible(true); // Display the frame
        });
    }
}
