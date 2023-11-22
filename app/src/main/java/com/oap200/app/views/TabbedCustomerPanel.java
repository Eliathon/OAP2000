// Created by Johnny

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder;

public class TabbedCustomerPanel extends JPanel {

    public TabbedCustomerPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab for Viewing Customers
        JPanel viewCustomersPanel = new JPanel();
        // Add components to viewCustomersPanel...
        tabbedPane.addTab("View Customers", null, viewCustomersPanel, "Click to view");

        // Tab for Adding Customers
        JPanel addCustomersPanel = new JPanel();
        // Add components to addCustomersPanel...
        tabbedPane.addTab("Add Customer", null, addCustomersPanel, "Click to add");

        // Tab for Updating Customers
        JPanel updateCustomersPanel = new JPanel();
        // Add components to updateCustomersPanel...
        tabbedPane.addTab("Update Customer", null, updateCustomersPanel, "Click to update");

        // Tab for Deleting Customers
        JPanel deleteCustomersPanel = new JPanel();
        // Add components to deleteCustomersPanel...
        tabbedPane.addTab("Delete Customer", null, deleteCustomersPanel, "Click to delete");

        // Add the tabbedPane to this panel
        add(tabbedPane, BorderLayout.CENTER);

        // Top panel with buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            // Define the action for the 'Back' button
            // Example: System.out.println("Back button clicked");
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            // Define the action for the 'Logout' button
            // Example: System.out.println("Logout button clicked");
        });

        topPanel.add(backButton);
        topPanel.add(logoutButton);

        // Add the top panel to this panel
        add(topPanel, BorderLayout.NORTH);
    }
}
