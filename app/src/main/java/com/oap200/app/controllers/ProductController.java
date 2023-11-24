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

        private void updateProductList() {
            List<String[]> allProducts = productsDAO.fetchProducts();
            productManagementPanel.displayProducts(allProducts);
        }
        
        public boolean handleAddProduct(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, String quantityInStock, String buyPrice, String MSRP) {
            // Logikken for å legge til et produkt i databasen
            // Du kan kalle productsDAO eller andre relevante metoder her
    
            // Returner true hvis tillegg var vellykket, ellers false
            return true; // eller false basert på faktisk tilstand
        }
        
        public ProductsDAO getProductsDAO() {
            return this.productsDAO;
        }
        


        

    
} 