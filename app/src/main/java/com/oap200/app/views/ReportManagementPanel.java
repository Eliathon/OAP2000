// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import javax.swing.*;
import com.oap200.app.utils.ButtonBuilder;
import java.awt.*;

public class ReportManagementPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Panels
    private ReportSalesPanel reportSalesPanel;
    private ReportStockPanel reportStockPanel;
    private ReportPaymentsPanel reportPaymentsPanel; // Nieuwe toevoeging

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
        reportPaymentsPanel = new ReportPaymentsPanel(); // Nieuwe toevoeging

        // Add panels to card layout
        cardPanel.add(reportSalesPanel, "SalesReport");
        cardPanel.add(reportStockPanel, "StockReport");
        cardPanel.add(reportPaymentsPanel, "PaymentsReport"); // Nieuwe toevoeging

        setLayout(new BorderLayout());
        add(createSidebar(), BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Ruimte toevoegen aan de bovenkant van de sidebar
        sidebar.add(Box.createVerticalStrut(20));

        // Sales Report knop
        sidebar.add(ButtonBuilder.createStyledButton("Sales Report", () -> cardLayout.show(cardPanel, "SalesReport")));

    
        // Stock Report knop
        sidebar.add(ButtonBuilder.createStyledButton("Stock Report", () -> cardLayout.show(cardPanel, "StockReport")));

        // Payments Report knop - Nieuwe toevoeging
        sidebar.add(ButtonBuilder.createStyledButton("Payments Report", () -> cardLayout.show(cardPanel, "PaymentsReport")));

        // Meer knoppen kunnen hier worden toegevoegd indien nodig...

        return sidebar;
    }
}
