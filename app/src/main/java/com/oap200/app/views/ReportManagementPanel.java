// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import javax.swing.*;
import com.oap200.app.utils.ButtonBuilder; // Importeer de ButtonBuilder klasse
import java.awt.*;

public class ReportManagementPanel extends JPanel {
    private CardLayout cardLayout;
    /**
     * The panel that contains the different report panels.
     * Uses a CardLayout to switch between panels.
     */
    private JPanel cardPanel;

    // Panels
    private ReportSalesPanel reportSalesPanel;
    private ReportStockPanel reportStockPanel;
    private ReportPaymentsPanel reportPaymentsPanel;
    private ReportFinancialPanel reportFinancialPanel;

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    // GUI creation method
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Company Management System");
        ReportManagementPanel reportManagementPanel = new ReportManagementPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(reportManagementPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    public ReportManagementPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize panels
        reportSalesPanel = new ReportSalesPanel();
        reportStockPanel = new ReportStockPanel();
        reportPaymentsPanel = new ReportPaymentsPanel(); 
        reportFinancialPanel = new ReportFinancialPanel();

        // Add panels to card layout
        cardPanel.add(reportSalesPanel, "SalesReport");
        cardPanel.add(reportStockPanel, "StockReport");
        cardPanel.add(reportPaymentsPanel, "PaymentsReport"); // Nieuwe toevoeging
        cardPanel.add(reportFinancialPanel, "FinancialReport");

        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(ButtonBuilder.createStyledButton("Sales Report", () -> cardLayout.show(cardPanel, "SalesReport")));
        sidebar.add(ButtonBuilder.createStyledButton("Stock Report", () -> cardLayout.show(cardPanel, "StockReport")));
        sidebar.add(ButtonBuilder.createStyledButton("Payments Report", () -> cardLayout.show(cardPanel, "PaymentsReport")));
        sidebar.add(ButtonBuilder.createStyledButton("Financial Report", () -> cardLayout.show(cardPanel, "FinancialReport")));
       
        return sidebar;
    }
}