//Created by Sebastian
package com.oap200.app.controllers;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.views.ProductManagementPanel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductController {
    

    private ProductsDAO productsDAO;
    private ProductManagementPanel productManagementPanel;

    // Constructor for dependency injection without ProductManagementPanel
    public ProductController(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    // Constructor for dependency injection with ProductManagementPanel
    public ProductController(ProductsDAO productsDAO, ProductManagementPanel productManagementPanel) {
        this.productsDAO = productsDAO;
        this.productManagementPanel = productManagementPanel;
    }

    // Method to handle searching for products by name
    public void handleSearchProducts(String productName) {
        List<String[]> searchResults = productsDAO.searchProducts(productName);
        productManagementPanel.displayProducts(searchResults);
    }

    public void handleSearchProductsByCode(String productCode) {
        List<String[]> searchResults = productsDAO.searchProductsByCode(productCode);
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

    
public boolean handleAddProduct(String productName, String productLine, String productScale,
    String productVendor, String productDescription, String quantityInStockText, String buyPriceText,
    String MSRPText) {
// Validate numeric fields
if (!isNumeric(quantityInStockText) || !isNumeric(buyPriceText) || !isNumeric(MSRPText)) {
    handleConversionError("Quantity In Stock, Buy Price, and MSRP");
    return false;
}

try {
    // Convert quantityInStockText, buyPriceText, and MSRPText to their respective types
    int quantityInStock = Integer.parseInt(quantityInStockText);
    BigDecimal buyPrice = new BigDecimal(buyPriceText);
    BigDecimal MSRP = new BigDecimal(MSRPText);

    return productsDAO.addProduct(productName, productLine, productScale, productVendor,
            productDescription, quantityInStock, buyPrice, MSRP);
} catch (NumberFormatException | ArithmeticException ex) {
    ex.printStackTrace();
    handleConversionError("Quantity In Stock, Buy Price, or MSRP");
    return false;
}
}

// Method to check if a string is numeric
private boolean isNumeric(String str) {
if (str == null || str.trim().isEmpty()) {
    return false;
}
try {
    Double.parseDouble(str);
    return true;
} catch (NumberFormatException e) {
    return false;
}
}

// Method to handle conversion error and display an error message
private void handleConversionError(String fieldNames) {
JOptionPane.showMessageDialog(productManagementPanel,
        "Error converting " + fieldNames + " to numbers. Make sure that these fields have valid numerical values.",
        "Error", JOptionPane.ERROR_MESSAGE);
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
public void handleUpdateProduct(String productCode, String newQuantityInStock, String newBuyPrice, String newMSRP) {
    try {
        // Check if all input values are empty
        if (newQuantityInStock.isEmpty() && newBuyPrice.isEmpty() && newMSRP.isEmpty()) {
            JOptionPane.showMessageDialog(productManagementPanel, "You need to write at least one input.",
                    "Update Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the input values are empty or null, and set them accordingly
        Integer quantityInStock = newQuantityInStock.isEmpty() ? null : Integer.parseInt(newQuantityInStock);
        BigDecimal buyPrice = newBuyPrice.isEmpty() ? null : new BigDecimal(newBuyPrice);
        BigDecimal msrp = newMSRP.isEmpty() ? null : new BigDecimal(newMSRP);

        // Call the updateProduct method in the DAO with the updated values
        productsDAO.updateProduct(productCode, quantityInStock, buyPrice, msrp);

        // Retrieve the updated product details
        List<String[]> updatedProduct = productsDAO.searchProductsByCode(productCode);

        // Check if the product details were retrieved successfully
        if (updatedProduct != null && !updatedProduct.isEmpty()) {
            StringBuilder message = new StringBuilder("Product updated successfully.\n");

            // Check and append details about updated values
            if (quantityInStock != null) {
                message.append("Quantity in Stock updated to: ").append(quantityInStock).append("\n");
            }
            if (buyPrice != null) {
                message.append("Buy Price updated to: ").append(buyPrice).append("\n");
            }
            if (msrp != null) {
                message.append("MSRP updated to: ").append(msrp).append("\n");
            }

            // Display the updated product details in the message
            JOptionPane.showMessageDialog(productManagementPanel, message.toString(), "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            // You may want to refresh the view or take additional actions here if needed.
        } else {
            JOptionPane.showMessageDialog(productManagementPanel, "Failed to retrieve updated product details.", "Update Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException | ArithmeticException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(productManagementPanel, "Error converting numbers. Make sure that quantityInStock, BuyPrice, MSRP have number as value", "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}


    public List<String> checkLowStock() throws ClassNotFoundException, SQLException {
        return productsDAO.getLowStockProducts();
    }
}
