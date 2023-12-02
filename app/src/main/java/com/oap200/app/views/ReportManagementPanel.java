package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder;

/**
 * The ReportManagementPanel class manages the presentation of different report panels in a tabbed layout.
 * It allows users to switch between various report panels such as Sales, Stock, Payments, and Financial reports.
 * This class also includes 'Back' and 'Logout' buttons for navigation.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportManagementPanel extends JPanel {

    /**
     * Launches the ReportManagementPanel in a separate event dispatch thread.
     * This is the entry point of the application, responsible for initializing and displaying the user interface.
     *
     * @param args Command-line arguments. Currently, these are not used in the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    /**
     * Initializes and displays the main GUI frame containing the ReportManagementPanel.
     * This method sets up the JFrame and adds the ReportManagementPanel to it, centering the window on the screen.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Company Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ReportManagementPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Constructs the ReportManagementPanel with a tabbed layout and navigation buttons.
     * Initializes the tabbed pane with different report panels and adds navigation buttons for 'Back' and 'Logout' actions.
     */
    public ReportManagementPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        initializeTabs(tabbedPane);

        JPanel buttonPanel = createButtonPanel();
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(tabbedPane, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Initializes and adds tabs to the given JTabbedPane.
     * Each tab corresponds to a different report panel, allowing for organized display of reports.
     *
     * @param tabbedPane The JTabbedPane to which report tabs are added.
     */
    private void initializeTabs(JTabbedPane tabbedPane) {
        tabbedPane.addTab("Sales Report", new ReportSalesPanel());
        tabbedPane.addTab("Stock Report", new ReportStockPanel());
        tabbedPane.addTab("Payments Report", new ReportPaymentsPanel());
        tabbedPane.addTab("Financial Report", new ReportFinancialPanel());
    }

    /**
     * Creates and returns a JPanel with navigation buttons 'Back' and 'Logout'.
     * The 'Back' button is configured to close the current window, and the 'Logout' button exits the application.
     *
     * @return JPanel containing the 'Back' and 'Logout' navigation buttons.
     */
    private JPanel createButtonPanel() {
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> closeWindow());
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> System.exit(0));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        return buttonPanel;
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
}

