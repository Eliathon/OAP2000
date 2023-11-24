package com.oap200.app.controllers;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.views.ProductManagementPanel;

import java.util.List;
public class ProductController {
    // Metode for å håndtere visning av produkter
        private static ProductsDAO productsDAO;
        private static ProductManagementPanel productManagementPanel;
    
        public ProductController(ProductsDAO productsDAO, ProductManagementPanel productManagementPanel) {
            this.productsDAO = productsDAO;
            this.productManagementPanel = productManagementPanel;
            
        }
        public static void handleSearchProducts(String productName) {
            List<String[]> searchResults = productsDAO.searchProducts(productName);
            productManagementPanel.displayProducts(searchResults);
        }
        

    
} 