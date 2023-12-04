package com.oap200.app.tabbedPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.views.ReportFinancialPanel;
import com.oap200.app.views.ReportPaymentsPanel;
import com.oap200.app.views.ReportSalesPanel;
import com.oap200.app.views.ReportStockPanel;

/**
 * The ReportManagementPanel class manages the presentation of different report panels in a tabbed layout.
 * It allows users to switch between various report panels such as Sales, Stock, Payments, and Financial reports.
 * This class also includes 'Back' and 'Logout' buttons for navigation and remembers the window's last position.
 *
 * @author Dirkje Jansje van der Poel
 */
public class TabbedReportManagementPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    /**
     * Constructs the ReportManagementPanel with a tabbed layout and navigation buttons.
     * Initializes the tabbed pane with different report panels and adds navigation buttons for 'Back' and 'Logout' actions.
     */
    public TabbedReportManagementPanel() {
        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(TabbedReportManagementPanel.class);
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

        // Add your report panels here
        tabbedPane.addTab("Sales Report", new ReportSalesPanel());
        tabbedPane.addTab("Stock Report", new ReportStockPanel());
        tabbedPane.addTab("Payments Report", new ReportPaymentsPanel());
        tabbedPane.addTab("Financial Report", new ReportFinancialPanel());

        // Set up the layout for the frame
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());

        // Create and add the 'Back' and 'Logout' buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> closeWindow());
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> System.exit(0));

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Add the button panel to the top panel
        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add the top panel to the frame
        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Closes the current window that contains this panel.
     */
    private void closeWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    /**
     * The main method that launches the ReportManagementPanel.
     * It creates and displays the main GUI frame containing the ReportManagementPanel.
     *
     * @param args Command-line arguments, not used in this application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TabbedReportManagementPanel frame = new TabbedReportManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.setVisible(true);
        });
    }
}
