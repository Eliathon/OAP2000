package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder;

public class TabbedPaymentPanel extends JPanel {

    public TabbedPaymentPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab for Viewing Payments
        JPanel viewPaymentsPanel = new JPanel();
        // Add components to viewPaymentsPanel...
        tabbedPane.addTab("View Payments", null, viewPaymentsPanel, "Click to view");

        // Additional tabs can be added here...

        // Add the tabbedPane to this panel
        add(tabbedPane, BorderLayout.CENTER);

        // Top panel with 'Back' and 'Logout' buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            System.out.println("Back button clicked");
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            System.out.println("Logout button clicked");
        });

        topPanel.add(backButton);
        topPanel.add(logoutButton);

        // Add the top panel to this panel
        add(topPanel, BorderLayout.NORTH);
    }
}