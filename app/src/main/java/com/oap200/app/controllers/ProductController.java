/**
 * ProductController is a class that acts as a controller for managing products. It handles interactions
 * between the ProductsDAO (data access object) and the ProductManagementPanel (view).
 *
 *@author Sebastian
 */
package com.oap200.app.controllers;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.views.ProductManagementPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * The ProductController class manages interactions between the ProductsDAO and ProductManagementPanel.
 */
public class ProductController {

    private ProductsDAO productsDAO;
    private ProductManagementPanel productManagementPanel;

    /**
     * Constructor for dependency injection without ProductManagementPanel.
     *
     * @param productsDAO The ProductsDAO instance.
     */
    public ProductController(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    /**
     * Constructor for dependency injection with ProductManagementPanel.
     *
     * @param productsDAO            The ProductsDAO instance.
     * @param productManagementPanel The ProductManagementPanel instance.
     */
    public ProductController(ProductsDAO productsDAO, ProductManagementPanel productManagementPanel) {
        this.productsDAO = productsDAO;
        this.productManagementPanel = productManagementPanel;
    }

    /**
     * Method to handle searching for products by name.
     *
     * @param productName The name of the product to search for.
     */
    public void handleSearchProducts(String productName) {
        List<String[]> searchResults = productsDAO.searchProducts(productName);
        productManagementPanel.displayProducts(searchResults);
    }

    /**
     * Method to handle searching for products by code.
     *
     * @param productCode The code of the product to search for.
     */
    public void handleSearchProductsByCode(String productCode) {
        List<String[]> searchResults = productsDAO.searchProductsByCode(productCode);
        productManagementPanel.displayProducts(searchResults);
    }

    /**
     * Method to handle displaying all products.
     */
    public void handleViewAllProducts() {
        List<String[]> allProducts = productsDAO.fetchProducts();
        productManagementPanel.displayProducts(allProducts);
    }

    /**
     * Method to handle deleting a product.
     *
     * @param productCode The code of the product to delete.
     * @return True if deletion is successful, false otherwise.
     */
    public boolean handleDeleteProduct(String productCode) {
        boolean deletionSuccessful = productsDAO.deleteProduct(productCode);
        if (deletionSuccessful) {
             handleViewAllProducts();
            return true;
        } else {
            // Handle errors here, for example, display an error message
            return false;
        }
    }

    /**
     * Method to handle adding a new product.
     *
     * @param productName        The name of the product.
     * @param productLine        The product line.
     * @param productScale       The product scale.
     * @param productVendor      The product vendor.
     * @param productDescription The product description.
     * @param quantityInStockText The quantity in stock as text.
     * @param buyPriceText       The buy price as text.
     * @param MSRPText           The MSRP as text.
     * @return True if addition is successful, false otherwise.
     */
    public boolean handleAddProduct(String productName, String productLine, String productScale,
                                    String productVendor, String productDescription, String quantityInStockText,
                                    String buyPriceText, String MSRPText) {
                                        handleViewAllProducts();
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
        
            // Call addProduct method
            boolean productAdded = productsDAO.addProduct(productName, productLine, productScale, productVendor,
                    productDescription, quantityInStock, buyPrice, MSRP);
            // Call handleViewAllProducts() regardless of the result of addProduct
            handleViewAllProducts();
            // Return the result of addProduct
            return productAdded;
        } catch (NumberFormatException | ArithmeticException ex) {
            ex.printStackTrace();
            handleConversionError("Quantity In Stock, Buy Price, or MSRP");
            return false;
        }
    }

    /**
     * Method to check if a string is numeric.
     *
     * @param str The string to check.
     * @return True if the string is numeric, false otherwise.
     */
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

    /**
     * Method to handle conversion error and display an error message.
     *
     * @param fieldNames The names of the fields causing the conversion error.
     */
    private void handleConversionError(String fieldNames) {
        JOptionPane.showMessageDialog(productManagementPanel,
                "Error converting " + fieldNames + " to numbers. Make sure that these fields have valid numerical values.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Method to handle updating a product.
     *
     * @param productCode       The code of the product to update.
     * @param newQuantityInStock The new quantity in stock.
     * @param newBuyPrice       The new buy price.
     * @param newMSRP           The new MSRP.
     */
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
                handleViewAllProducts();

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

    /**
     * Method to retrieve the ProductsDAO instance.
     *
     * @return The ProductsDAO instance.
     */
    public ProductsDAO getProductsDAO() {
        return this.productsDAO;
    }

    /**
     * Method to retrieve the list of product lines.
     *
     * @return The list of product lines.
     */
    public List<String> getProductLines() {
        return ProductsDAO.getProductLines();
    }

    /**
     * Method to check and return a list of products with low stock.
     *
     * @return List of products with low stock.
     * @throws ClassNotFoundException If the class is not found.
     * @throws SQLException           If a SQL exception occurs.
     */
    public List<String> checkLowStock() throws ClassNotFoundException, SQLException {
        return productsDAO.getLowStockProducts();
    }
}
