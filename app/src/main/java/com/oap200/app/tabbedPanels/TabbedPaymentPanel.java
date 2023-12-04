package com.oap200.app.tabbedPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder; // Importing the ButtonBuilder class

/**
*@author Jesper Solberg
*
*/

/**
 * The TabbedPaymentPanel class represents a Swing JFrame for a tabbed payment panel,
 * providing functionalities for viewing payments and managing window positions.
 */
public class TabbedPaymentPanel extends JFrame {

    // Preferences keys for window position
    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    /**
     * Constructs a TabbedPaymentPanel.
     * Initializes the window position based on preferences and sets up window listeners.
     */
    public TabbedPaymentPanel() {
        // Loading the last window position from preferences
        Preferences prefs = Preferences.userNodeForPackage(TabbedPaymentPanel.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        // Adding a window listener to save the current position on window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Saving the current window position to preferences
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

        // Creating a tabbed pane for organizing content
        JTabbedPane tabbedPane = new JTabbedPane();

        // Creating the first tab for viewing payments
        JPanel panel1 = new JPanel();
        // Adding components related to viewing payments to panel1
        tabbedPane.addTab("View Payments", null, panel1, "Click to view");

        // Setting up the layout for the frame using BorderLayout
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());

        // Creating and adding 'Back' and 'Logout' buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            // Action when the 'Back' button is clicked
            System.out.println("Back button clicked");
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            // Action when the 'Logout' button is clicked
            System.out.println("Logout button clicked");
        });

        // Creating a button panel for the 'Back' and 'Logout' buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Adding the button panel and tabbed pane to the top panel
        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        // Adding the top panel to the content pane
        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    /**
     * The main method to run the Swing application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Creating an instance of TabbedPaymentPanel
            TabbedPaymentPanel frame = new TabbedPaymentPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600); // Setting the frame size
            frame.setVisible(true); // Displaying the frame, toggleable value.
        });
    }
}
