//Created by Sebastian
package com.oap200.app.controllers;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.utils.DbConnect;
import com.oap200.app.views.ProductManagementPanel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductController {

    // Method for handling the display of products
    private ProductsDAO productsDAO;
    private ProductManagementPanel productManagementPanel;

    public ProductController() {
        this.productsDAO = new ProductsDAO();
        this.productManagementPanel = new ProductManagementPanel();
    }

    public ProductController(ProductsDAO productsDAO, ProductManagementPanel productManagementPanel) {
        this.productsDAO = productsDAO;
        this.productManagementPanel = productManagementPanel;
    }

    // Method to handle searching for products by name
    public void handleSearchProducts(String productName) {
        List<String[]> searchResults = productsDAO.searchProducts(productName);
        productManagementPanel.displayProducts(searchResults);
    }

    // Method to handle displaying all products
    public void handleViewAllProducts() {
        List<String[]> allProducts = productsDAO.fetchProducts();
        productManagementPanel.displayProducts(allProducts);
    }

    // Method to handle deleting a product
    public boolean handleDeleteProduct(String productCode) {
        boolean deletionSuccessful = productsDAO.deleteProduct(productCode);
        if (deletionSuccessful) {
            return true;
        } else {
            // Handle errors here, for example, display an error message
            return false;
        }
    }

    // Method to handle adding a new product
    public boolean handleAddProduct(String productCode, String productName, String productLine, String productScale,
            String productVendor, String productDescription, String quantityInStockText, String buyPriceText,
            String MSRPText) {
        try {
            int quantityInStock = Integer.parseInt(quantityInStockText);
            BigDecimal buyPrice = new BigDecimal(buyPriceText);
            BigDecimal MSRP = new BigDecimal(MSRPText);

            return productsDAO.addProduct(productCode, productName, productLine, productScale, productVendor,
                    productDescription, quantityInStock, buyPrice, MSRP);
        } catch (NumberFormatException | ArithmeticException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(productManagementPanel, "Error converting numbers.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Method to retrieve the ProductsDAO instance
    public ProductsDAO getProductsDAO() {
        return this.productsDAO;
    }

    // Method to retrieve the list of product lines
    public List<String> getProductLines() {
        return ProductsDAO.getProductLines();
    }

    // Method to handle updating a product
    public boolean handleUpdateProduct(String productCode, String newQuantityInStock, String newBuyPrice,
            String newMSRP) {
        try {
            return productsDAO.updateProduct(productCode, newQuantityInStock, newBuyPrice, newMSRP);
        } catch (NumberFormatException | ArithmeticException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(productManagementPanel, "Error converting numbers.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<String> checkLowStock() {
        List<String> lowStockItems = new ArrayList<>();
        String sql = "SELECT productName FROM products WHERE quantityInStock < 200";

        try {
            try (Connection conn = new DbConnect().getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    lowStockItems.add(rs.getString("productName"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Log the exception and return an empty list or handle appropriately
        }
        return lowStockItems;
    }
}
