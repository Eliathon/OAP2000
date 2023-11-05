//Created by Sebastian

package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.BigDecimal;


public class ProductManagementPanel {

    private JFrame frame;
    private JButton viewButton; // Knapp for å vise produkter
    private JButton addButton;  // Knapp for å legge til produkt
    private JButton deleteButton;  // Knapp for å slette produkt
    private JButton updateButton;
    private JTextArea resultTextArea;
    private JTable resultTable;

    private JTextField productNameField;
    private JTextField productCodeField; // Legg til et felt for produktkoden
    private JTextField productLineField; // Legg til et felt for produktlinjen
    private JTextField productScaleField; // Legg til et felt for produktskalaen
    private JTextField productVendorField; // Legg til et felt for produktselgeren
    private JTextArea productDescriptionArea; // Legg til et område for produktbeskrivelsen
    private JTextField quantityInStockField; // Legg til et felt for antall på lager
    private JTextField buyPriceField; // Legg til et felt for kjøpsprisen
    private JTextField MSRPField; // Legg til et felt for MSRP

    private JTextArea resultMessageArea;
    private JScrollPane messageScrollPane;

    private JComboBox<String> operationDropdown;
   

    public void start() {
        frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
        frame.setLayout(new BorderLayout());

        resultTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
       
        resultMessageArea = new JTextArea();
        resultMessageArea.setEditable(false);
        messageScrollPane = new JScrollPane(resultMessageArea);
        messageScrollPane.setPreferredSize(new Dimension(800, 100));
        
 

        JPanel menuPanel = new JPanel();
        String[] options = {"Update", "Add", "View", "Delete"};
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

                updateButton = new JButton("Update Product");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        buttonPanel.add(updateButton);

        

        optionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) optionsComboBox.getSelectedItem();
                topPanel.removeAll();
                labelPanel.removeAll();
                fieldPanel.removeAll();
                bottomPanel.removeAll();
                resetTable();
                resetView();
                
               
                
                

              
        
                switch (selectedOption) {
                    case "Update":
                       

                        labelPanel.add(productCodeLabel);
    fieldPanel.add(productCodeField);

    labelPanel.add(productNameLabel);
    fieldPanel.add(productNameField);

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

    topPanel.add(updateButton, BorderLayout.WEST);
    topPanel.add(menuPanel, BorderLayout.NORTH);
    bottomPanel.add(tableScrollPane, BorderLayout.CENTER);
    bottomPanel.add(messageScrollPane, BorderLayout.SOUTH);

                    break;

                    case "Add":
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);

        topPanel.add(productNameLabel, BorderLayout.WEST);
        topPanel.add(productNameField, BorderLayout.WEST); 
        
                        
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

        bottomPanel.add(messageScrollPane, BorderLayout.SOUTH);

     

                        break;
                    case "View":
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);

        topPanel.add(productNameLabel, BorderLayout.WEST);
        topPanel.add(productNameField, BorderLayout.WEST); 
        topPanel.add(viewButton, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.NORTH);

      
        bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);
        
       
                       
                        break;
                        case "Delete":
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);
        topPanel.add(deleteButton, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.NORTH);
        bottomPanel.add(messageScrollPane, BorderLayout.SOUTH);

        

                        break;
                }
                topPanel.revalidate();
                topPanel.repaint();

                labelPanel.revalidate();
                fieldPanel.repaint();

                labelPanel.revalidate();
                fieldPanel.repaint();

                bottomPanel.revalidate();
                bottomPanel.repaint();

                
            }
        });

        

        menuPanel.add(optionsComboBox, BorderLayout.EAST);
        topPanel.add(menuPanel, BorderLayout.NORTH);
        
        
        

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        
    }
    private void resetView() {
        DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
        tableModel.setRowCount(0); // Tømmer alle rader fra tabellen
        resultMessageArea.setText(""); // Tømmer tekstområdet for meldinger
    }

    private void resetTable() {
        DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
        tableModel.setRowCount(0); // Tømmer alle rader fra tabellen
    }

    private void updateProduct() {
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
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE products SET productName=?, productLine=?, productScale=?, " +
                "productVendor=?, productDescription=?, quantityInStock=?, buyPrice=?, MSRP=? WHERE productCode=?"
            );
            statement.setString(1, productName);
            statement.setString(2, productLine);
            statement.setString(3, productScale);
            statement.setString(4, productVendor);
            statement.setString(5, productDescription);
            statement.setInt(6, quantityInStock);
            statement.setBigDecimal(7, buyPrice);
            statement.setBigDecimal(8, MSRP);
            statement.setString(9, productCode);
    
            int rowsAffected = statement.executeUpdate();
    
            if (rowsAffected > 0) {
                resultMessageArea.setText("Product updated successfully.");
            } else {
                resultMessageArea.setText("Failed to update product. Product code not found.");
            }
    
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultMessageArea.setText("An error occurred while updating the product.");
        }
    }
    

    private void viewProducts() {
    String searchQuery = productNameField.getText();

    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products WHERE productName LIKE '%" + searchQuery + "%'");

        // Opprett et dataobjekt for tabellen
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Product Name"); // Legg til kolonner etter behov

        // Fyll tabellen med data fra resultatsettet
        while (resultSet.next()) {
            String productName = resultSet.getString("productName");
            tableModel.addRow(new Object[]{productName}); // Legg til rad med data
        }

        // Oppdater JTable med det nye datamodellen
        resultTable.setModel(tableModel);

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
                resultMessageArea.setText("Product added successfully.");
            } else {
                resultMessageArea.setText("Failed to add product.");
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
                resultMessageArea.setText("Product deleted successfully.");
            } else {
                resultMessageArea.setText("Product with code " + productCode + " not found.");
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


