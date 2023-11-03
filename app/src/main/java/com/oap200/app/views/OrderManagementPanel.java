package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OrderManagementPanel extends JFrame {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private Connection connection;
    private JButton viewButton;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField orderNumberField, orderDateField, requiredDateField, shippedDateField, statusField, commentsField, customerNumberField;
    private JTextField orderNumberField, productCodeField, quantityOrderedField, priceEachField, OrderLineNumberField; 
  
   
   public class OrderManagementPanel() {

    public static void main(String[]args) {
        JFrame frame = new JFrame("Order Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton viewButton = new JButton("View");
        JButton createButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.NORTH);

        JTable orderTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(orderTable);

        frame.add(scrollPane, BorderLayout.Center);

        frame.setVisible(true);

        }
   
   

        // Create action listeners for buttons
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            
                List<Order> orders = fetchOrdersFromDatabase(connection);
           
            DefaultTableModel model = new DefaultTableModel();

        classicmodels.addColumn("Ordernumber");
        classicmodels.addColumn("Costumernumber");
        classicmodels.addColumn("Status");
        classicmodels.addColumn("Quantity ordered");

        for(Order order : orders) {
            classicmodels.addRow(new object[]{order.getOrderNumber(), order.getCustomerNumber(), order.getQuantityOrdered(), order.getStatus()});
        }
           orderTable.setmodel(classicmodels);
    }
       });

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the Create functionality to add a new order
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the Update functionality to edit an existing order
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the Delete functionality to remove an order
            }
        });

        // Set up the database connection
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
            // Use 'connection' to execute database queries
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OrderManagementPanel().setVisible(true);
            }
        });
    }
}
