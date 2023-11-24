package com.oap200.app.controllers;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.views.ProductManagementPanel;

import java.util.List;
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
        
        public boolean handleAddProduct(String productCode, String productName, String productLine, String productScale,
                                    String productVendor, String productDescription, String quantityInStock,
                                    String buyPrice, String MSRP) {
        return productsDAO.addProduct(productCode, productName, productLine, productScale,
                                       productVendor, productDescription, quantityInStock,
                                       buyPrice, MSRP);
    }
        

    
} 