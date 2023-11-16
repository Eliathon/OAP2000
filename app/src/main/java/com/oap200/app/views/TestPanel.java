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

public class TestPanel {

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
        
        // Initialize other components
        viewButton = new JButton("View Order");  // Make sure to initialize this
        addButton = new JButton("Add Order");
        updateButton = new JButton("Update Order");
        deleteButton = new JButton("Delete Order");
        orderNumberField = new JTextField(20);
        // Initialize other JTextFields and JTextAreas similarly
    
        // Layout for input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(0, 2)); // Adjust grid layout as needed
        inputPanel.add(new JLabel("Order Number:"));
        inputPanel.add(orderNumberField);
        // Add other fields to inputPanel similarly
    
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
    
        // Add inputPanel and buttonPanel to viewPanel
        viewPanel.add(inputPanel, BorderLayout.NORTH);
        viewPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        return viewPanel; // Return the completed panel
    }





private void createAndShowGUI() {
    frame = new JFrame("Order Management");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(viewPanel());
    frame.pack();
    frame.setVisible(true);
}
public static void main(String[] args) {
    new TestPanel().start();
}
}