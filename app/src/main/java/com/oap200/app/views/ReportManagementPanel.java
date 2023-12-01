package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;

/**
 * ReportManagementPanel manages the presentation of different report panels in a tabbed layout.
 * It allows switching between various report panels such as Sales, Stock, Payments, and Financial reports.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportManagementPanel extends JPanel {

    /**
     * The main method to run the ReportManagementPanel.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    /**
     * Creates and displays the main GUI frame.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Company Management System");
        ReportManagementPanel reportManagementPanel = new ReportManagementPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(reportManagementPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    /**
     * Constructs a new ReportManagementPanel with a tabbed layout.
     */
    public ReportManagementPanel() {
        // Create the JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Initialize panels
        ReportSalesPanel reportSalesPanel = new ReportSalesPanel();
        ReportStockPanel reportStockPanel = new ReportStockPanel();
        ReportPaymentsPanel reportPaymentsPanel = new ReportPaymentsPanel();
        ReportFinancialPanel reportFinancialPanel = new ReportFinancialPanel();

        // Add panels to tab layout
        tabbedPane.addTab("Sales Report", reportSalesPanel);
        tabbedPane.addTab("Stock Report", reportStockPanel);
        tabbedPane.addTab("Payments Report", reportPaymentsPanel);
        tabbedPane.addTab("Financial Report", reportFinancialPanel);

        // Set up the layout for the panel
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }
}
