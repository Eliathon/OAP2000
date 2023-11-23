package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder;

public class ProductManagementPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

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

        // Tab 1: View Products
        JPanel viewPanel = new JPanel(new BorderLayout());
        addComponentsToPanelView(viewPanel);
        viewPanel.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Product", null, viewPanel, "Click to view");

        // Tab 2: Add Products
        JPanel addPanel = new JPanel(new BorderLayout());
        addComponentsToPanel(addPanel);
        addPanel.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Products", null, addPanel, "Click to add");

        // Tab 3: Update Products
        JPanel updatePanel = new JPanel(new BorderLayout());
        addComponentsToPanelUpdate(updatePanel);
        updatePanel.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Products", null, updatePanel, "Click to Update");

        // Tab 4: Delete Products
        JPanel deletePanel = new JPanel(new BorderLayout());
        addComponentsToPanelDelete(deletePanel);
        deletePanel.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Products", null, deletePanel, "Click to Delete");

        // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

                // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        initializeTable(tablePanel);

        // Additional Panel
        JPanel additionalPanel = new JPanel();  // Customize as needed


        // Create a vertical box layout for the additional panel
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue()); // Adds vertical glue at the top
        verticalBox.add(tabbedPane);
        verticalBox.add(additionalPanel); // Adds additionalPanel under the tabbed pane
        verticalBox.add(Box.createVerticalGlue()); // Adds vertical glue at the bottom

        // Use JSplitPane to add the additional panel under the tabbed pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, verticalBox, tablePanel);
        splitPane.setDividerLocation(500);  // Adjust the initial divider location

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(splitPane, BorderLayout.CENTER);  // Use the split pane instead of tabbed pane


        getContentPane().add(topPanel, BorderLayout.NORTH);
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
    }

    private void addComponentsToPanel(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(8, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1)); // 8 fields

        // Cloning fields for each tab
        JTextField productCode = new JTextField(10);
        JTextField productName = new JTextField(10);
        JTextField productScale  = new JTextField(10);
        JTextField productVendor = new JTextField(10);
        JTextArea productDescription = new JTextArea();
        JTextField quantityInStock = new JTextField(10);
        JTextField buyPrice = new JTextField(10);
        JTextField MSRP = new JTextField(10);

        labelPanel.add(new JLabel("Product code:"));
        fieldPanel.add(productCode);
        labelPanel.add(new JLabel("Product name:"));
        fieldPanel.add(productName);
        labelPanel.add(new JLabel("Product scale:"));
        fieldPanel.add(productScale);
        labelPanel.add(new JLabel("Product vendor:"));
        fieldPanel.add(productVendor);
        labelPanel.add(new JLabel("Product description:"));
        fieldPanel.add(productDescription);
        labelPanel.add(new JLabel("Quantity in stock:"));
        fieldPanel.add(quantityInStock);
        labelPanel.add(new JLabel("Buy price:"));
        fieldPanel.add(buyPrice);
        labelPanel.add(new JLabel("MSRP:"));
        fieldPanel.add(MSRP);

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        JPanel labelPanel = new JPanel(new GridLayout(2, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(2, 1)); // 8 fields

        JTextField productCode = new JTextField(10);
        JTextField productName = new JTextField(10);

        labelPanel.add(new JLabel("Product code:"));
        fieldPanel.add(productCode);
        labelPanel.add(new JLabel("Product name:"));
        fieldPanel.add(productName);

        panelView.add(labelPanel, BorderLayout.WEST);
        panelView.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelUpdate(JPanel panelUpdate) {
        JPanel labelPanel = new JPanel(new GridLayout(8, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1)); // 8 fields

        // Cloning fields for each tab
        JTextField productCode = new JTextField(10);
        JTextField quantityInStock = new JTextField(10);
        JTextField buyPrice = new JTextField(10);
        JTextField MSRP = new JTextField(10);

        labelPanel.add(new JLabel("Product code:"));
        fieldPanel.add(productCode);
        labelPanel.add(new JLabel("Quantity in stock:"));
        fieldPanel.add(quantityInStock);
        labelPanel.add(new JLabel("Buy price:"));
        fieldPanel.add(buyPrice);
        labelPanel.add(new JLabel("MSRP:"));
        fieldPanel.add(MSRP);

        panelUpdate.add(labelPanel, BorderLayout.WEST);
        panelUpdate.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelDelete(JPanel panelDelete) {
        JPanel labelPanel = new JPanel(new GridLayout(2, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(2, 1)); // 8 fields

        JTextField productCode = new JTextField(10);

        labelPanel.add(new JLabel("Product code:"));
        fieldPanel.add(productCode);

        panelDelete.add(labelPanel, BorderLayout.WEST);
        panelDelete.add(fieldPanel, BorderLayout.CENTER);
    }

    private void initializeTable(JPanel panel) {
        // Example data for the table
        String[] columnNames = {"ID", "Name", "Price", "Price", "Price", "Price"};
        Object[][] data = {
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
                {3, "Product C", 20.0, 20.0, 20.0, 20.0},
        };

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create a JTable with the table model
        JTable table = new JTable(tableModel);

        // Angi ønsket bredde og høyde
        int preferredWidth = 800;
        int preferredHeight = 700;

        // Set the preferred width for the columns
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // ID column
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Name column
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Price column

        // Add the table to a scroll pane and add it to the panel
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManagementPanel frame = new ProductManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Product Management");
            frame.pack();
            frame.setVisible(true);
        });
    }
}

