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

import com.oap200.app.utils.DbConnect;

public class ProductsDAO {
    public List<String[]> fetchProducts() {
        System.out.println("View button clicked!");
        List<String[]> products = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM products");

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

    public List<String[]> searchProducts(String productName) {
        List<String[]> searchResults = new ArrayList<>();
    
        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            PreparedStatement preparedStatement = myConnection.prepareStatement("SELECT * FROM products WHERE productName LIKE ?");
            preparedStatement.setString(1, "%" + productName + "%");
            ResultSet myRs = preparedStatement.executeQuery();
            
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

    public boolean deleteProduct(String productCode) {
        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            PreparedStatement preparedStatement = myConnection.prepareStatement("DELETE FROM products WHERE productCode = ?");
            preparedStatement.setString(1, productCode);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returnerer true hvis slettingen var vellykket
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public static List<String> getProductLines() {
        List<String> productLines = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT DISTINCT productLine FROM productlines");

            while (myRs.next()) {
                String productLine = myRs.getString("productLine");
                productLines.add(productLine);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return productLines;
    }
    
    public boolean addProduct(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, int quantityInStock, BigDecimal buyPrice, BigDecimal MSRP) {
       
       
        Connection myConnection = null;
        
        try {
            DbConnect db = new DbConnect();
            myConnection = db.getConnection();
    
            // Forbered INSERT-spørringen
           // String insertQuery = "INSERT INTO products (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, productCode);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, productLine);
            preparedStatement.setString(4, productScale);
            preparedStatement.setString(5, productVendor);
            preparedStatement.setString(6, productDescription);
            preparedStatement.setInt(7, quantityInStock);
            preparedStatement.setBigDecimal(8, buyPrice);
            preparedStatement.setBigDecimal(9, MSRP);
            
            if (productName == null || productName.trim().isEmpty() || productLine == null || productLine.trim().isEmpty()) {
                System.out.println("Product name and product line are required.");
                return false;
            }
            
    
            // Start transaksjon
            myConnection.setAutoCommit(false);
    
            // Utfør INSERT-spørringen
            int rowsAffected = preparedStatement.executeUpdate();
    
            // Bekreft transaksjonen hvis tillegget var vellykket
            if (rowsAffected > 0) {
                myConnection.commit();
                System.out.println("Product added successfully");
            } else {
                // Rull tilbake transaksjonen hvis tillegget mislyktes
                myConnection.rollback();
                System.out.println("Failed to add product");
            }
    
            // Logg antall berørte rader (for feilsøking)
            System.out.println("Rows affected: " + rowsAffected);
    
            return rowsAffected > 0; // Returner true hvis tillegget var vellykket
    
        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            // Logg eventuelle unntak
            ex.printStackTrace();
            return false;
    
        } finally {
            try {
                if (myConnection != null) {
                    // Sett tilbake til autocommit=true
                    myConnection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateProduct(String productCode, String newQuantityInStock, String newBuyPrice, String newMSRP) {
    try {
        int quantityInStock = Integer.parseInt(newQuantityInStock);
        BigDecimal buyPrice = new BigDecimal(newBuyPrice);
        BigDecimal msrp = new BigDecimal(newMSRP);

        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();
        PreparedStatement preparedStatement = myConnection.prepareStatement(
                "UPDATE products SET quantityInStock=?, buyPrice=?, MSRP=? WHERE productCode=?");

        preparedStatement.setInt(1, quantityInStock);
        preparedStatement.setBigDecimal(2, buyPrice);
        preparedStatement.setBigDecimal(3, msrp);
        preparedStatement.setString(4, productCode);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
        ex.printStackTrace();
        return false;
    }
}

    
    
    
    }