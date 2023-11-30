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
    
        // Legg til søkefelt og visknapper i toppanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(viewSearchButtonPanel, BorderLayout.SOUTH);
    
        // Legg til toppanel over tabellen
        panel.add(topPanel, BorderLayout.NORTH);
    
        productsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productsTable);
    
        // Legg til rullefeltet i hovedpanelet
        panel.add(scrollPane, BorderLayout.CENTER);
    
        // Husk å returnere hovedpanelet
        return panel;
    }
    
    private JPanel createAddPanel(JButton addButton) {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Legg til label- og feltpanel som før
        JPanel labelPanel = new JPanel(new GridLayout(8, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1));
    
        labelPanel.add(new JLabel("Product Name:"));
        fieldPanel.add(productName);
    
        // Oppdater størrelsen til Product Name-feltet til å være det samme som i view-panelet
        Dimension preferredSize = productsTable.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(productsTable, "Product Name", false, false, -1, 0)
                .getPreferredSize();
        productName.setPreferredSize(preferredSize);
    
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
    
        // Legg til label- og feltpanel på øst- og sentralposisjonen
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    
        // Legg til knappen i en egen panel for å kunne justere plassering
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // Endret til venstrejustering
        buttonPanel.add(addButton);
    
        // Legg til knappens panel nederst
        panel.add(buttonPanel, BorderLayout.CENTER);
    
        // Legg til søkefelt og visknapper i toppanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.WEST);
        topPanel.add(fieldPanel, BorderLayout.CENTER);
    
        // Legg til topPanel i hovedpanelet
        panel.add(topPanel, BorderLayout.NORTH);
    
        return panel;
    }
    

    private JPanel createUpdatePanel(JButton updateButton) {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Legg til label- og feltpanel som før
        JPanel labelPanel = new JPanel(new GridLayout(4, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(4, 1));
    
        labelPanel.add(new JLabel("Update Product Code:"));
        fieldPanel.add(updateProductCode);
        labelPanel.add(new JLabel("Update Quantity In Stock:"));
        fieldPanel.add(updateQuantityInStock);
        labelPanel.add(new JLabel("Update Buy Price:"));
        fieldPanel.add(updateBuyPrice);
        labelPanel.add(new JLabel("Update MSRP:"));
        fieldPanel.add(updateMSRP);
    
        // Legg til knappen i en egen panel for å kunne justere plassering
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // Endret til venstrejustering
        buttonPanel.add(updateButton);
        // Legg til label- og feltpanel på øst- og sentralposisjonen
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelPanel, BorderLayout.WEST);
        topPanel.add(fieldPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);
    
        return panel;
    }
    

    private JPanel createDeletePanel(JButton deleteButton) {
        JPanel panel = new JPanel(new BorderLayout());
    
        // Legg til label- og feltpanel som før
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
    
        // Legg til delete-knappen i et eget panel for å plassere den til høyre
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(deleteButton);
    
        inputPanel.add(new JLabel("Product Code:"));
        inputPanel.add(searchCodeDeleteField);
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(topPanel, BorderLayout.NORTH);
    
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
            JOptionPane.showMessageDialog(this, "Product: " + productCode + " deleted successfully. The productno longer exists in the system", "Deletion completed",
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
    
            // Find the first empty field
            String missingField = findMissingField();
    
            // Display an error message indicating which field is missing
            JOptionPane.showMessageDialog(this, "Please fill in the " + missingField + " field.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Proceed with adding the product
        boolean additionSuccessful = productController.handleAddProduct(
                productName.getText(), (String) productLineComboBox.getSelectedItem(),
                productScale.getText(), productVendor.getText(), productDescription.getText(),
                quantityInStock.getText(), buyPrice.getText(), MSRP.getText());
    
        if (additionSuccessful) {
            // Retrieve selected product line from JComboBox
            String selectedProductLine = (String) productLineComboBox.getSelectedItem();
    
            // Retrieve the generated product code directly from ProductsDAO
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
    
    // Method to find the first empty field
    private String findMissingField() {
        if (productName.getText().isEmpty()) {
            return "Product Name";
        } else if (productScale.getText().isEmpty()) {
            return "Product Scale";
        } else if (productVendor.getText().isEmpty()) {
            return "Product Vendor";
        } else if (productDescription.getText().isEmpty()) {
            return "Product Description";
        } else if (quantityInStock.getText().isEmpty()) {
            return "Quantity In Stock";
        } else if (buyPrice.getText().isEmpty()) {
            return "Buy Price";
        } else if (MSRP.getText().isEmpty()) {
            return "MSRP";
        } else {
            return "unknown field";
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
