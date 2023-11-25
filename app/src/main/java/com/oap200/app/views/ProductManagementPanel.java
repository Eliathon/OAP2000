package com.oap200.app.views;
import com.oap200.app.models.ProductsDAO;
import com.oap200.app.controllers.ProductController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder;

import java.util.Arrays;
import java.util.List;

public class ProductManagementPanel extends JFrame {



    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    private JTable productsTable;
    private JTextField searchTextField;
    private JTextField searchCodeField;

    private JTextField productCode;
    private JTextField productName;
    private JComboBox<String> productLineComboBox = new JComboBox<>();
    private JTextField productScale;
    private JTextField productVendor;
    private JTextField productDescription;
    private JTextField quantityInStock;
    private JTextField buyPrice;
    private JTextField MSRP;

private ProductController productController;


    

    public ProductManagementPanel() {
        initializeFields();
        productController = new ProductController(new ProductsDAO(), this);
        
        

        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(ProductManagementPanel.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

        // Set up the layout for the frame
        setLayout(new BorderLayout());

        // Initialize ButtonBuilder buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            /* Action for Back Button */});
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            /* Action for Logout Button */});
        JButton viewButton = ButtonBuilder.createViewButton(() -> {
            /* Action for Logout Button */});
            
        JButton addButton = ButtonBuilder.createAddButton(() -> {
            System.out.println("Add Button Clicked!"); });
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> {
            /* Action for Delete Button */});
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> {
            /* Action for Update Button */});
            // Create a JButton for search
        JButton searchButton = ButtonBuilder.createSearchButton(() -> {
            searchProducts();
        });

                // Panel for "View" knappen
        JPanel viewSearchButtonPanel = new JPanel(new FlowLayout());
        viewSearchButtonPanel.add(viewButton);
        viewSearchButtonPanel.add(searchButton);

       


        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Employee
        JPanel panel1 = new JPanel(new BorderLayout());
        
        addComponentsToPanelView(panel1);
        panel1.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("View Products", null, panel1, "Click to view");

        // Tab 2: Add Employee
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanelAdd(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Products", null, panel2, "Click to add");

        // Tab 3: Update Products
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanelUpdate(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Products", null, panel3, "Click to Update");

        // Tab 4: Delete Products
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanelDelete(panel4);
        panel4.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Products", null, panel4, "Click to Delete");

        // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Main panel for the frame
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Initialize the table
        productsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productsTable);
        // Correct this line to add the scrollPane to the CENTER instead of EAST
        panel1.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> productController.handleViewAllProducts());
        searchButton.addActionListener(e -> searchProducts());
        deleteButton.addActionListener(e -> deleteProduct());
        addButton.addActionListener(e -> addProduct());
        loadProductLines();

    }
    

    private void initializeFields() {
        // Last produktlinjene før du initialiserer komponentene
        System.out.println("Initializing fields...");
        loadProductLines();
    
        searchTextField = new JTextField(10);
    
        this.productCode = new JTextField(10);
        this.productName = new JTextField(10);
        this.productScale = new JTextField(10);
        this.productVendor = new JTextField(10);
        this.productDescription = new JTextField(10);
        this.quantityInStock = new JTextField(10);
        this.buyPrice = new JTextField(10);
        this.MSRP = new JTextField(10);
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
    

    
    
    
    
    
    
    
    

    private void searchProducts() {
        String productName = searchTextField.getText();
        productController.handleSearchProducts(productName);
    }

    private void deleteProduct() {
        String productCode = searchCodeField.getText();
        boolean deletionSuccessful = productController.handleDeleteProduct(productCode);
        if (deletionSuccessful) {
            JOptionPane.showMessageDialog(this, "Produktet ble slettet vellykket.", "Sletting fullført", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Feil ved sletting av produkt.", "Feil", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProduct() {
        System.out.println("addProduct() called!");

        boolean additionSuccessful = productController.handleAddProduct(
            productCode.getText(), productName.getText(), (String) productLineComboBox.getSelectedItem(),
            productScale.getText(), productVendor.getText(), productDescription.getText(),
            quantityInStock.getText(), buyPrice.getText(), MSRP.getText());
    
        if (additionSuccessful) {
            JOptionPane.showMessageDialog(this, "Produktet ble lagt til vellykket.", "Legge til fullført", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Feil ved legging til produkt.", "Feil", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    

    private void addComponentsToPanelAdd(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(9, 1)); 
        JPanel fieldPanel = new JPanel(new GridLayout(9, 1)); 

       
        

        labelPanel.add(new JLabel("Product Code:"));
        fieldPanel.add(productCode);
        labelPanel.add(new JLabel("Product Name:"));
        fieldPanel.add(productName);
        labelPanel.add(new JLabel("Product Line:"));
        fieldPanel.add(productLineComboBox); // Endre denne linjen
        labelPanel.add(new JLabel("Product Scale:"));
        fieldPanel.add(productScale);
        labelPanel.add(new JLabel("Product Vendor:"));
        fieldPanel.add(productVendor);
        labelPanel.add(new JLabel("Product Description:"));
        fieldPanel.add(productDescription);
        labelPanel.add(new JLabel("quantityInStock:"));
        fieldPanel.add(quantityInStock);
        labelPanel.add(new JLabel("buyPrice:"));
        fieldPanel.add(buyPrice);
        labelPanel.add(new JLabel("MSRP:"));
        fieldPanel.add(MSRP);
    

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelUpdate(JPanel panelUpdate) {
        panelUpdate.setLayout(new BorderLayout());
    
        JPanel inputPanelUpdate = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Product Code
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Product Code:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField productCodeTextField = new JTextField(10);
        inputPanelUpdate.add(productCodeTextField, gbc);
    
        // Quantity In Stock
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Quantity In Stock:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField quantityInStockTextField = new JTextField(10);
        inputPanelUpdate.add(quantityInStockTextField, gbc);
    
        // Buy Price
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Buy Price:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField buyPriceTextField = new JTextField(10);
        inputPanelUpdate.add(buyPriceTextField, gbc);
    
        // MSRP
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("MSRP:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField msrpTextField = new JTextField(10);
        inputPanelUpdate.add(msrpTextField, gbc);
    
        panelUpdate.add(inputPanelUpdate, BorderLayout.NORTH);
    
        // Now, create the scrollPane with the employeeTable right here:
        JScrollPane scrollPane = new JScrollPane(productsTable);
    
        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelUpdate.add(tableContainer, BorderLayout.CENTER);
    }
    
    private void addComponentsToPanelView(JPanel panelView) {
        // Create a JTextField for search
        
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Last Name:" label
        inputPanel.add(new JLabel("Product name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        searchTextField = new JTextField(10);
        inputPanel.add(searchTextField, gbc);

        

        panelView.add(inputPanel, BorderLayout.NORTH);

        // Now, create the scrollPane with the employeeTable right here:
        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);

        
    }

    private void addComponentsToPanelDelete(JPanel panelDelete) {
        // Create a JTextField for search
        
        panelDelete.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Last Name:" label
        inputPanel.add(new JLabel("Product code:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        searchCodeField = new JTextField(10);
        inputPanel.add(searchCodeField, gbc);

        

        panelDelete.add(inputPanel, BorderLayout.NORTH);

        // Now, create the scrollPane with the employeeTable right here:
        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelDelete.add(tableContainer, BorderLayout.CENTER);
    }

    public void displayProducts(List<String[]> productList) {
        String[] columnNames = { "Product Code", "Product Name", "Product Line", "Product Scale", "Product Vendor", "Product Description", "quantityInStock", "buyPrice", "MSRP" };
    
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : productList) {
            model.addRow(row);
        }
        productsTable.setModel(model);
    
        // Endre utskriften for å bruke Arrays.deepToString
        System.out.println("Displayed products with lines: " + Arrays.deepToString(productList.toArray()));
    }
    
    

    
    
       
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManagementPanel frame = new ProductManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Product management");
            frame.pack();
            frame.setVisible(true);
        });
    }
}

