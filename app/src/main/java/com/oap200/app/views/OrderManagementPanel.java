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
    private JTable resultTable;
    private JTextField orderNumberField;
    private JTextField orderDateField;
    private JTextField requiredDateField;
    private JTextField shippedDateField;
    private JTextField statusField;
    private JTextArea commentsDescriptionArea;
    private JTextField customerNumberField;
    private JTextArea resultMessageArea;

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

    private JPanel createViewPanel() {
        JPanel viewPanel = new JPanel(new BorderLayout());

        resultTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        tableScrollPane.setPreferredSize(new Dimension(1900, 600));

        resultMessageArea = new JTextArea();
        resultMessageArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(resultMessageArea);
        messageScrollPane.setPreferredSize(new Dimension(800, 100));

        JPanel panel = new JPanel();
        panel.add(new JLabel("Order Number"));
        panel.add(orderNumberField);
        panel.add(new JButton(new AbstractAction("View Orders") {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrder();
            }
        }));

        viewPanel.add(panel, BorderLayout.NORTH);
        viewPanel.add(tableScrollPane, BorderLayout.CENTER);
        viewPanel.add(messageScrollPane, BorderLayout.SOUTH);

        return viewPanel;
    }

    private JPanel createAddPanel() {
        JPanel addPanel = new JPanel();
        // Add components for adding orders
        // ...

        addPanel.add(new JLabel("Order Number"));
        addPanel.add(orderNumberField);
        addPanel.add(new JLabel("Order Date"));
        addPanel.add(orderDateField);
        // Add other components...

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
        // Add components for updating orders
        // ...

        updatePanel.add(new JLabel("Order Number"));
        updatePanel.add(orderNumberField);
        updatePanel.add(new JLabel("Order Date"));
        updatePanel.add(orderDateField);
        // Add other components...

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
        // Add components for deleting orders
        // ...

        deletePanel.add(new JLabel("Order Number"));
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

        DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
        tableModel.setRowCount(0);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classi", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE orderNumber LIKE '%" + searchQuery + "%'");

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("orderNumber");
                String orderDate = resultSet.getString("orderDate");
                String requiredDate = resultSet.getString("requiredDate");
                String shippedDate = resultSet.getString("shippedDate");
                String status = resultSet.getString("Status");
                String customerNumber = resultSet.getString("customerNumber");
                String comments = resultSet.getString("commentsDescription");
                tableModel.addRow(new Object[]{orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber});
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    private void addOrder() {
        // Create a dialog for adding an order
        JDialog addOrderDialog = new JDialog(frame, "Add Order", true);
        addOrderDialog.setSize(400, 300);
        addOrderDialog.setLayout(new BorderLayout());
    
        // Create input fields
        JTextField orderNumberInput = new JTextField(20);
        JTextField orderDateInput = new JTextField(20);
        JTextField requiredDateInput = new JTextField(20);
        JTextField shippedDateInput = new JTextField(20);
        JTextField statusInput = new JTextField(20);
        JTextField customerNumberInput = new JTextField(20);
        JTextArea commentsInput = new JTextArea(4, 20);
        commentsInput.setLineWrap(true);
        JScrollPane commentsScrollPane = new JScrollPane(commentsInput);
    
        // Create labels for input fields
        JLabel orderNumberLabel = new JLabel("Order Number:");
        JLabel orderDateLabel = new JLabel("Order Date:");
        JLabel requiredDateLabel = new JLabel("Required Date:");
        JLabel shippedDateLabel = new JLabel("Shipped Date:");
        JLabel statusLabel = new JLabel("Order Status:");
        JLabel customerNumberLabel = new JLabel("Customer Number:");
        JLabel commentsLabel = new JLabel("Comments:");
    
        // Create a button to add the order
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve values from input fields
                String orderNumber = orderNumberInput.getText();
                String orderDate = orderDateInput.getText();
                String requiredDate = requiredDateInput.getText();
                String shippedDate = shippedDateInput.getText();
                String status = statusInput.getText();
                String customerNumber = customerNumberInput.getText();
                String comments = commentsInput.getText();
    
                // Perform validation and insertion logic here
                // ...
    
                // Close the dialog
                addOrderDialog.dispose();
            }
        });
    
        // Create a panel to hold input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2));
        inputPanel.add(orderNumberLabel);
        inputPanel.add(orderNumberInput);
        inputPanel.add(orderDateLabel);
        inputPanel.add(orderDateInput);
        inputPanel.add(requiredDateLabel);
        inputPanel.add(requiredDateInput);
        inputPanel.add(shippedDateLabel);
        inputPanel.add(shippedDateInput);
        inputPanel.add(statusLabel);
        inputPanel.add(statusInput);
        inputPanel.add(customerNumberLabel);
        inputPanel.add(customerNumberInput);
        inputPanel.add(commentsLabel);
        inputPanel.add(commentsScrollPane);
        inputPanel.add(new JLabel()); // Placeholder for layout
    
        // Add components to the dialog
        addOrderDialog.add(inputPanel, BorderLayout.CENTER);
        addOrderDialog.add(addButton, BorderLayout.SOUTH);
    
        // Make the dialog visible
        addOrderDialog.setVisible(true);
    }
    
        
    

    private void updateOrder() {
        // Implement the updateOrder method
    }

    private void deleteOrder() {
        // Implement the deleteOrder method
    }

    public static void main(String[] args) {
        new OrderManagementPanel().start();
    }
}