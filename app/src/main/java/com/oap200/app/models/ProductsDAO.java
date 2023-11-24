package com.oap200.app.models;

import java.sql.Connection;
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
    
    
        // ... annen kode ...
    
        public boolean addProduct(String productCode, String productName, String productLine, String productScale,
                                  String productVendor, String productDescription, String quantityInStock,
                                  String buyPrice, String MSRP) {
            try {
                DbConnect db = new DbConnect();
                Connection myConnection = db.getConnection();
    
                String query = "INSERT INTO products (productCode, productName, productLine, productScale, " +
                        "productVendor, productDescription, quantityInStock, buyPrice, MSRP) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
                try (PreparedStatement preparedStatement = myConnection.prepareStatement(query)) {
                    preparedStatement.setString(1, productCode);
                    preparedStatement.setString(2, productName);
                    preparedStatement.setString(3, productLine);
                    preparedStatement.setString(4, productScale);
                    preparedStatement.setString(5, productVendor);
                    preparedStatement.setString(6, productDescription);
                    preparedStatement.setString(7, quantityInStock);
                    preparedStatement.setString(8, buyPrice);
                    preparedStatement.setString(9, MSRP);
    
                    int rowsAffected = preparedStatement.executeUpdate();
    
                    return rowsAffected > 0;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
    
            return false;
        }
    }
    
    
    
   
    
    

    
    
    
