package com.oap200.app.views;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.controllers.OrdersController;
import com.oap200.app.utils.ButtonBuilder;

import java.lang.Integer;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Panel for managing orders.
 * Extends JPanel to serve as a component within a larger user interface.
 * This class handles the display, addition, updating, and deletion of orders.
 * @author Patrik.
 */
public class OrderManagementPanel extends JPanel {

    private MainFrame mainFrame;

    // Preferences keys for storing window position
    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    // Components for the view
    private JTable ordersTable;
    private JTextField searchTextField;
    private JTextField searchNumberField;

    // Components for adding a product
    private JTextField orderNumber;
    private JTextField orderDate;
    private JTextField requiredDate;
    private JTextField shippedDate;
    private JTextField status;
    private JTextField comments;
    private JTextField customerNumber;

    // Components for updating a product
    private JTextField updateorderNumber;
    private JTextField updateorderDate;
    private JTextField updaterequiredDate;
    private JTextField updateshippedDate;
    private JTextField updatestatus;
    private JTextField updatecomments;
    private JTextField updatecustomerNumber;

    private OrdersController ordersController;

    /**
     * Constructs an OrderManagementPanel.
     *
     * @param parentFrame The parent JFrame to which this panel belongs.
     */
    public OrderManagementPanel(JFrame parentFrame) {
        initializeFields();
        ordersController = new OrdersController(new OrderDAO(), this);
        
        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(OrderManagementPanel.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        // Save window position on close
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
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> { });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {  });
        JButton viewButton = ButtonBuilder.createViewButton(() -> { });
        JButton addButton = ButtonBuilder.createAddButton(() -> addOrder());
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> deleteOrder());
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> updateOrder());
        JButton searchButton = ButtonBuilder.createSearchButton(() -> searchOrders());

        JPanel viewSearchButtonPanel = new JPanel(new FlowLayout());
        viewSearchButtonPanel.add(viewButton);
        viewSearchButtonPanel.add(searchButton);

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Orders
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("View Orders", null, panel1, "Click to view");

        // Tab 2: Add Orders
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanelAdd(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Orders", null, panel2, "Click to add");

        // Tab 3: Update Orders
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanelUpdate(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Orders", null, panel3, "Click to Update");

        // Tab 4: Delete Orders
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanelDelete(panel4);
        panel4.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Orders", null, panel4, "Click to Delete");

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

        add(mainPanel, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Initialize the table
        ordersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        panel1.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> ordersController.handleViewAllOrders());
        searchButton.addActionListener(e -> searchOrders());
        deleteButton.addActionListener(e -> deleteOrder());
        addButton.addActionListener(e -> addOrder());
        updateButton.addActionListener(e -> updateOrder());
        loadOrdersLines();
    }

    private void addWindowListener(WindowAdapter windowAdapter) {
    }

    private void updateOrder() {
        // To be implemented
    }

    private void loadOrdersLines() {
        // To be implemented
    }

    private void initializeFields() {
        System.out.println("Initializing fields...");
        loadOrdersLines();

        searchTextField = new JTextField(10);

        this.orderNumber = new JTextField(10);
        this.orderDate = new JTextField(10);
        this.requiredDate = new JTextField(10);
        this.shippedDate = new JTextField(10);
        this.status = new JTextField(10);
        this.comments = new JTextField(10);
        this.customerNumber = new JTextField(10);
    }

    private void searchOrders() {
        String orderNumber = searchTextField.getText();
        ordersController.handleSearchOrders(orderNumber);
    }

    private void deleteOrder() {
        String orderNumber = searchNumberField.getText();
        boolean deletionSuccessful = ordersController.handleDeleteOrders(orderNumber);
        if (deletionSuccessful) {
            JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Deletion completed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrder() {
        System.out.println("addOrder() called!");

        String orderNumberText = orderNumber.getText();
        String orderDateText = orderDate.getText();
        String requiredDateText = requiredDate.getText();
        String shippedDateText = shippedDate.getText();
        String customerNumberText = customerNumber.getText();

        // Validate Order Number
        try {
            Integer.parseInt(orderNumberText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Order number cannot be empty and must contain numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDate(orderDateText, "yyyy-MM-dd")){
            JOptionPane.showMessageDialog(this, "Order date requires yyyy-MM-dd date format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!requiredDateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Required date requires yyyy-mm-dd date format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!shippedDateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Shipped date requires yyyy-mm-dd date format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Integer.parseInt(customerNumberText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Customer number cannot be empty and must contain numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int orderNumberInt = Integer.parseInt(orderNumberText);
            int customerNumberInt = Integer.parseInt(customerNumberText);

            boolean additionSuccessful = ordersController.handleAddOrder(
                    orderNumberInt, orderDate.getText(),
                    requiredDate.getText(), shippedDate.getText(), status.getText(),
                    comments.getText(), customerNumberInt);

            if (additionSuccessful) {
                JOptionPane.showMessageDialog(this, "Order added successfully.", "Addition completed",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error adding order.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidDate(String orderDateText, String format) {
        // To be implemented
        return false;
    }

    private void addComponentsToPanelAdd(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(9, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(9, 1));

        labelPanel.add(new JLabel("Order Number:"));
        fieldPanel.add(orderNumber);
        labelPanel.add(new JLabel("Order Date:"));
        fieldPanel.add(orderDate);
        labelPanel.add(new JLabel("Required Date:"));
        fieldPanel.add(requiredDate);
        labelPanel.add(new JLabel("Shipped Date:"));
        fieldPanel.add(shippedDate);
        labelPanel.add(new JLabel("Status:"));
        fieldPanel.add(new JComboBox<>(new String[] { "Shipped", "Pending", "Cancelled" }));
        labelPanel.add(new JLabel("Comments:"));
        fieldPanel.add(comments);
        labelPanel.add(new JLabel("Customer Number:"));
        fieldPanel.add(customerNumber);

        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelUpdate(JPanel panelUpdate) {
        panelUpdate.setLayout(new BorderLayout());

        JPanel inputPanelUpdate = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Update Order Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        updateorderNumber = new JTextField(10);
        inputPanelUpdate.add(updateorderNumber, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Order Date:"), gbc);
        gbc.gridx = 1;
        updateorderDate = new JTextField(10);
        inputPanelUpdate.add(updateorderDate, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Required Date:"), gbc);
        gbc.gridx = 1;
        updaterequiredDate = new JTextField(10);
        inputPanelUpdate.add(updaterequiredDate, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Shipped Date:"), gbc);
        gbc.gridx = 1;
        updateshippedDate = new JTextField(10);
        inputPanelUpdate.add(updateshippedDate, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Status:"), gbc);
        gbc.gridx = 1;
        updatestatus = new JTextField(10);
        inputPanelUpdate.add(updatestatus, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Comments:"), gbc);
        gbc.gridx = 1;
        updatecomments = new JTextField(10);
        inputPanelUpdate.add(updatecomments, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Costumer Number:"), gbc);
        gbc.gridx = 1;
        updatecustomerNumber = new JTextField(10);
        inputPanelUpdate.add(updatecustomerNumber, gbc);

        // Add this to fill the space in the panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        inputPanelUpdate.add(new JPanel(), gbc);

        panelUpdate.add(inputPanelUpdate, BorderLayout.NORTH);

        // Create the scrollPane with the employeeTable
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelUpdate.add(tableContainer, BorderLayout.CENTER);
    }
/**
 * Adds components to the panel for viewing orders.
 *
 * @param panelView The panel to which components are added.
 */
private void addComponentsToPanelView(JPanel panelView) {
    // Set layout for the main view panel
    panelView.setLayout(new BorderLayout());

    // Create the input panel for viewing orders
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Add components for viewing Order Number
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.3;
    inputPanel.add(new JLabel("Order Number:"), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.7;
    searchTextField = new JTextField(10);
    inputPanel.add(searchTextField, gbc);

    // Add the input panel to the main view panel
    panelView.add(inputPanel, BorderLayout.NORTH);

    // Create the scrollPane with the ordersTable
    JScrollPane scrollPane = new JScrollPane(ordersTable);

    // Create a container panel for the table to align it to the left
    JPanel tableContainer = new JPanel(new BorderLayout());
    tableContainer.add(scrollPane, BorderLayout.CENTER);

    // Add the table container to the main view panel
    panelView.add(tableContainer, BorderLayout.CENTER);
}
/**
 * Adds components to the panel for deleting orders.
 *
 * @param panelDelete The panel to which components are added.
 */
private void addComponentsToPanelDelete(JPanel panelDelete) {
    // Set layout for the main delete panel
    panelDelete.setLayout(new BorderLayout());

    // Create the input panel for deleting orders
    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Add components for deleting Order Number
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.3;
    inputPanel.add(new JLabel("Order Number:"), gbc);

    gbc.gridx = 1;
    gbc.weightx = 0.7;
    searchNumberField = new JTextField(10);
    inputPanel.add(searchNumberField, gbc);

    // Add the input panel to the main delete panel
    panelDelete.add(inputPanel, BorderLayout.NORTH);

    // Create the scrollPane with the ordersTable
    JScrollPane scrollPane = new JScrollPane(ordersTable);

    // Create a container panel for the table to align it to the left
    JPanel tableContainer = new JPanel(new BorderLayout());
    tableContainer.add(scrollPane, BorderLayout.CENTER);

    // Add the table container to the main delete panel
    panelDelete.add(tableContainer, BorderLayout.CENTER);
}
/**
 * Displays a list of orders in a table.
 *
 * @param orderList The list of orders to be displayed.
 */
public void displayOrders(List<String[]> orderList) {
    String[] columnNames = {"Order Number", "Order Date", "Required Date", "Shipped Date", "Status", "Comments", "Customer Number"};

    // Create a DefaultTableModel with column names and 0 rows
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

    // Add rows to the model based on the orderList
    for (String[] row : orderList) {
        model.addRow(row);
    }

    // Set the model for the ordersTable
    ordersTable.setModel(model);

    // Print the displayed orders to the console
    System.out.println("Display orders: " + Arrays.deepToString(orderList.toArray()));
}

/**
 * The main method to start the Order Management application.
 *
 * @param args Command line arguments (not used in this application).
 */
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        // Create the main JFrame for the application
        JFrame panel = new JFrame();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setTitle("Order Management");

        // Pack the frame to fit its components
        panel.pack();

        // Set the frame to be visible
        panel.setVisible(true);
    });
}
}