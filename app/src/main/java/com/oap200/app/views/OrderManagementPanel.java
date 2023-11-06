package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.BigDecimal;

public class OrderManagementPanel {

    private JFrame frame;
    private JButton viewButton; 
    private JButton addButton;  
    private JButton updateButton; 
    private JButton deleteButton;

    private JTextField orderNumberField;
    private JTextField orderDateField;
    private JTextField requiredDateField;
    private JTextField shippedDateField;
    private JTextField status;
    private JTextArea comments;
    private JTextField customerNumber;
    private JTable resultTable;

    public void start() {

        frame = new JFrame("Order Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setLayout(new FlowLayout());

        viewButton = new JButton("View Orders");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrder();
            }
        });

        {
            addButton = new JButton("addOrders");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addOrder();
                }
            });
        }
    
    {
        updateButton = new JButton("updateOrders");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });
    }
    {
        deleteButton = new JButton("deleteOrders");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE orderNumber LIKE '%" + searchQuery + "%'");
    
            // Opprett et dataobjekt for tabellen
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Order Number"); // Legg til kolonner etter behov
    
            // Fyll tabellen med data fra resultatsettet
            while (resultSet.next()) {
                String orderNumber = resultSet.getString("orderNumber");
                tableModel.addRow(new Object[]{orderNumber}); // Legg til rad med data
            }
    
            // Oppdater JTable med det nye datamodellen
            resultTable.setModel(tableModel);
    
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
