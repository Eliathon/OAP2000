//*  Created by Patrik*//

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
            viewPanel();

                createAndShowGUI();
            }
        });
    }

	private JPanel viewPanel() {
		JPanel viewPanel = new JPanel(new BorderLayout());
		viewButton = new JButton("View Order");
		resultTable = new JTable();
		JScrollPane tableScrollPane = new JScrollPane(resultTable);
		tableScrollPane.setPreferredSize(new Dimension(500, 300));

		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewOrder();
			}
		});

		JPanel inputPanel = new JPanel();
		inputPanel.add(new JLabel("Enter Order Number:"));
		orderNumberField = new JTextField(10);
		inputPanel.add(orderNumberField);
		inputPanel.add(viewButton);

		viewPanel.add(inputPanel, BorderLayout.NORTH);
		viewPanel.add(tableScrollPane, BorderLayout.CENTER);

		return viewPanel;
	}


    private JPanel addPanel() {
        JPanel addPanel = new JPanel();
        addButton = new JButton("Add Order");
         addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });
        addPanel.add(addButton);

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


        return addPanel;
    }

    private JPanel updatePanel() {
        JPanel updatePanel = new JPanel();
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });

        updatePanel.add(updateButton);

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
        
   

        return updatePanel;
    }

    private JPanel deletePanel() {
        JPanel deletePanel = new JPanel();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
        deletePanel.add(deleteButton);

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


        return deletePanel;
    }
    
    private void createAndShowGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int windowWidth = (int) (width * 1); 
        int windowHeight = (int) (height * 1);
        frame = new JFrame("Order Management");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tabbedPane = new JTabbedPane();
    
        // View Orders Tab
        JPanel viewPanel = new JPanel();
        viewPanel.add(new JLabel("Order Number"));
        tabbedPane.addTab("View Orders", viewPanel);
       
        JTextField searchTextField = new JTextField(10);
        JButton searchButton = new JButton("View All Orders");
    
        viewPanel.add(searchTextField, BorderLayout.WEST);
        viewPanel.add(searchButton, BorderLayout.NORTH);

    
    
        // Add Order Tab
        JPanel addPanel = new JPanel();
        tabbedPane.addTab("Add Order", addPanel);
        
        JLabel orderNumberAddLabel = new JLabel("Order Number");
        JTextField orderNumberAddField = new JTextField(7);
        addPanel.add(orderNumberAddLabel);
        addPanel.add(orderNumberAddField);
       
        JLabel orderDateAddLabel = new JLabel("Order Date");;
        JTextField orderDateAddField = new JTextField(10);
        addPanel.add(orderDateAddLabel);
        addPanel.add(orderDateAddField);
        
        JLabel requiredDateAddLabel = new JLabel("Required Date");;
        JTextField requiredDateAddField = new JTextField(10);
        addPanel.add(requiredDateAddLabel);
        addPanel.add(requiredDateAddField);
        

        JLabel shippedDateAddLabel = new JLabel("Shipped Date");;
        JTextField shippedDateAddField = new JTextField(10);
        addPanel.add(shippedDateAddLabel);
        addPanel.add(shippedDateAddField);

        JLabel statusAddLabel = new JLabel("Order Status");;
        JTextField statusAddField = new JTextField(10);
        addPanel.add(statusAddLabel);
        addPanel.add(statusAddField);

        JLabel commentsAddLabel = new JLabel("Order Comments");
        JTextField commentsAddField = new JTextField(10);
        addPanel.add(commentsAddLabel);
        addPanel.add(commentsAddField);
        
        JLabel customerNumberAddLabel = new JLabel("Customer Number");
        JTextField customerNumberAddField = new JTextField(10);
        addPanel.add(customerNumberAddLabel);
        addPanel.add(customerNumberAddField);
        
        JButton anotherButton = new JButton("Add Order");
        addPanel.add(anotherButton);

     // Update Order Tab
        JPanel updatePanel = new JPanel();
        updatePanel.add(new JLabel("Update"));
        tabbedPane.addTab("Update Order", updatePanel);
        
        JLabel orderNumberUpdateLabel = new JLabel("Order Number");
        JTextField orderNumberUpdateField = new JTextField(7);
        updatePanel.add(orderNumberUpdateLabel);
        updatePanel.add(orderNumberUpdateField);
       
        JLabel orderDateUpdateLabel = new JLabel("Order Date");;
        JTextField orderDateUpdateField = new JTextField(10);
        updatePanel.add(orderDateUpdateLabel);
        updatePanel.add(orderDateUpdateField);
        
        JLabel requiredDateUpdateLabel = new JLabel("Required Date");;
        JTextField requiredDateUpdateField = new JTextField(10);
        updatePanel.add(requiredDateUpdateLabel);
        updatePanel.add(requiredDateUpdateField);
        

        JLabel shippedDateUpdateLabel = new JLabel("Shipped Date");;
        JTextField shippedDateUpdateField = new JTextField(10);
        updatePanel.add(shippedDateUpdateLabel);
        updatePanel.add(shippedDateUpdateField);

        JLabel statusUpdateLabel = new JLabel("Order Status");;
        JTextField statusUpdateField = new JTextField(10);
        updatePanel.add(statusUpdateLabel);
        updatePanel.add(statusUpdateField);

        JLabel commentsUpdateLabel = new JLabel("Order Comments");
        JTextField commentsUpdateField = new JTextField(10);
        updatePanel.add(commentsUpdateLabel);
        updatePanel.add(commentsUpdateField);
        
        JLabel customerNumberUpdateLabel = new JLabel("Customer Number");
        JTextField customerNumberUpdateField = new JTextField(10);
        updatePanel.add(customerNumberUpdateLabel);
        updatePanel.add(customerNumberUpdateField);
       
        JButton updateButton = new JButton("Update Order");
        updatePanel.add(updateButton);

        // Delete Order Tab
        JPanel deletePanel = new JPanel();
        deletePanel.add(new JLabel("Delete"));
        tabbedPane.addTab("Delete Order", deletePanel);

        JTextField deleteTextField = new JTextField(10);
        JButton deleteButton = new JButton("Delete Order");

        deletePanel.add(deleteTextField);
        deletePanel.add(deleteButton);
    
        frame.add(tabbedPane);
        frame.setSize(1100, 600);
        frame.setVisible(true);


    }


    private void viewOrder() {
        String searchQuery = orderNumberField.getText();

            // Validate input fiels
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter a valid Order Number to seach", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            String sqlQuery = "SELECT * FROM orders WHERE orderNumber LIKE '%" + searchQuery + "%'";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

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

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }   catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while retrieving orders from database", "Database error", JOptionPane.ERROR_MESSAGE);
    }
    }

    private void addOrder() {

        String orderNumber = orderNumberField.getText();
        String orderDate = orderDateField.getText();
        String requiredDate = requiredDateField.getText();
        String shippedDate = shippedDateField.getText();
        String status = statusField.getText();
        String comments = commentsDescriptionArea.getText();
        String customerNumber = customerNumberField.getText();
    
        // Validate input fields 
        if (orderNumber.isEmpty() || orderDate.isEmpty() || requiredDate.isEmpty() || shippedDate.isEmpty() || status.isEmpty() || comments.isEmpty() || customerNumber.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fill out all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            //Log order details for debugging
        System.out.println("Adding Order - Order Number: " + orderNumber + ", OrderDate: " + orderDate + ", RequiredDate: " + requiredDate + ", ShippedDate: " + shippedDate + ", Status: " + status + ", Comments: " + comments + ", CustomerNumber: " + customerNumber);

         // Perform the database insertion
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            String sql = "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, orderNumber);
            preparedStatement.setString(2, orderDate); 
            preparedStatement.setString(3, requiredDate);
            preparedStatement.setString(4, shippedDate);
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, comments);
            preparedStatement.setString(7, customerNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order added successfully!");
                resultMessageArea.setText("Order added successfully!");
            } else {
                System.out.println("Failed to add order!");
                resultMessageArea.setText("Failed to add order!");
            }
        
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while adding new order to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    
    private void updateOrder() {
        String orderNumber = orderNumberField.getText();
        String updateOrderDate = orderDateField.getText();
        String updatestatus = statusField.getText();
        String updatecomments = commentsDescriptionArea.getText();
        String updateCustomerNumber = customerNumberField.getText();
    
        // Validate input fields if needed
     if (orderNumber.isEmpty() || orderDate.isEmpty() || requiredDate.isEmpty() || shippedDate.isEmpty() || status.isEmpty() || comments.isEmpty() || customerNumber.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fill out all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        // Perform the database update
        System.out.println("Updating Order - Order Number: " + orderNumber + ", OrderDate: " + updateOrderDate + ", Status: " + updatestatus + ", Comments: " + updatecomments + ", CustomerNumber: " + updateCustomerNumber);
        // Update the result message or any other UI component
        resultMessageArea.setText("Order updated successfully!");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            String sql = "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, orderNumber);
            preparedStatement.setString(2, orderDate); 
            preparedStatement.setString(3, requiredDate);
            preparedStatement.setString(4, shippedDate);
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, comments);
            preparedStatement.setString(7, customerNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order added successfully!");
                resultMessageArea.setText("Order added successfully!");
            } else {
                System.out.println("Failed to add order!");
                resultMessageArea.setText("Failed to add order!");
            }
        
        preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while updating order to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }} 
        }  
    private void deleteOrder() {
        String orderNumber = orderNumberField.getText();
       
        // Validate input if needed
        // Perform the database deletion
        // ...
        
        System.out.println("Deleting Order - Order Number: " + orderNumber);
        // Update the result message or any other UI component
        resultMessageArea.setText("Order deleted successfully!");
    }
    
    public static void main(String[] args) {
        new OrderManagementPanel().start();
    }
}