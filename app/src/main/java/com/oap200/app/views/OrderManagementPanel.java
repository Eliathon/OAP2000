// Created by Patrik
package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.oap200.app.controllers.OrdersController;

import java.awt.*;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.tabbedPanels.TabbedOrderPanel;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DbConnect;
import com.mysql.cj.jdbc.Driver;

public class OrderManagementPanel extends JFrame {
   
private static final String PREF_X = "window_x";
   private static final String PREF_Y = "window_y";

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

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Orders
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Orders", null, panel1, "Click to view");

        // Tab 2: Add Order
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        addButton.addActionListener(e -> addOrders());
        tabbedPane.addTab("Add Orders", null, panel2, "Click to add");

        // Tab 3: Update Order
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel3);
        updateButton.addActionListener(e -> updateOrders());
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


    private void initializeFields() {
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
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
        JTextField comments = new JTextField(10);
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

    private void addOrders() {
        OrdersController ordersController = new OrdersController();
    
        String orderNumber = orderNumberField.getText();
        String orderDate = orderDateField.getText();
        String requiredDate = requiredDateField.getText();
        String shippedDate = shippedDateField.getText();
        String status = statusField.getText();
        String comments = commentsField.getText();
        String customerNumber = customerNumberField.getText();
    
        boolean isAdded = orderController.addOrder(orderNumber, orderDate, requiredDate,
         shippedDate, status, comments, customerNumber);
    
        if (isAdded) {
            // Update the table in the view if addition is successful
            viewOrders();
        } else {
            System.out.println("Error adding order!");
        }
    }

    private void addComponentsToPanelAdd(JPanel paneladd) {
        panelAdd.setLayout(new BorderLayout());
    
        JPanel addComponentsToPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Label weight
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        // Adding the "Order Number:" label
        inputPanel.add(new JLabel("Order Number:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderNumberField = new JTextField(10); //JTextField for order number
        inputPanel.add(orderNumberField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Order Date:" label
        inputPanel.add(new JLabel("Order Date:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderDate = new JTextField(10); // Define the JTextField for order date
        inputPanel.add(orderDate, gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Required Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField requiredDate = new JTextField(10); // Define the JTextField for required date
        inputPanel.add(requiredDate, gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Shipped Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField shippedDate = new JTextField(10); // Define the JTextField for shipped date
        inputPanel.add(shippedDate, gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField status = new JTextField(10); // Define the JTextField for status
        inputPanel.add(status, gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Comments:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField comments = new JTextField(10); // Define the JTextField for comments
        inputPanel.add(comments, gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Customer Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField customerNumber = new JTextField(10); // Define the JTextField for customer number
        inputPanel.add(customerNumber, gbc);
    
        panelAdd.add(inputPanel, BorderLayout.NORTH);
    
        JScrollPane scrollPane = new JScrollPane(OrdersTable); 
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

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField orderNumber = new JTextField(10);
        inputPanel.add(orderNumber, gbc);

        panelDelete.add(inputPanel, BorderLayout.NORTH);
    
        JScrollPane scrollPane = new JScrollPane(OrdersTable); 
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelDelete.add(tableContainer, BorderLayout.CENTER);
    }

    private void updateOrders() {

        JTextArea orderNumber = new JTextArea(10);
        JTextArea orderDate = new JTextArea(10);
        JTextArea requiredDate = new JTextArea(10);
        JTextArea shippedDate = new JTextArea(10);
        JTextArea status = new JTextArea(10);
        JTextArea comments = new JTextArea(10);
        JTextArea customerNumber = new JTextArea(10);

        OrdersController ordersController = new OrdersController();

        boolean isUpdated = ordersController.updateOrder(orderNumber.getText(), orderDate.getText(), requiredDate.getText(), shippedDate.getText(), status.getText(), comments.getText(), customerNumber.getText());
        
        if (isUpdated) {
            // Update the table in the view if update is successful
            viewOrders();
        } else {
            System.out.println("Error updating order!");
        }
    
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
