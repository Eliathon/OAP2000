//Created by Sebastian
package com.oap200.app.controllers;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.views.ProductManagementPanel;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;
public class ProductController {

    // Metode for å håndtere visning av produkter
        private ProductsDAO productsDAO;
        private ProductManagementPanel productManagementPanel;
    
        public ProductController(ProductsDAO productsDAO, ProductManagementPanel productManagementPanel) {
            this.productsDAO = productsDAO;
            this.productManagementPanel = productManagementPanel;
            
        }
        public void handleSearchProducts(String productName) {
            List<String[]> searchResults = productsDAO.searchProducts(productName);
            productManagementPanel.displayProducts(searchResults);
        }
    
        public void handleViewAllProducts() {
            List<String[]> allProducts = productsDAO.fetchProducts();
            productManagementPanel.displayProducts(allProducts);
        }

        public boolean handleDeleteProduct(String productCode) {
            boolean deletionSuccessful = productsDAO.deleteProduct(productCode);
            if (deletionSuccessful) {
                return true;
            } else {
                // Håndter feil her, for eksempel vis en feilmelding
                return false;
            }
        }

       
        
        public boolean handleAddProduct(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, String quantityInStockText, String buyPriceText, String MSRPText) {
            try {
                int quantityInStock = Integer.parseInt(quantityInStockText);
                BigDecimal buyPrice = new BigDecimal(buyPriceText);
                BigDecimal MSRP = new BigDecimal(MSRPText);
        
                return productsDAO.addProduct(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP);
            } catch (NumberFormatException | ArithmeticException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(productManagementPanel, "Feil ved konvertering av tall.", "Feil", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        
    
        public ProductsDAO getProductsDAO() {
            return this.productsDAO;
        }
    
        public List<String> getProductLines() {
            return ProductsDAO.getProductLines();
        }

        public boolean handleUpdateProduct(String productCode, String newQuantityInStock, String newBuyPrice, String newMSRP) {
            try {
                return productsDAO.updateProduct(productCode, newQuantityInStock, newBuyPrice, newMSRP);
            } catch (NumberFormatException | ArithmeticException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(productManagementPanel, "Feil ved konvertering av tall.", "Feil", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        
        
    
} 