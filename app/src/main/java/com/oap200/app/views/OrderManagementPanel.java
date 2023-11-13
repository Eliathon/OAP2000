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
    private JButton viewButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable resultTable;
    private JTextField orderNumberField;
    private JTextField orderDateField;
    private JTextField requiredDateField;
    private JTextField shippedDateField;
    private JTextField statusField;
    private JTextArea commentsDescriptionArea;
    private JTextField customerNumberField;

    private JTextArea resultMessageArea;
    private JScrollPane messageScrollPane;

    public void start() {

        frame = new JFrame("Order Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600); // Increase height and width to place elements
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        resultTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        tableScrollPane.setPreferredSize(new Dimension(1900, 600));

        resultMessageArea = new JTextArea();
        resultMessageArea.setEditable(false);
        messageScrollPane = new JScrollPane(resultMessageArea);
        messageScrollPane.setPreferredSize(new Dimension(800, 100));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setLayout(new FlowLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setLayout(new FlowLayout());

        JLabel orderNumberLabel = new JLabel("Order Number");
        orderNumberField = new JTextField();

        JLabel orderDateLabel = new JLabel("Order Date");
        orderDateField = new JTextField();

        JLabel requiredDateLabel = new JLabel("Required Date");
        requiredDateField = new JTextField();

        JLabel shippedDateLabel = new JLabel("Shipped Date");
        shippedDateField = new JTextField();

        JLabel statusLabel = new JLabel("Order Status");
        statusField = new JTextField();

        JLabel commentsLabel = new JLabel("Comments");
        commentsDescriptionArea = new JTextArea();
        commentsDescriptionArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(commentsDescriptionArea);

        JLabel customerNumberLabel = new JLabel("Customer Number");
        customerNumberField = new JTextField();

        {
            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(bottomPanel, BorderLayout.SOUTH);
            frame.setVisible(true);

        }

        viewButton = new JButton("View Orders");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrder();
            }
        });

        {
            addButton = new JButton("Add Order");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addOrder();
                }

                private void addOrder() {
                }
            });
        }

        {
            updateButton = new JButton("Update Order");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateOrder();
                }

                private void updateOrder() {
                }
            });
        }
        {
            deleteButton = new JButton("Delete Order");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteOrder();
                }

                private void deleteOrder() {
                }
            });
        }

        topPanel.add(viewButton);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        topPanel.add(addButton);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        topPanel.add(updateButton);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        topPanel.add(deleteButton);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);

    }

    private void viewOrder() {
        String searchQuery = orderNumberField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classi", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM orders WHERE orderNumber LIKE '%" + searchQuery + "%'");

            // Opprett et dataobjekt for tabellen
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Order Number");
            tableModel.addColumn("Order Date");
            tableModel.addColumn("Required Date");
            tableModel.addColumn("Shipped Date");
            tableModel.addColumn("Status");
            tableModel.addColumn("Customer Number");
            tableModel.addColumn("Comments Description");

            // Fyll tabellen med data fra resultatsettet
            while (resultSet.next()) {
                String orderNumber = resultSet.getString("orderNumber");
                String orderDate = resultSet.getString("orderDate");
                String requiredDate = resultSet.getString("requiredDate");
                String shippedDate = resultSet.getString("shippedDate");
                String status = resultSet.getString("Status");
                String customerNumber = resultSet.getString("customerNumber");
                String comments = resultSet.getString("commentsDescription");
                tableModel.addRow(new Object[] { orderNumber, orderDate, requiredDate, shippedDate, status, comments,
                        customerNumber }); // Legg til rad med data
            }
            // Oppdate JTable with the new tablemodel
            resultTable.setModel(tableModel);

            resultTable = new JTable();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void addOrder() {

        String orderNumber = orderNumberField.getText();
        String orderDate = orderDateField.getText();
        String requiredDate = requiredDateField.getText();
        String shippedDate = shippedDateField.getText();
        String status = statusField.getText();
        String customerNumber = customerNumberField.getText();
        String comments = commentsDescriptionArea.getText();

        if (orderNumber.isEmpty() || orderDate.isEmpty() || requiredDate.isEmpty() || status.isEmpty()
                || customerNumber.isEmpty()) {
            resultMessageArea.setText("Fill in all required fields to add order");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classi", "root", "");
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, customerNumber, commentsDescription) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, orderNumber);
            statement.setString(2, orderDate);
            statement.setString(3, requiredDate);
            statement.setString(4, shippedDate);
            statement.setString(5, status);
            statement.setString(6, customerNumber);
            statement.setString(7, comments);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                resultMessageArea.setText("Order added successfully!");
            } else {
                resultMessageArea.setText("Failed to add the order");
            }

            resultTable = new JTable();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void updateOrder() {

        String orderNumber = orderNumberField.getText();
        String updateOrderDate = orderDateField.getText();
        String updateRequiredDate = requiredDateField.getText();
        String updateShippedDate = shippedDateField.getText();
        String updatestatus = statusField.getText();
        String updateCustomerNumber = customerNumberField.getText();
        String updateComments = commentsDescriptionArea.getText();

        if (orderNumber.isEmpty() || updateOrderDate.isEmpty() || updateRequiredDate.isEmpty() || updatestatus.isEmpty()
                || updateCustomerNumber.isEmpty())
            ;
        resultMessageArea.setText("Fill in all required fields to update order");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classi", "root", "");
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE orders SET orderDate=?, requiredDate=?, shippedDate=?, status=?, customerNumber=?, commentsDescription=?, WHERE orderNumber=?");
            statement.setString(1, updateOrderDate);
            statement.setString(2, updateRequiredDate);
            statement.setString(3, updateShippedDate);
            statement.setString(4, updatestatus);
            statement.setString(5, updateCustomerNumber);
            statement.setString(6, updateComments);
            statement.setString(7, orderNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                resultMessageArea.setText("Order updated successfully!");
            } else {
                resultMessageArea.setText("Failed to update the order");
            }

            resultTable = new JTable();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteOrder() {
        String orderNumber = orderNumberField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classi", "root", "");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE orderNumber = ?");
            statement.setString(1, orderNumber);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                resultMessageArea.setText("Order deleted successfully!");
            } else {
                resultMessageArea.setText("Failed to delete the order");
            }

            resultTable = new JTable();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OrderManagementPanel orderManagementPanel = new OrderManagementPanel();
        orderManagementPanel.start();
    }
}
