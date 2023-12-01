//Created by Dirkje J van der Poel
package com.oap200.app.views;

import java.awt.BorderLayout;

import javax.swing.*;

public class ReportManagementPanel extends JPanel {

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    // GUI creation method
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Company Management System");
        ReportManagementPanel reportManagementPanel = new ReportManagementPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(reportManagementPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

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
