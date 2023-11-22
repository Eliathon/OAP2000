// Created by Johnny
package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder;

public class TabbedProductPanel extends JPanel {

    public TabbedProductPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the first tab for viewing products
        JPanel viewProductsPanel = new JPanel();
        // Add components to viewProductsPanel...
        tabbedPane.addTab("View Product", null, viewProductsPanel, "Click to view");

        // Create the second tab for adding products
        JPanel addProductsPanel = new JPanel();
        // Add components to addProductsPanel...
        tabbedPane.addTab("Add Product", null, addProductsPanel, "Click to add");

        // Create the third tab for updating products
        JPanel updateProductsPanel = new JPanel();
        // Add components to updateProductsPanel...
        tabbedPane.addTab("Update Product", null, updateProductsPanel, "Click to update");

        // Create the fourth tab for deleting products
        JPanel deleteProductsPanel = new JPanel();
        // Add components to deleteProductsPanel...
        tabbedPane.addTab("Delete Product", null, deleteProductsPanel, "Click to delete");

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