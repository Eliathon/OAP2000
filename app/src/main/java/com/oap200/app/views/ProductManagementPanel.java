//Created by Sebastian

package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class ProductManagementPanel {

    private JFrame frame;
    private JButton viewButton; // Knapp for å vise produkter
    private JButton addButton; // Knapp for å legge til produkt
    private JButton deleteButton; // Knapp for å slette produkt
    private JButton updateButton;
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

    private JComboBox<String> productLineComboBox;
<<<<<<< HEAD
    private JComboBox<String> filterComboBox; // New ComboBox for filtering by product line
   
=======
    private JComboBox<String> operationDropdown;
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

    public void start() {
        

        frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
        frame.setLayout(new BorderLayout());

        resultTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        tableScrollPane.setPreferredSize(new Dimension(1900, 600));

        resultMessageArea = new JTextArea();
        resultMessageArea.setEditable(false);
        messageScrollPane = new JScrollPane(resultMessageArea);
        messageScrollPane.setPreferredSize(new Dimension(800, 100));

        JPanel menuPanel = new JPanel();
        String[] options = { "Update", "Add", "View", "Delete" };
        JComboBox<String> optionsComboBox = new JComboBox<>(options);
        optionsComboBox.setSelectedIndex(2);

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterComboBox = new JComboBox<>(getProductLines().toArray(new String[0]));
        filterComboBox.insertItemAt("All", 0); // Add an "All" option at the beginning
        filterComboBox.setSelectedIndex(0); // Set "All" as the default filter
        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProducts(); // Trigger a refresh when the filter changes
            }
        });

        
        

        productLineComboBox = new JComboBox<>(getProductLines().toArray(new String[0]));
        productLineComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String selectedProductLine = (String) productLineComboBox.getSelectedItem();
                // Gjør noe med den valgte produktlinjen
            }
        });

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

        // Defining labels for the text inputs and size
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
                repaintTable();  //
            }
        });

        updateButton = new JButton("Update Product");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
                repaintTable();
                
                
                
                
            
            }
        });
        buttonPanel.add(updateButton);

        optionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) optionsComboBox.getSelectedItem();

                productNameField.setText("");
                productCodeField.setText("");
                productScaleField.setText("");
                productVendorField.setText("");
                productDescriptionArea.setText("");
                quantityInStockField.setText("");
                buyPriceField.setText("");
                MSRPField.setText("");

                topPanel.removeAll();
                labelPanel.removeAll();
                fieldPanel.removeAll();
                bottomPanel.removeAll();

                resetTable();
                resetView();
<<<<<<< HEAD
                viewProducts();
                resetFilterPanel();
                
                
              
        
                switch (selectedOption) {
                    case "Update":
                       
                    
                    
        labelPanel.add(productCodeLabel);
        fieldPanel.add(productCodeField);

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
        topPanel.add(viewButton, BorderLayout.WEST);
        bottomPanel.add(messageScrollPane, BorderLayout.NORTH);
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);
    
=======

                switch (selectedOption) {
                    case "Update":

                        labelPanel.add(productCodeLabel);
                        fieldPanel.add(productCodeField);

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
                        topPanel.add(viewButton, BorderLayout.WEST);
                        bottomPanel.add(messageScrollPane, BorderLayout.NORTH);
                        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

                        break;

                    case "Add":
                        topPanel.add(productCodeLabel, BorderLayout.WEST);
                        topPanel.add(productCodeField, BorderLayout.WEST);

                        topPanel.add(productNameLabel, BorderLayout.WEST);
                        topPanel.add(productNameField, BorderLayout.WEST);

                        labelPanel.add(new JLabel("Product Line:"));
                        fieldPanel.add(productLineComboBox);

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

<<<<<<< HEAD
        bottomPanel.add(messageScrollPane, BorderLayout.SOUTH);
        bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);
=======
                        centerPanel.add(labelPanel, BorderLayout.WEST);
                        centerPanel.add(fieldPanel, BorderLayout.WEST);
                        topPanel.add(addButton, BorderLayout.WEST);
                        topPanel.add(menuPanel, BorderLayout.NORTH);
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

                        bottomPanel.add(messageScrollPane, BorderLayout.SOUTH);

                        break;
                    case "View":
<<<<<<< HEAD
                    
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);
=======
                        topPanel.add(productCodeLabel, BorderLayout.WEST);
                        topPanel.add(productCodeField, BorderLayout.WEST);
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

                        topPanel.add(productNameLabel, BorderLayout.WEST);
                        topPanel.add(productNameField, BorderLayout.WEST);
                        topPanel.add(viewButton, BorderLayout.WEST);
                        topPanel.add(menuPanel, BorderLayout.NORTH);

                        bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);

<<<<<<< HEAD

        
        filterPanel.add(new JLabel("Filter by Product Line: "), BorderLayout.WEST);
        filterPanel.add(filterComboBox, BorderLayout.CENTER);

        bottomPanel.add(filterPanel, BorderLayout.NORTH);

      
        bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);

       
        
       
                       
                        break;
                        case "Delete":
        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);
        topPanel.add(deleteButton, BorderLayout.WEST);
        topPanel.add(menuPanel, BorderLayout.NORTH);
        bottomPanel.add(messageScrollPane, BorderLayout.CENTER);
        bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);

        
=======
                        break;
                    case "Delete":
                        topPanel.add(productCodeLabel, BorderLayout.WEST);
                        topPanel.add(productCodeField, BorderLayout.WEST);
                        topPanel.add(deleteButton, BorderLayout.WEST);
                        topPanel.add(menuPanel, BorderLayout.NORTH);
                        bottomPanel.add(messageScrollPane, BorderLayout.SOUTH);
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

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

<<<<<<< HEAD
        viewProducts();  //

        menuPanel.add(optionsComboBox, BorderLayout.EAST);
        topPanel.add(menuPanel, BorderLayout.NORTH);

        topPanel.add(productCodeLabel, BorderLayout.WEST);
        topPanel.add(productCodeField, BorderLayout.WEST);

        topPanel.add(productNameLabel, BorderLayout.WEST);
        topPanel.add(productNameField, BorderLayout.WEST); 
        topPanel.add(viewButton, BorderLayout.WEST);

        filterPanel.add(new JLabel("Filter by Product Line: "), BorderLayout.WEST);
        filterPanel.add(filterComboBox, BorderLayout.CENTER);
        bottomPanel.add(filterPanel, BorderLayout.NORTH);
        bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);
        
        
        
        
        
=======
        menuPanel.add(optionsComboBox, BorderLayout.EAST);
        topPanel.add(menuPanel, BorderLayout.NORTH);
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }
<<<<<<< HEAD
    
    private void repaintTable() {
        //
    }
    
=======

>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
    private void resetView() {
        DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
        tableModel.setRowCount(0); // Tømmer alle rader fra tabellen
        resultMessageArea.setText(""); // Tømmer tekstområdet for meldinger
    }

    private void resetTable() {
        DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
        tableModel.setRowCount(0); // Tømmer alle rader fra tabellen
    }

<<<<<<< HEAD
    
    private void resetFilterPanel() {
        // Clear or reset any components in your filter panel
        // For example:
        productCodeField.setText("");
        productNameField.setText("");
        // Reset other filter components as needed
    
        // Set the filter combobox to "all"
        filterComboBox.setSelectedItem("All");
    }
    
    

    

    

   
=======
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
    private void updateProduct() {
        String productCode = productCodeField.getText();
        BigDecimal buyPrice = null;
        BigDecimal MSRP = null;
        Integer quantityInStock = null;
<<<<<<< HEAD
        
        
    
=======

>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
        try {
            if (!buyPriceField.getText().isEmpty()) {
                buyPrice = new BigDecimal(buyPriceField.getText());
            }

            if (!MSRPField.getText().isEmpty()) {
                MSRP = new BigDecimal(MSRPField.getText());
            }

            if (!quantityInStockField.getText().isEmpty()) {
                quantityInStock = Integer.parseInt(quantityInStockField.getText());
            }

            if (buyPrice != null || MSRP != null || quantityInStock != null) {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root",
                        "");
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE products SET buyPrice = IFNULL(?, buyPrice), MSRP = IFNULL(?, MSRP), quantityInStock = IFNULL(?, quantityInStock) WHERE productCode = ?");

                if (buyPrice != null) {
                    statement.setBigDecimal(1, buyPrice);
                } else {
                    statement.setNull(1, Types.DECIMAL);
                }

                if (MSRP != null) {
                    statement.setBigDecimal(2, MSRP);
                } else {
                    statement.setNull(2, Types.DECIMAL);
                }

                if (quantityInStock != null) {
                    statement.setInt(3, quantityInStock);
                } else {
                    statement.setNull(3, Types.INTEGER);
                }

                statement.setString(4, productCode);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    resultMessageArea.setText("Product updated successfully.");
                } else {
                    resultMessageArea.setText("Failed to update product. Product code not found.");
                }

<<<<<<< HEAD

=======
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
                connection.close();
            } else {
                resultMessageArea.setText("No fields to update.");
            }
            
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            resultMessageArea.setText("An error occurred while updating the product.");
        }
        
    }
<<<<<<< HEAD
    
    private void viewProducts() {
        String searchQuery = productNameField.getText();
        String productCode = productCodeField.getText();
        String selectedProductLine = (String) filterComboBox.getSelectedItem();
    
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
    
            String query = "SELECT * FROM products WHERE 1=1";
    
            if (!searchQuery.isEmpty()) {
                query += " AND productName LIKE '%" + searchQuery + "%'";
            }
    
            if (!productCode.isEmpty()) {
                query += " AND productCode = '" + productCode + "'";
            }
    
            if (!selectedProductLine.equals("All")) {
                query += " AND productLine = '" + selectedProductLine + "'";
            }
    
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
    
=======

    private void viewProducts() {
        String searchQuery = productNameField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root",
                    "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM products WHERE productName LIKE '%" + searchQuery + "%'");

            // Opprett et dataobjekt for tabellen
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Product Code");
            tableModel.addColumn("Product Name");
            tableModel.addColumn("Product Line");
            tableModel.addColumn("Product Scale");
            tableModel.addColumn("Product Vendor");
            tableModel.addColumn("Product Description");
            tableModel.addColumn("quantityInStock");
            tableModel.addColumn("buyPrice");
            tableModel.addColumn("MSRP");
<<<<<<< HEAD
    
=======

            // Fyll tabellen med data fra resultatsettet
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
            while (resultSet.next()) {
                // Fyll tabellen med data fra resultatsettet
                String productCodeResult = resultSet.getString("productCode");
                String productName = resultSet.getString("productName");
                String productLine = resultSet.getString("productLine");
                String productScale = resultSet.getString("productScale");
                String productVendor = resultSet.getString("productVendor");
                String productDescription = resultSet.getString("productDescription");
                String quantityInStock = resultSet.getString("quantityInStock");
                String buyPrice = resultSet.getString("buyPrice");
                String MSRP = resultSet.getString("MSRP");
<<<<<<< HEAD
    
                tableModel.addRow(new Object[]{productCodeResult, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP});
            }
    
            resultTable.setModel(tableModel);
=======

                tableModel.addRow(new Object[] { productCode, productName, productLine, productScale, productVendor,
                        productDescription, quantityInStock, buyPrice, MSRP });
            }

            // Oppdater JTable med det nye datamodellen
            resultTable.setModel(tableModel);

>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
<<<<<<< HEAD
    
    
    
    
    
    
        
=======
>>>>>>> fc3e4bb5eda4c9c11c0ca0962d3fa439174302a1

    private void addProduct() {
        String productCode = productCodeField.getText();
        String productName = productNameField.getText();
        String productLine = (String) productLineComboBox.getSelectedItem(); // Henter valgt produktlinje fra ComboBox
        String productScale = productScaleField.getText();
        String productVendor = productVendorField.getText();
        String productDescription = productDescriptionArea.getText();
        int quantityInStock = Integer.parseInt(quantityInStockField.getText());
        BigDecimal buyPrice = new BigDecimal(buyPriceField.getText());
        BigDecimal MSRP = new BigDecimal(MSRPField.getText());

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root",
                    "");

            // Sjekk om produktlinjen allerede eksisterer
            PreparedStatement checkProductLineStatement = connection
                    .prepareStatement("SELECT * FROM productlines WHERE productLine = ?");
            checkProductLineStatement.setString(1, productLine);
            ResultSet resultSet = checkProductLineStatement.executeQuery();

            if (!resultSet.next()) {
                // Produktlinjen eksisterer ikke, legg den til først
                PreparedStatement addProductLineStatement = connection
                        .prepareStatement("INSERT INTO productlines (productLine) VALUES (?)");
                addProductLineStatement.setString(1, productLine);
                addProductLineStatement.executeUpdate();
            }

            // Legg til produktet
            PreparedStatement addProductStatement = connection
                    .prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            addProductStatement.setString(1, productCode);
            addProductStatement.setString(2, productName);
            addProductStatement.setString(3, productLine);
            addProductStatement.setString(4, productScale);
            addProductStatement.setString(5, productVendor);
            addProductStatement.setString(6, productDescription);
            addProductStatement.setInt(7, quantityInStock);
            addProductStatement.setBigDecimal(8, buyPrice);
            addProductStatement.setBigDecimal(9, MSRP);

            int rowsAffected = addProductStatement.executeUpdate();

            if (rowsAffected > 0) {
                resultMessageArea.setText("Product added successfully.");
            } else {
                resultMessageArea.setText("Failed to add product.");
            }

            connection.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteProduct() {
        String productCode = productCodeField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root",
                    "");
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

    private ArrayList<String> getProductLines() {
        ArrayList<String> productLines = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root",
                    "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT productLine FROM products");

            while (resultSet.next()) {
                String productLine = resultSet.getString("productLine");
                productLines.add(productLine);
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return productLines;
    }

    public static void main(String[] args) {
        ProductManagementPanel productManagementPanel = new ProductManagementPanel();
        productManagementPanel.start();
    }
}