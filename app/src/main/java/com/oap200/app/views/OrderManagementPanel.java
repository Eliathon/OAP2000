// Created by Patrik
package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.oap200.app.controllers.OrdersController;

import java.awt.*;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.tabbedPanels.TabbedOrderPanel;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DbConnect;
import com.mysql.cj.jdbc.Driver;

public class OrderManagementPanel extends JFrame {
   
private static final String PREF_X = "window_x";
   private static final String PREF_Y = "window_y";

    private JTextField searchTextField;
    private JTextField orderNumber;
    private JTextField orderDate;
    private JTextField requiredDate; 
    private JTextField shippedDate; 
    private JTextField status;
    private JTextField comments;
    private JTextField customerNumber;  


   private JTable OrdersTable;

   public OrderManagementPanel() {
    initializeFields();
// Load the last window position
      Preferences prefs = Preferences.userNodeForPackage(TabbedOrderPanel.class);
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
        JButton searchButton = ButtonBuilder.createSearchButton(() -> {
        /* Action for Search Button */});

        JPanel viewSearchButtonPanel = new JPanel(new FlowLayout());
        viewSearchButtonPanel.add(viewButton);
        viewSearchButtonPanel.add(searchButton);
       
                // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Orders
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);

        panel1.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("View Orders", null, panel1, "Click to view");

        // Tab 2: Add Order
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel2);
        addButton.addActionListener(e -> addOrders());
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Orders", null, panel2, "Click to add");

        // Tab 3: Update Order
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Orders", null, panel3, "Click to Update");

        // Tab 4: Delete Order
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel4);
        panel4.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Orders", null, panel4, "Click to Delete");

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
        OrdersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(OrdersTable);
        // Correct this line to add the scrollPane to the CENTER instead of EAST
        panel1.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> viewOrders());
        searchButton.addActionListener(e -> searchOrders());
    }
     private void initializeFields() {

        searchTextField = new JTextField(10);
        this.orderNumber = new JTextField(10);
     }   

    private void viewOrders() {
        OrderDAO OrderDAO = new OrderDAO();
        List<String[]> ordersList = OrderDAO.fetchOrders();
        String[] columnNames = { "Order Number", "Order Date", "Required Date", "Shipped Date", "Status", "Comments",
                "Customer Number" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : ordersList) {
            model.addRow(row);
        }
        OrdersTable.setModel(model);
    }

    private void addOrders() {
        // Create an instance of OrderDAO
        OrderDAO orderDAO = new OrderDAO();
    
        // Call the addOrders method from OrderDAO
            String orderNumber = orderNumberTextField.getText();
            String orderDate = orderDateTextField.getText();
            String requiredDate = requiredDateTextField.getText();
            String shippedDate = shippedDateTextField.getText();
            String status = statusTextField.getText();
            String comments = commentsTextField.getText();
            String customerNumber = customerNumberTextField.getText();

             // Call the addOrders method from OrderDAO
       boolean isAdded = orderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
        
       if (isAdded) {
             // Update the table in the view if addition is successful
           viewOrders();
        } else {
            System.out.println("Error adding order!");
        }
    }
    

    
    private void ordersResult() {
        String orderNumber = searchTextField.getText();
        OrdersController.handleOrdersResult(orderNumber);
    }

    private void searchOrders() {
        String orderNumber = searchTextField.getText();
        OrdersController.handleSearchOrders(orderNumber);
    }

    private void addComponentsToPanel(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(7, 1)); // 7 labels
        JPanel fieldPanel = new JPanel(new GridLayout(7, 1)); // 7 fields

        // Cloning fields for each tab
        JTextField orderNumber = new JTextField(10);
        JTextField orderDate = new JTextField(10);
        JTextField requiredDate = new JTextField(10);
        JTextField shippedDate = new JTextField(10);
        JTextField status = new JTextField(10);
        JTextField comments = new JTextField(30);
        JTextField customerNumber = new JTextField(10);
        
        labelPanel.add(new JLabel("Order Number:"));
        fieldPanel.add(orderNumber);
        labelPanel.add(new JLabel("Order Date:"));
        fieldPanel.add(orderDate);
        labelPanel.add(new JLabel("Required Date:"));
        fieldPanel.add(requiredDate);
        labelPanel.add(new JLabel("Shipped Date:"));
        fieldPanel.add(shippedDate);
        labelPanel.add(new JLabel("Status:"));
        fieldPanel.add(status);
        labelPanel.add(new JLabel("Comments:"));
        fieldPanel.add(comments);
        labelPanel.add(new JLabel("Customer Number:"));
        fieldPanel.add(customerNumber);

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Label weight
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adding the "Order Number:" label
        inputPanel.add(new JLabel("Order Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderNumber = new JTextField(10);
        inputPanel.add(orderNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Last Name:" label
        inputPanel.add(new JLabel("Order Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderDate = new JTextField(10);
        inputPanel.add(orderDate, gbc);

        panelView.add(inputPanel, BorderLayout.NORTH);

        // Now, create the scrollPane with the orderTable right here:
        JScrollPane scrollPane = new JScrollPane(OrdersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
    }

    private void addComponentsToPanelAdd(JPanel panelAdd) {
        panelAdd.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Label weight
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adding the "Order Number:" label
        inputPanel.add(new JLabel(" Add Order Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderNumber = new JTextField(10);
        inputPanel.add(orderNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Order Date:" label
        inputPanel.add(new JLabel("Add Order Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderDate = new JTextField(10);
        inputPanel.add(orderDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Required Date:" label
        inputPanel.add(new JLabel("Add Required Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField requiredDate = new JTextField(10);
        inputPanel.add(requiredDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Required Date:" label
        inputPanel.add(new JLabel("Add Shipped Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField shippedDate = new JTextField(10);
        inputPanel.add(shippedDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Required Date:" label
        inputPanel.add(new JLabel("Add Status:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField status = new JTextField(10);
        inputPanel.add(status, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Required Date:" label
        inputPanel.add(new JLabel("Add Comments:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField comments = new JTextField(30);
        inputPanel.add(comments, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Required Date:" label
        inputPanel.add(new JLabel("Add Customer Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField customerNumber = new JTextField(10);
        inputPanel.add(customerNumber, gbc);

        panelAdd.add(inputPanel, BorderLayout.NORTH);

        // Now, create the scrollPane with the orderTable right here:
        JScrollPane scrollPane = new JScrollPane(OrdersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelAdd.add(tableContainer, BorderLayout.CENTER);
    }
    
    private void addComponentsToPanelDelete(JPanel panelDelete) {
        panelDelete.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Label weight
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        // Adding the "Order Number:" label
        inputPanel.add(new JLabel("Order Number:"), gbc);
    
        panelDelete.add(inputPanel, BorderLayout.NORTH);
    
        // Now, create the scrollPane with the orderTable right here:
        JScrollPane scrollPane = new JScrollPane(OrdersTable);
    
        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelDelete.add(tableContainer, BorderLayout.CENTER);
    }
    



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderManagementPanel frame = new OrderManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("OrderManagementPanel");
            frame.pack();
            frame.setVisible(true);
        });
    }
}
