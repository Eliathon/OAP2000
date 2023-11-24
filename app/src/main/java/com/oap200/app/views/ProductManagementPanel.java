package com.oap200.app.views;
import com.oap200.app.models.ProductsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder;
import java.util.List;

public class ProductManagementPanel extends JFrame {
    private JTextField productNameField;


    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    private JTable productsTable;

    public ProductManagementPanel() {
        initializeFields();

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
            /* Action for View Button */});
        JButton addButton = ButtonBuilder.createAddButton(() -> {
            /* Action for Add Button */});
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> {
            /* Action for Delete Button */});
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> {
            /* Action for Update Button */});

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Employee
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Products", null, panel1, "Click to view");

        // Tab 2: Add Employee
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Products", null, panel2, "Click to add");

        // Tab 3: Update Products
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Products", null, panel3, "Click to Update");

        // Tab 4: Delete Products
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel4);
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

        viewButton.addActionListener(e -> {
            String productNameFilter = productNameField.getText(); // Hent produktets navn fra tekstfeltet
            viewProducts(productNameFilter);
        });
    }

        private void viewProducts(String productNameFilter) {
            ProductsDAO productsDAO = new ProductsDAO();
            List<String[]> productList;
        
            if (productNameFilter.isEmpty()) {
                // Hvis produktets navn er tomt, vis hele tabellen
                productList = productsDAO.fetchProducts();
            } else {
                // Ellers, utfør et søk basert på produktets navn
                productList = productsDAO.searchProducts(productNameFilter);
            }
        
            DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
            model.setRowCount(0); // Clear existing data in the table
        
            for (String[] row : productList) {
                model.addRow(row);
            }
        }
        

    private void initializeFields() {
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
    
    }

    private void addComponentsToPanel(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(9, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(9, 1)); // 8 fields

        // Cloning fields for each tab
        JTextField productCode = new JTextField(10);
        JTextField productName = new JTextField(10);
        JTextField productLine = new JTextField(10);
        JTextField productScale = new JTextField(10);
        JTextField productVendor = new JTextField(10);
        JTextField productDescription = new JTextField(10);
        JTextField quantityInStock = new JTextField(10);
        JTextField buyPrice = new JTextField(10);
        JTextField MSRP = new JTextField(10);
        

        labelPanel.add(new JLabel("Product Code:"));
        fieldPanel.add(productCode);
        labelPanel.add(new JLabel("Product Name:"));
        fieldPanel.add(productName);
        labelPanel.add(new JLabel("Product Line:"));
        fieldPanel.add(productLine);
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

    private void addComponentsToPanelView(JPanel panelView) {
        panelView.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Label weight
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        // Adding the "Product Code:" label
        inputPanel.add(new JLabel("Product Code:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField productCodeField = new JTextField(10);
        inputPanel.add(productCodeField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Product Name:" label
        inputPanel.add(new JLabel("Product Name:"), gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        productNameField = new JTextField(10); // Bruk det eksisterende feltet
        inputPanel.add(productNameField, gbc);
    
        panelView.add(inputPanel, BorderLayout.NORTH);
    
        // Now, create the scrollPane with the productsTable right here:
        JScrollPane scrollPane = new JScrollPane(productsTable);
    
        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
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

