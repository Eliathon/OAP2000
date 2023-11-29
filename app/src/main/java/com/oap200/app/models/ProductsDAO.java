//Created by Sebastian
package com.oap200.app.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;
import java.math.BigInteger;

import java.sql.Types;

import com.oap200.app.utils.DbConnect;

public class ProductsDAO {
    // Method to fetch all products from the database
    public List<String[]> fetchProducts() {
        System.out.println("View button clicked!");
        List<String[]> products = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();

            // Execute SELECT query to retrieve all products
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM products");

            // Process the retrieved data and populate the 'products' list
            while (myRs.next()) {
                String[] product = new String[] {
                        myRs.getString("productCode"),
                        myRs.getString("productName"),
                        myRs.getString("productLine"),
                        myRs.getString("productScale"),
                        myRs.getString("productVendor"),
                        myRs.getString("productDescription"),
                        myRs.getString("quantityInStock"),
                        myRs.getString("buyPrice"),
                        myRs.getString("MSRP"),
                };
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    // Method to search for products by name in the database
    public List<String[]> searchProducts(String productName) {
        List<String[]> searchResults = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();

            // Execute prepared statement to search for products with a given name
            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("SELECT * FROM products WHERE productName LIKE ?");
            preparedStatement.setString(1, "%" + productName + "%");
            ResultSet myRs = preparedStatement.executeQuery();

            // Process the retrieved data and populate the 'searchResults' list
            while (myRs.next()) {
                String[] product = new String[] {
                        myRs.getString("productCode"),
                        myRs.getString("productName"),
                        myRs.getString("productLine"),
                        myRs.getString("productScale"),
                        myRs.getString("productVendor"),
                        myRs.getString("productDescription"),
                        myRs.getString("quantityInStock"),
                        myRs.getString("buyPrice"),
                        myRs.getString("MSRP"),
                };
                searchResults.add(product);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }

    public List<String[]> searchProductsByCode(String productCode) {
        List<String[]> searchResults = new ArrayList<>();
    
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
    
            // Execute prepared statement to search for products with a given code
            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("SELECT * FROM products WHERE productCode = ?");
            preparedStatement.setString(1, productCode);
            ResultSet myRs = preparedStatement.executeQuery();
    
            // Process the retrieved data and populate the 'searchResults' list
            while (myRs.next()) {
                String[] product = new String[] {
                        myRs.getString("productCode"),
                        myRs.getString("productName"),
                        myRs.getString("productLine"),
                        myRs.getString("productScale"),
                        myRs.getString("productVendor"),
                        myRs.getString("productDescription"),
                        myRs.getString("quantityInStock"),
                        myRs.getString("buyPrice"),
                        myRs.getString("MSRP"),
                };
                searchResults.add(product);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }
    

    // Method to delete a product from the database
    public boolean deleteProduct(String productCode) {
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();

            // Execute prepared statement to delete a product with a given code
            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("DELETE FROM products WHERE productCode = ?");
            preparedStatement.setString(1, productCode);

            // Get the number of rows affected after the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if deletion was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Method to retrieve distinct product lines from the database
    public static List<String> getProductLines() {
        List<String> productLines = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();

            // Execute SELECT query to retrieve distinct product lines
            ResultSet myRs = myStmt.executeQuery("SELECT DISTINCT productLine FROM productlines");

            // Process the retrieved data and populate the 'productLines' list
            while (myRs.next()) {
                String productLine = myRs.getString("productLine");
                productLines.add(productLine);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return productLines;
    }
    private String generateProductCode(int codeLength) {
        // Definer tillatte tegn for produktkoden (kun sifre)
        String allowedCharacters = "0123456789";
    
        // Legg til sifre i produktkoden basert på lengden
        StringBuilder productCodeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
    
        for (int i = 0; i < codeLength; i++) {
            char randomChar = allowedCharacters.charAt(random.nextInt(allowedCharacters.length()));
            productCodeBuilder.append(randomChar);
        }
    
        return productCodeBuilder.toString();
    }
    
    
    
    
    
    

// Method to check if a product code already exists in the database
private boolean doesProductCodeExist(String productCode) {
    try (Connection myConnection = new DbConnect().getConnection();
         PreparedStatement preparedStatement = myConnection.prepareStatement("SELECT COUNT(*) FROM products WHERE productCode = ?")) {
        preparedStatement.setString(1, productCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}


// Method to add a new product to the database
public boolean addProduct(String productName, String productLine, String productScale,
        String productVendor, String productDescription, int quantityInStockText, BigDecimal buyPriceText,
        BigDecimal MSRPText) {
    Connection myConnection = null;

    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        myConnection = db.getConnection();

        // Generer en unik produktkode med tilpasset metode
        String productCode = generateProductCode(8); // Sett lengden du ønsker

        // Sjekk om produktkoden allerede eksisterer i databasen
        while (doesProductCodeExist(productCode)) {
            productCode = generateProductCode(8); // Generer en ny produktkode hvis den allerede eksisterer
        }

        // Prepare the INSERT query
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, productCode);
        preparedStatement.setString(2, productName);
        preparedStatement.setString(3, productLine);
        preparedStatement.setString(4, productScale);
        preparedStatement.setString(5, productVendor);
        preparedStatement.setString(6, productDescription);
        preparedStatement.setInt(7, quantityInStockText);
        preparedStatement.setBigDecimal(8, buyPriceText);
        preparedStatement.setBigDecimal(9, MSRPText);

        // Start transaction
        myConnection.setAutoCommit(false);

        // Execute the INSERT query
        int rowsAffected = preparedStatement.executeUpdate();

        // Confirm the transaction if the addition was successful
        if (rowsAffected > 0) {
            myConnection.commit();
            System.out.println("Product added successfully");
        } else {
            // Roll back the transaction if the addition failed
            myConnection.rollback();
            System.out.println("Failed to add product");
        }

        // Log the number of affected rows (for troubleshooting)
        System.out.println("Rows affected: " + rowsAffected);

        // Return true if the addition was successful
        return rowsAffected > 0;

    } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
        // Log any exceptions
        ex.printStackTrace();
        return false;

    } finally {
        try {
            if (myConnection != null) {
                // Set back to autocommit=true
                myConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






    // Method to update a product in the database
    public boolean updateProduct(String productCode, Integer newQuantityInStock, BigDecimal newBuyPrice,
            BigDecimal newMSRP) {
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            PreparedStatement preparedStatement = myConnection.prepareStatement(
                    "UPDATE products SET quantityInStock = IFNULL(?, quantityInStock), buyPrice = IFNULL(?, buyPrice), MSRP = IFNULL(?, MSRP) WHERE productCode = ?");

            // Set parameters for the UPDATE query
            if (newQuantityInStock != null) {
                preparedStatement.setInt(1, newQuantityInStock);
            } else {
                preparedStatement.setNull(1, Types.INTEGER);
            }

            if (newBuyPrice != null) {
                preparedStatement.setBigDecimal(2, newBuyPrice);
            } else {
                preparedStatement.setNull(2, Types.DECIMAL);
            }

            if (newMSRP != null) {
                preparedStatement.setBigDecimal(3, newMSRP);
            } else {
                preparedStatement.setNull(3, Types.DECIMAL);
            }

            preparedStatement.setString(4, productCode);

            // Execute the UPDATE query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if the update was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<String> getLowStockProducts() throws SQLException, ClassNotFoundException {
        List<String> lowStockItems = new ArrayList<>();
        String sql = "SELECT productName FROM products WHERE quantityInStock < 200";

        try (Connection conn = new DbConnect().getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lowStockItems.add(rs.getString("productName"));
            }
        }
        return lowStockItems;
    }
}
