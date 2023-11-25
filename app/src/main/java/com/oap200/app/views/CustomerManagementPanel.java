// Created by Johnny
package com.oap200.app.views;
import com.oap200.app.models.CustomerDAO;
import com.oap200.app.controllers.CustomerController;
import com.oap200.app.utils.ButtonBuilder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;


import java.util.Arrays;
import java.util.List;

public class CustomerManagementPanel extends JFrame {

   private static final String PREF_X = "window_x"; 
   private static final String PREF_Y = "window_y"; 
	
   private JTable customerTable; 

   public CustomerManagementPanel() {
        initializeFields();

        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(PaymentManagement.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

         // Set up the layout for the frame
        setLayout(new BorderLayout());

        // Initialize ButtonBuilder buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            /* Action for Back Button */});
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            /* Action for Logout Button */});
        JButton viewButton = ButtonBuilder.createViewButton(() -> {
            /* Action for View Button */});
        JButton addButton = ButtonBuilder.createAddButton(() -> {
            /* Action for Add Button */});
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> {
            /* Action for Delete Button */});
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> {
            /* Action for Update Button */});
            // Initialize JTabbedPane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Tab 1: View Customers
            JPanel panel1 = new JPanel(new BorderLayout());
            addComponentsToPanelView(panel1);
            panel1.add(viewButton, BorderLayout.SOUTH);
            tabbedPane.addTab("View Payments", null, panel1, "Click to view");
    
            // Tab 2: Add Customers
            JPanel panel2 = new JPanel(new BorderLayout());
            addComponentsToPanel(panel2);
            panel2.add(addButton, BorderLayout.SOUTH);
            tabbedPane.addTab("Add Payments", null, panel2, "Click to add");
    
            // Tab 3: Update Customers
            JPanel panel3 = new JPanel(new BorderLayout());
            addComponentsToPanel(panel3);
            panel3.add(updateButton, BorderLayout.SOUTH);
            tabbedPane.addTab("Update Payments", null, panel3, "Click to Update");
    
            // Tab 4: Delete Customers
            JPanel panel4 = new JPanel(new BorderLayout());
            addComponentsToPanel(panel4);
            panel4.add(deleteButton, BorderLayout.SOUTH);
            tabbedPane.addTab("Delete Payments", null, panel4, "Click to Delete"); 
            
            // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Main panel for the frame
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Initialize the table
        customerTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(customerTable);
       
        panel1.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> viewCustomers());
    }
      private void viewCustomers() {
        CustomerDAO CustomerDAO = new CustomerDAO();
        List<String[]> customersList = DAO.fetchCustomers();
        String[] columnNames = { "Customer Number", "Customer Name", "Contact Last Name", "Contact First Name" , "Phone Number" , 
		"Adress Line 1" , " Aress Line 2" , "City" , "State" , " Postal Code" , "Country"} , " Sales Rep Employee Number" , "Credit Limit";

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : customersList) {
            model.addRow(row);
        }
        customersTable.setModel(model);
    }