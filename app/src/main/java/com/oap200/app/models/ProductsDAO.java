//Created by Sebastian
package com.oap200.app.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import com.oap200.app.views.ProductManagementPanel;
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

    // Method to add a new product to the database
    public boolean addProduct(String productCode, String productName, String productLine, String productScale,
            String productVendor, String productDescription, int quantityInStock, BigDecimal buyPrice,
            BigDecimal MSRP) {
        Connection myConnection = null;

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            myConnection = db.getConnection();

            // Prepare the INSERT query
            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, productCode);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, productLine);
            preparedStatement.setString(4, productScale);
            preparedStatement.setString(5, productVendor);
            preparedStatement.setString(6, productDescription);
            preparedStatement.setInt(7, quantityInStock);
            preparedStatement.setBigDecimal(8, buyPrice);
            preparedStatement.setBigDecimal(9, MSRP);

            // Validate input data
            if (productName == null || productName.trim().isEmpty() || productLine == null
                    || productLine.trim().isEmpty()) {
                System.out.println("Product name and product line are required.");
                return false;
            }

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
   // Method to update a product in the database
public boolean updateProduct(String productCode, Integer newQuantityInStock, BigDecimal newBuyPrice, BigDecimal newMSRP) {
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

}
