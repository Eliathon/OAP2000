package com.oap200.app.views;

/**
 * The ProductManagementPanel class represents a graphical user interface panel
 * dedicated to the management of products. It provides functionality for viewing,
 * searching, adding, updating, and deleting products in a database. This panel is
 * designed to be part of a larger application, offering an organized and user-friendly
 * interface for handling various product-related operations.
 * <p>
 * The panel includes features such as displaying a list of products, searching for
 * products by name or code, adding new products with specified details, updating
 * existing product information, deleting products, and presenting a list of products
 * with low stock levels. The integration with a ProductsDAO class ensures seamless
 * interaction with the underlying database, making it a comprehensive tool for product
 * management within the application.
 * <p>
 * This class is intended to be used as a component within a broader application's
 * graphical interface, contributing to an efficient and intuitive product management
 * workflow.
 *
 * @author Sebastian
 * @version 1.0
 */

import com.oap200.app.models.ProductsDAO;
import com.oap200.app.controllers.ProductController;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing products, including viewing, adding, updating, and deleting products.
 */
public class ProductManagementPanel extends JPanel {

    // Components for managing products
    private JTable productsTable;
    private JTable productsTableAdd;
    private JTable productsTableUpdate;
    private JTable productsTableDelete;
    private JTextField searchCodeDeleteField;
    private JTextField searchTextField, searchCodeField;
    private JTextField productName, productScale, productVendor, productDescription, quantityInStock,
            buyPrice, MSRP;
    private JTextField updateProductCode, updateQuantityInStock, updateBuyPrice, updateMSRP;
    private JComboBox<String> productLineComboBox;
    private ProductController productController;

    /**
     * Constructs a new ProductManagementPanel.
     *
     * @param parentFrame The parent JFrame.
     */
    public ProductManagementPanel(JFrame parentFrame) {
        productController = new ProductController(new ProductsDAO(), this);
        initializeFields();
        setLayout(new BorderLayout());

        // Buttons for navigation and actions
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
        JButton viewButton = ButtonBuilder.createViewButton(this::viewProducts);
        JButton addButton = ButtonBuilder.createAddButton(this::addProduct);
        JButton deleteButton = ButtonBuilder.createDeleteButton(this::deleteProduct);
        JButton updateButton = ButtonBuilder.createUpdateButton(this::updateProduct);
        JButton searchButton = ButtonBuilder.createSearchButton(this::searchProducts);
        JButton searchCodeButton = ButtonBuilder.createSearchCodeButton(this::searchProductsByCode);

        JPanel viewSearchButtonPanel = createViewSearchButtonPanel(viewButton, searchButton, searchCodeButton);
        

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Products", null, createViewPanel(viewSearchButtonPanel), "Click to view");
        tabbedPane.addTab("Add Products", null, createAddPanel(addButton), "Click to add");
        tabbedPane.addTab("Update Products", null, createUpdatePanel(updateButton), "Click to Update");
        tabbedPane.addTab("Delete Products", null, createDeletePanel(deleteButton), "Click to Delete");


    // Legg til ChangeListener
    tabbedPane.addChangeListener(e -> {
        // Sjekk om den aktive fanen er "Add Products"
       if (tabbedPane.getSelectedIndex() == 1) {
            // Hvis ja, kjør viewProducts-metoden
           viewProducts();
        }
         if (tabbedPane.getSelectedIndex() == 2) {
            // Hvis ja, kjør viewProducts-metoden
            viewProducts();
        } if (tabbedPane.getSelectedIndex() == 3) {
            // Hvis ja, kjør viewProducts-metoden
            viewProducts();
        } if (tabbedPane.getSelectedIndex() == 4) {
            // Hvis ja, kjør viewProducts-metoden
            viewProducts();
        }
    });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }
    /**
     * Initializes the text fields and combo box used in the panel.
     */
    private void initializeFields() {
        // Text fields for searching and managing products
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

        // Combo box for selecting product lines
        productLineComboBox = new JComboBox<>();
        loadProductLines();
    }

    /**
     * Opens the login panel in a new JFrame.
     */
    private void openLoginPanel() {
        // Code to open LoginPanel
        JFrame loginFrame = new JFrame("Login");
        LoginPanel loginPanel = new LoginPanel();
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    /**
     * Loads product lines into the product line combo box.
     */
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

    /**
     * Creates a panel with buttons for viewing and searching products.
     *
     * @param viewButton        The button for viewing products.
     * @param searchButton      The button for searching products.
     * @param searchCodeButton  The button for searching products by code.
     * @return A JPanel with the specified buttons.
     */
    private JPanel createViewSearchButtonPanel(JButton viewButton, JButton searchButton, JButton searchCodeButton) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(searchCodeButton);
        return panel;
    }


    /**
     * Creates a panel for viewing products with search fields and buttons.
     *
     * @param viewSearchButtonPanel The panel with view and search buttons.
     * @return A JPanel for viewing products.
     */
    private JPanel createViewPanel(JPanel viewSearchButtonPanel) {
        JPanel panel = new JPanel(new BorderLayout());

        // Add search field for product code
        JPanel searchCodePanel = new JPanel(new FlowLayout());
        searchCodePanel.add(new JLabel("Search by product code:"));
        searchCodePanel.add(searchCodeField);

        // Add search field for product name
        JPanel searchNamePanel = new JPanel(new FlowLayout());
        searchNamePanel.add(new JLabel("Search by product name:"));
        searchNamePanel.add(searchTextField);

        // Add search fields to the main panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
        searchPanel.add(searchCodePanel);
        searchPanel.add(searchNamePanel);

        // Add search field and buttons to the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(viewSearchButtonPanel, BorderLayout.SOUTH);

        // Add top panel above the table
        panel.add(topPanel, BorderLayout.NORTH);

        productsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Add scroll pane to the main panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Remember to return the main panel
        return panel;
    }

    
    /**
     * Creates a panel for adding products with input fields and a button.
     *
     * @param addButton The button for adding a product.
     * @return A JPanel for adding products.
     */
    private JPanel createAddPanel(JButton addButton) {
        JPanel panel = new JPanel(new BorderLayout());
        // Add label and field panel as before
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        // Add labels and corresponding text fields
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productName);

        // Update the size of the Product Name field to match the view panel
        Dimension preferredSize = productsTable.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(productsTable, "Product Name", false, false, -1, 0)
                .getPreferredSize();
        productName.setPreferredSize(preferredSize);

        inputPanel.add(new JLabel("Product Line:"));
        inputPanel.add(productLineComboBox);
        inputPanel.add(new JLabel("Product Scale:"));
        inputPanel.add(productScale);
        inputPanel.add(new JLabel("Product Vendor:"));
        inputPanel.add(productVendor);
        inputPanel.add(new JLabel("Product Description:"));
        inputPanel.add(productDescription);
        inputPanel.add(new JLabel("Quantity In Stock:"));
        inputPanel.add(quantityInStock);
        inputPanel.add(new JLabel("Buy Price:"));
        inputPanel.add(buyPrice);
        inputPanel.add(new JLabel("MSRP:"));
        inputPanel.add(MSRP);

        // Add label and field panel to the west and center positions
        panel.add(inputPanel, BorderLayout.WEST);
        

        // Add the button to a separate panel for positioning adjustment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        
       

        // Add the button's panel at the center
        panel.add(buttonPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.WEST);
        topPanel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(topPanel, BorderLayout.NORTH);

        productsTableAdd = new JTable();
        JScrollPane scrollPaneAdd = new JScrollPane(productsTableAdd);

        // Add scroll pane to the main panel
        panel.add(scrollPaneAdd, BorderLayout.CENTER);

        // Add the top panel to the main panel
        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }

    /**
     * Creates a panel for updating products with input fields and a button.
     *
     * @param updateButton The button for updating a product.
     * @return A JPanel for updating products.
     */
    private JPanel createUpdatePanel(JButton updateButton) {
        JPanel panel = new JPanel(new BorderLayout());

        // Add label and field panel as before
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        // Add labels and corresponding text fields for updating
        inputPanel.add(new JLabel("Update Product Code:"));
        inputPanel.add(updateProductCode);
        inputPanel.add(new JLabel("Update Quantity In Stock:"));
        inputPanel.add(updateQuantityInStock);
        inputPanel.add(new JLabel("Update Buy Price:"));
        inputPanel.add(updateBuyPrice);
        inputPanel.add(new JLabel("Update MSRP:"));
        inputPanel.add(updateMSRP);

        // Add the button to a separate panel for positioning adjustment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(updateButton);

        productsTableUpdate = new JTable();
        JScrollPane scrollPaneUpdate = new JScrollPane(productsTableUpdate);

        // Add scroll pane to the main panel
        panel.add(scrollPaneUpdate, BorderLayout.CENTER);

        // Add label and field panel to the west and center positions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }

    /**
     * Creates a panel for deleting products with an input field and a button.
     *
     * @param deleteButton The button for deleting a product.
     * @return A JPanel for deleting products.
     */
    private JPanel createDeletePanel(JButton deleteButton) {
        JPanel panel = new JPanel(new BorderLayout());

        // Add label and field panel as before
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));

        // Add the delete button to a separate panel for right alignment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(deleteButton);

         productsTableDelete = new JTable();
        JScrollPane scrollPaneDelete = new JScrollPane(productsTableDelete);

        // Add scroll pane to the main panel
        panel.add(scrollPaneDelete, BorderLayout.CENTER);

        // Add input field for product code
        inputPanel.add(new JLabel("Product Code:"));
        inputPanel.add(searchCodeDeleteField);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }
    /**
     * Displays all products using the product controller.
     */
    private void viewProducts() {
        productController.handleViewAllProducts();
    }

    /**
     * Searches for products by name using the product controller.
     */
    private void searchProducts() {
        String productName = searchTextField.getText();
        productController.handleSearchProducts(productName);
    }

    /**
     * Searches for products by code using the product controller.
     */
    private void searchProductsByCode() {
        String productCode = searchCodeField.getText();
        productController.handleSearchProductsByCode(productCode);
    }

    /**
     * Deletes a product using the product controller.
     */
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
            JOptionPane.showMessageDialog(this, "Product: " + productCode + " deleted successfully. The product no longer exists in the system", "Deletion completed",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting product. Please make sure the product code is valid.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a product using the product controller.
     */
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

            // Create a message with details of the added product
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

            // Display a success message with product details
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

    /**
     * Updates a product using the product controller.
     */
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

    /**
     * Displays a list of products in the table.
     *
     * @param productList The list of products to be displayed.
     */
    public void displayProducts(List<String[]> productList) {
        String[] columnNames = { "Product Code", "Product Name", "Product Line", "Product Scale", "Product Vendor",
                "Product Description", "Quantity In Stock", "Buy Price", "MSRP" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : productList) {
            model.addRow(row);
        }
        productsTable.setModel(model);
        productsTableAdd.setModel(model);
        productsTableUpdate.setModel(model);
        productsTableDelete.setModel(model);
    }

    /**
     * Sets the controller for this panel.
     *
     * @param controller The ProductController to be set.
     */
    public void setController(ProductController controller) {
        this.productController = controller;
    }

    /**
     * The main method to run the application.
     *
     * @param args Command-line arguments.
     */
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
