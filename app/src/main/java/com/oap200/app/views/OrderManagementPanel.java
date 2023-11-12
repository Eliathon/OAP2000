// Created by Patrik

package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;


public class OrderManagementPanel {

    private JFrame frame;
    private JPanel panel;
    private JTable resultTable;

    private JButton viewButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
   
    private JTextField orderNumberField;
    private JTextField orderDateField;
    private JTextField requiredDateField;
    private JTextField shippedDateField;
    private JTextField statusField;
    private JTextArea commentsDescriptionArea;
    private JTextField customerNumberField;
    private JTextArea resultMessageArea;

    private Connection connection;

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Order Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        JTabbedPane tabbedPane = new JTabbedPane();
    
        // View Orders Tab
        JPanel viewPanel = new JPanel();
        viewPanel.add(new JLabel("View Orders Content"));
        tabbedPane.addTab("View Orders", viewPanel);
    
        // Add Order Tab
        JPanel addPanel = new JPanel();
        addPanel.add(new JLabel("Add Order Content"));
        tabbedPane.addTab("Add Order", addPanel);
    
        // Update Order Tab
        JPanel updatePanel = new JPanel();
        updatePanel.add(new JLabel("Update Order Content"));
        tabbedPane.addTab("Update Order", updatePanel);
    
        // Delete Order Tab
        JPanel deletePanel = new JPanel();
        deletePanel.add(new JLabel("Delete Order Content"));
        tabbedPane.addTab("Delete Order", deletePanel);
    
        frame.add(tabbedPane);
        frame.setSize(1100, 600);
        frame.setVisible(true);
    }

    

    private JPanel createAddPanel() {
        JPanel addPanel = new JPanel();
        orderNumberField = new JTextField(10);
        orderDateField = new JTextField(10);
        requiredDateField = new JTextField();
        shippedDateField = new JTextField();
        statusField = new JTextField();
        commentsDescriptionArea = new JTextArea();
        customerNumberField = new JTextField();

        addPanel.add(new JLabel("Order Number"));
        addPanel.add(orderNumberField);

        addPanel.add(new JLabel("Order Date"));
        addPanel.add(orderDateField);

        addPanel.add(new JLabel("Required Date"));
        addPanel.add(requiredDateField);

        addPanel.add(new JLabel("Shipped Date"));
        addPanel.add(shippedDateField);

        addPanel.add(new JLabel("Status"));
        addPanel.add(statusField);

        addPanel.add(new JLabel("Order Comments"));
        addPanel.add(commentsDescriptionArea);

        addPanel.add(new JLabel("Customer Number"));
        addPanel.add(customerNumberField);

        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        addPanel.add(addButton);

        return addPanel;
    }

    private JPanel createUpdatePanel() {
        JPanel updatePanel = new JPanel();
        orderNumberField = new JTextField(10);
        orderDateField = new JTextField(10);
        requiredDateField = new JTextField();
        shippedDateField = new JTextField();
        statusField = new JTextField();
        commentsDescriptionArea = new JTextArea();
        customerNumberField = new JTextField();

        updatePanel.add(new JLabel("Order Number"));
        updatePanel.add(orderNumberField);

        updatePanel.add(new JLabel("Order Date"));
        updatePanel.add(orderDateField);

        updatePanel.add(new JLabel("Required Date"));
        updatePanel.add(requiredDateField);

        updatePanel.add(new JLabel("Shipped Date"));
        updatePanel.add(shippedDateField);

        updatePanel.add(new JLabel("Status"));
        updatePanel.add(statusField);

        updatePanel.add(new JLabel("Order Comments"));
        updatePanel.add(commentsDescriptionArea);

        updatePanel.add(new JLabel("Customer Number"));
        updatePanel.add(customerNumberField);
        

        JButton updateButton = new JButton("Update Order");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
        updatePanel.add(updateButton);

        return updatePanel;
    }

    private JPanel createDeletePanel() {
        JPanel deletePanel = new JPanel();
        orderNumberField = new JTextField(10);
        orderDateField = new JTextField(10);
        requiredDateField = new JTextField();
        shippedDateField = new JTextField();
        statusField = new JTextField();
        commentsDescriptionArea = new JTextArea();
        customerNumberField = new JTextField();

        deletePanel.add(new JLabel("Order Number"));
        orderNumberField = new JTextField(10);
        deletePanel.add(orderNumberField);

        JButton deleteButton = new JButton("Delete Order");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        deletePanel.add(deleteButton);

        return deletePanel;
    }

    private void viewOrder() {
        String searchQuery = orderNumberField.getText();
    
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE orderNumber = '" + searchQuery + "'");

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Order Number");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Required Date");
        tableModel.addColumn("Shipped Date");
        tableModel.addColumn("Status");
        tableModel.addColumn("Comments");
        tableModel.addColumn("Customer Number");

        while (resultSet.next()) {
            String orderNumber = resultSet.getString("orderNumber");
            String orderDate = resultSet.getString("orderDate");
            String requiredDate = resultSet.getString("requiredDate");
            String shippedDate = resultSet.getString("shippedDate");
            String status = resultSet.getString("status");
            String comments = resultSet.getString("comments");
            String customerNumber = resultSet.getString("customerNumber");

            tableModel.addRow(new Object[]{orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber});

        }
    
        resultTable.setModel(tableModel);

        connection.close();
    }   catch (SQLException ex) {
            ex.printStackTrace();
    }
    }

    private void addOrder() {
        String orderNumber = orderNumberField.getText();
        String orderDate = orderDateField.getText();
        // Get other input fields...
    
        // Validate input fields if needed
    
        // Perform the database insertion
        // ...
    
        // Update the result message or any other UI component
        resultMessageArea.setText("Order added successfully!");
    }
    
    private void updateOrder() {
        String orderNumber = orderNumberField.getText();
        String updateOrderDate = orderDateField.getText();
        // Get other updated fields...
    
        // Validate input fields if needed
    
        // Perform the database update
        // ...
    
        // Update the result message or any other UI component
        resultMessageArea.setText("Order updated successfully!");
    }
    
    private void deleteOrder() {
        String orderNumber = orderNumberField.getText();
    
        // Validate input if needed
    
        // Perform the database deletion
        // ...
    
        // Update the result message or any other UI component
        resultMessageArea.setText("Order deleted successfully!");
    }
    
    public static void main(String[] args) {
        new OrderManagementPanel().start();
    }
}