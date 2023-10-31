// Created by Sindre

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProductManagementPanel {

    private JFrame frame;
    private JPanel panel;
    private JButton viewButton; // Endret navn på knappen til "searchButton"
    private JButton addButton; //Add product
    private JTextField productNameField;
    private JTextArea resultTextArea; // La til en JTextArea for å vise resultatene

    public void start() {
        frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2)); // La til en ekstra rad for søkeknappen

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField();

        viewButton = new JButton("View Product"); // Endret tekst på knappen
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProducts(); // Kall den nye metoden for søk
            }
        });

        addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        resultTextArea = new JTextArea(); // Opprett en JTextArea for resultater
        resultTextArea.setEditable(false); // Sørg for at den ikke kan redigeres
       
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        panel.add(productNameLabel);
        panel.add(productNameField);
        panel.add(viewButton); // Legg til knappen for søk
        panel.add(addButton);
        panel.add(scrollPane); 

        frame.add(panel);
        frame.setVisible(true);
    }

    private void viewProducts() {
        String searchQuery = productNameField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE productName LIKE '%" + searchQuery + "%'");

            StringBuilder resultText = new StringBuilder();
            while (resultSet.next()) {
                String productName = resultSet.getString("productName");
                resultText.append(productName).append("\n");
            }

            resultTextArea.setText(resultText.toString());

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addProduct() {
        String productName = productNameField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/databasenavn", "brukernavn", "passord");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO produkter (productName) VALUES (?)");
            statement.setString(1, productName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                resultTextArea.setText("Product added successfully.");
            } else {
                resultTextArea.setText("Failed to add product.");
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProductManagementPanel productManagementPanel = new ProductManagementPanel();
        productManagementPanel.start();
    }
}


