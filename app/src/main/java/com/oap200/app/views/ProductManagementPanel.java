package com.oap200.app.views;

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.controllers.ProductController;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ProductManagementPanel extends JPanel {

    private JTable productsTable;
    private JTextField searchCodeDeleteField;
    private JTextField searchTextField, searchCodeField;
    private JTextField productName, productScale, productVendor, productDescription, quantityInStock,
            buyPrice, MSRP;
    private JTextField updateProductCode, updateQuantityInStock, updateBuyPrice, updateMSRP;
    private JComboBox<String> productLineComboBox;
    private ProductController productController;


    public ProductManagementPanel(JFrame parentFrame) {
        productController = new ProductController(new ProductsDAO(), this);

        initializeFields();
        setLayout(new BorderLayout());

        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            // Get the top-level frame that contains this panel
            Window window = SwingUtilities.getWindowAncestor(ProductManagementPanel.this);
            if (window != null) {
                window.dispose(); // This will close the window
            }
            // Hide or remove this panel
            this.setVisible(false); // or parentFrame.remove(this);
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            // Close the parent frame and open the LoginPanel
            parentFrame.dispose();
            openLoginPanel();
        });
        JButton viewButton = ButtonBuilder.createViewButton(() -> viewProducts());
        JButton addButton = ButtonBuilder.createAddButton(() -> addProduct());
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> deleteProduct());
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> updateProduct());
        JButton searchButton = ButtonBuilder.createSearchButton(() -> {
            searchProducts();
        });
        JButton searchCodeButton = ButtonBuilder.createSearchCodeButton(() ->searchProductsByCode() );
        

        JPanel viewSearchButtonPanel = createViewSearchButtonPanel(viewButton, searchButton, searchCodeButton);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Products", null, createViewPanel(viewSearchButtonPanel), "Click to view");
        tabbedPane.addTab("Add Products", null, createAddPanel(addButton), "Click to add");
        tabbedPane.addTab("Update Products", null, createUpdatePanel(updateButton), "Click to Update");
        tabbedPane.addTab("Delete Products", null, createDeletePanel(deleteButton), "Click to Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initializeFields() {
        searchTextField = new JTextField(10);
        searchCodeField = new JTextField(10);
        searchCodeDeleteField = new JTextField(10);

        productName = new JTextField(10);
        productScale = new JTextField(10);
        productVendor = new JTextField(10);
        productDescription = new JTextField(10);
        quantityInStock = new JTextField(10);
        buyPrice = new JTextField(10);
        MSRP = new JTextField(10);

        updateProductCode = new JTextField(10);
        updateQuantityInStock = new JTextField(10);
        updateBuyPrice = new JTextField(10);
        updateMSRP = new JTextField(10);

        productLineComboBox = new JComboBox<>();
        loadProductLines();
    }

    private void openLoginPanel() {
        // Code to open LoginPanel
        JFrame loginFrame = new JFrame("Login");
        LoginPanel loginPanel = new LoginPanel();
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    private void loadProductLines() {
        if (productController != null) {
            List<String> productLines = productController.getProductLines();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(productLines.toArray(new String[0]));
            productLineComboBox.setModel(model);
            System.out.println("Product lines loaded: " + productLines);
        } else {
            System.err.println("ProductController is null!!");
        }
    }

    private JPanel createViewSearchButtonPanel(JButton viewButton, JButton searchButton, JButton searchCodeButton) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(searchCodeButton);
        return panel;
    }

    private JPanel createViewPanel(JPanel viewSearchButtonPanel) {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Legg til søkefelt for produktkode
        JPanel searchCodePanel = new JPanel(new FlowLayout());
        searchCodePanel.add(new JLabel("Search by product code:"));
        searchCodePanel.add(searchCodeField);
    
        // Legg til søkefelt for produktnavn
        JPanel searchNamePanel = new JPanel(new FlowLayout());
        searchNamePanel.add(new JLabel("Search by product name:"));
        searchNamePanel.add(searchTextField);
    
        // Legg til søkefeltene i hovedpanelet
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
        searchPanel.add(searchCodePanel);
        searchPanel.add(searchNamePanel);
    
        // Legg til søke- og visknappene nederst
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(viewSearchButtonPanel, BorderLayout.SOUTH);
    
        productsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productsTable);
    
        // Legg til rullefeltet i hovedpanelet
        panel.add(scrollPane, BorderLayout.CENTER);
    
        // Husk å returnere hovedpanelet
        return panel;
    }
    
    
    
    

    private JPanel createAddPanel(JButton addButton) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(8, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1));
    
        labelPanel.add(new JLabel("Product Name:"));
        fieldPanel.add(productName);
        labelPanel.add(new JLabel("Product Line:"));
        fieldPanel.add(productLineComboBox);
        labelPanel.add(new JLabel("Product Scale:"));
        fieldPanel.add(productScale);
        labelPanel.add(new JLabel("Product Vendor:"));
        fieldPanel.add(productVendor);
        labelPanel.add(new JLabel("Product Description:"));
        fieldPanel.add(productDescription);
        labelPanel.add(new JLabel("Quantity In Stock:"));
        fieldPanel.add(quantityInStock);
        labelPanel.add(new JLabel("Buy Price:"));
        fieldPanel.add(buyPrice);
        labelPanel.add(new JLabel("MSRP:"));
        fieldPanel.add(MSRP);
    
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    

    private JPanel createUpdatePanel(JButton updateButton) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addTextFieldToPanel(inputPanel, gbc, 0, "Update Product Code:", updateProductCode);
        addTextFieldToPanel(inputPanel, gbc, 1, "Update Quantity In Stock:", updateQuantityInStock);
        addTextFieldToPanel(inputPanel, gbc, 2, "Update Buy Price:", updateBuyPrice);
        addTextFieldToPanel(inputPanel, gbc, 3, "Update MSRP:", updateMSRP);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(updateButton, BorderLayout.SOUTH);
        return panel;
    }

    private void addTextFieldToPanel(JPanel panel, GridBagConstraints gbc, int gridy, String labelText,
            JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 0.3;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(textField, gbc);
    }

    private JPanel createDeletePanel(JButton deleteButton) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanel.add(new JLabel("Product Code:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(searchCodeDeleteField, gbc);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(deleteButton, BorderLayout.SOUTH);
        return panel;
    }

    private void viewProducts() {
        productController.handleViewAllProducts();
    }

    private void searchProducts() {
        String productName = searchTextField.getText();
        productController.handleSearchProducts(productName);
    }

    private void searchProductsByCode() {
        String productCode = searchCodeField.getText();
        productController.handleSearchProductsByCode(productCode);
    }
    

    private void deleteProduct() {
        String productCode = searchCodeDeleteField.getText();

        // Check if the product code is empty
        if (productCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product code.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean deletionSuccessful = productController.handleDeleteProduct(productCode);

        if (deletionSuccessful) {
            JOptionPane.showMessageDialog(this, "Product: " + productCode + " deleted successfully.", "Deletion completed",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting product. Please make sure the product code is valid.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProduct() {
        System.out.println("addProduct() called!");
    
        // Check if any input field is empty
        if (productName.getText().isEmpty() ||
                productScale.getText().isEmpty() || productVendor.getText().isEmpty() ||
                productDescription.getText().isEmpty() || quantityInStock.getText().isEmpty() ||
                buyPrice.getText().isEmpty() || MSRP.getText().isEmpty()) {
    
            // Display an error message
            JOptionPane.showMessageDialog(this, "Please fill in all input fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Proceed with adding the product
        boolean additionSuccessful = productController.handleAddProduct(
            productName.getText(), (String) productLineComboBox.getSelectedItem(),
            productScale.getText(), productVendor.getText(), productDescription.getText(),
            quantityInStock.getText(), buyPrice.getText(), MSRP.getText());
    
        if (additionSuccessful) {
            // Hent valgt produktlinje fra JComboBox
            String selectedProductLine = (String) productLineComboBox.getSelectedItem();
    
            // Hent den genererte produktkoden direkte fra ProductsDAO
            String generatedProductCode = productController.getProductsDAO().getGeneratedProductCode();
    
            String productInfoMessage = "Product added successfully with inputs:\n" +
                    "Product Code: " + generatedProductCode + "\n" +
                    "Product Name: " + productName.getText() + "\n" +
                    "Product Line: " + selectedProductLine + "\n" +
                    "Product Scale: " + productScale.getText() + "\n" +
                    "Product Vendor: " + productVendor.getText() + "\n" +
                    "Product Description: " + productDescription.getText() + "\n" +
                    "Quantity In Stock: " + quantityInStock.getText() + "\n" +
                    "Buy Price: " + buyPrice.getText() + "\n" +
                    "MSRP: " + MSRP.getText();
    
            JOptionPane.showMessageDialog(this, productInfoMessage, "Addition completed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error adding product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void updateProduct() {
        try {
            // Validate inputs
            if (updateProductCode.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product code is required for update.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert numeric fields to String, handling empty strings
            String newQuantityInStock = updateQuantityInStock.getText().isEmpty() ? ""
                    : updateQuantityInStock.getText();
            String newBuyPrice = updateBuyPrice.getText().isEmpty() ? "" : updateBuyPrice.getText();
            String newMSRP = updateMSRP.getText().isEmpty() ? "" : updateMSRP.getText();

            // Call the updateProduct method in the controller with the updated values
            productController.handleUpdateProduct(updateProductCode.getText(), newQuantityInStock, newBuyPrice,
                    newMSRP);

            // You might want to refresh the view or take additional actions here if needed.

        } catch (NumberFormatException | ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayProducts(List<String[]> productList) {
        String[] columnNames = { "Product Code", "Product Name", "Product Line", "Product Scale", "Product Vendor",
                "Product Description", "Quantity In Stock", "Buy Price", "MSRP" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : productList) {
            model.addRow(row);
        }
        productsTable.setModel(model);
    }

    public void setController(ProductController controller) {
        this.productController = controller;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame productFrame = new JFrame("Product Management");
            ProductManagementPanel panel = new ProductManagementPanel(productFrame);
            productFrame.setContentPane(panel);
            productFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            productFrame.pack();
            productFrame.setVisible(true);
        });
    }
}
