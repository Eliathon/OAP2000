//Created by Sebastian

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.BigDecimal;


public class ProductManagementPanel {

    private JFrame frame;
    private JPanel panel;
    private JButton viewButton; // Knapp for å vise produkter
    private JButton addButton;  // Knapp for å legge til produkt
    private JButton deleteButton;  // Knapp for å slette produkt
    private JTextArea resultTextArea;

    private JTextField productNameField;
    private JTextField productCodeField; // Legg til et felt for produktkoden
    private JTextField productLineField; // Legg til et felt for produktlinjen
    private JTextField productScaleField; // Legg til et felt for produktskalaen
    private JTextField productVendorField; // Legg til et felt for produktselgeren
    private JTextArea productDescriptionArea; // Legg til et område for produktbeskrivelsen
    private JTextField quantityInStockField; // Legg til et felt for antall på lager
    private JTextField buyPriceField; // Legg til et felt for kjøpsprisen
    private JTextField MSRPField; // Legg til et felt for MSRP

    private JComboBox<String> operationDropdown;
   

    public void start() {
        frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
        frame.setLayout(new BorderLayout());
 
       // centerPanel = new JPanel(new GridLayout(0, 2));

        JPanel menuPanel = new JPanel();
        String[] options = {"Add", "View", "Delete"};
        JComboBox<String> optionsComboBox = new JComboBox<>(options);
        

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setLayout(new FlowLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setLayout(new FlowLayout());

        JPanel labelPanel = new JPanel(new GridLayout(0, 1)); // 0 rader, 1 kolonne
        labelPanel.setPreferredSize(new Dimension(150, 200));

        JPanel fieldPanel = new JPanel(new GridLayout(0, 1)); // 0 rader, 1 kolonne
        fieldPanel.setPreferredSize(new Dimension(350, 200));
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

       /*  JPanel addButtonPanel = new JPanel();
        addButtonPanel.setLayout(new FlowLayout());*/

       /* JPanel deleteButtonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());   */

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setLayout(new FlowLayout());

       
       
        //Defining labels for the text inputs and size
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField();
        productNameField.setPreferredSize(new Dimension(200, 30));

        JLabel productCodeLabel = new JLabel("Product Code:");
        productCodeField = new JTextField();
        productCodeField.setPreferredSize(new Dimension(200, 30));

        

        JLabel productLineLabel = new JLabel("Product Line:");
        productLineField = new JTextField();
        
        

        JLabel productScaleLabel = new JLabel("Product Scale:");
        productScaleField = new JTextField();
        

        JLabel productVendorLabel = new JLabel("Product Vendor:");
        productVendorField = new JTextField();
        

        JLabel productDescriptionLabel = new JLabel("Product Description:");
        productDescriptionArea = new JTextArea();
        productDescriptionArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(productDescriptionArea);

        JLabel quantityInStockLabel = new JLabel("Quantity In Stock:");
        quantityInStockField = new JTextField();

        JLabel buyPriceLabel = new JLabel("Buy Price:");
        buyPriceField = new JTextField();

        JLabel MSRPLabel = new JLabel("MSRP:");
        MSRPField = new JTextField();

        viewButton = new JButton("View Products");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProducts();
            }
        });

        addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        optionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) optionsComboBox.getSelectedItem();
                topPanel.removeAll();
                labelPanel.removeAll();
                fieldPanel.removeAll();

              
        
                switch (selectedOption) {
                    case "Add":
        
                        
        labelPanel.add(productLineLabel);
        fieldPanel.add(productLineField);

        labelPanel.add(productScaleLabel);
        fieldPanel.add(productScaleField);

        labelPanel.add(productVendorLabel);
        fieldPanel.add(productVendorField);

        labelPanel.add(productDescriptionLabel);
        fieldPanel.add(descriptionScrollPane);

        labelPanel.add(quantityInStockLabel);
        fieldPanel.add(quantityInStockField);

        labelPanel.add(buyPriceLabel);
        fieldPanel.add(buyPriceField);

        labelPanel.add(MSRPLabel);
        fieldPanel.add(MSRPField);

        centerPanel.add(labelPanel, BorderLayout.WEST);
        centerPanel.add(fieldPanel, BorderLayout.WEST); 
        topPanel.add(addButton, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.NORTH);

     

        topPanel.add(addButton, BorderLayout.EAST);
                        
                        



                        break;
                    case "View":
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);

        topPanel.add(productNameLabel, BorderLayout.WEST);
        topPanel.add(productNameField, BorderLayout.WEST); 
        topPanel.add(viewButton, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.NORTH);
        
       

        
        
                        

                       
    break;
    case "Delete":
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);
        topPanel.add(deleteButton, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.NORTH);

                        break;
                }
                topPanel.revalidate();
                topPanel.repaint();

                labelPanel.revalidate();
                fieldPanel.repaint();

                labelPanel.revalidate();
                fieldPanel.repaint();
            }
        });

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

           

        menuPanel.add(optionsComboBox, BorderLayout.EAST);

        topPanel.add(buttonPanel, BorderLayout.EAST);
      
        topPanel.add(menuPanel, BorderLayout.NORTH);
        
        
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
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
        String productCode = productCodeField.getText();
        String productName = productNameField.getText();
        String productLine = productLineField.getText();
        String productScale = productScaleField.getText();
        String productVendor = productVendorField.getText();
        String productDescription = productDescriptionArea.getText();
        int quantityInStock = Integer.parseInt(quantityInStockField.getText());
        BigDecimal buyPrice = new BigDecimal(buyPriceField.getText());
        BigDecimal MSRP = new BigDecimal(MSRPField.getText());

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, productCode);
            statement.setString(2, productName);
            statement.setString(3, productLine);
            statement.setString(4, productScale);
            statement.setString(5, productVendor);
            statement.setString(6, productDescription);
            statement.setInt(7, quantityInStock);
            statement.setBigDecimal(8, buyPrice);
            statement.setBigDecimal(9, MSRP);

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

    private void deleteProduct() {
        String productCode = productCodeField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE productCode = ?");
            statement.setString(1, productCode);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                resultTextArea.setText("Product deleted successfully.");
            } else {
                resultTextArea.setText("Product with code " + productCode + " not found.");
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


