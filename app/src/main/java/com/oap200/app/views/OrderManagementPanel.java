package com.oap200.app.views;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.controllers.*;
import com.oap200.app.utils.ButtonBuilder;

import java.lang.Integer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
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
    private JTextField fetchLatestOrderNumber;
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

    private OrdersController OrdersController;
    /**
     * Constructs an OrderManagementPanel.
     *
     * @param parentFrame The parent JFrame to which this panel belongs.
     */
    public OrderManagementPanel(JFrame parentFrame) {
       
        initializeFields();
        OrdersController = new OrdersController(new OrderDAO(), this);

        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(OrderManagementPanel.class);
        int x = prefs.getInt(PREF_X, 100); // Default x position
        int y = prefs.getInt(PREF_Y, 100); // Default y position
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
        // Initialize BackButton buttons
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose(); 

        });
        // Initialize LogoutButton button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {

            LoginPanel loginPanel = new LoginPanel();
            loginPanel.setVisible(true);

            SwingUtilities.getWindowAncestor(this).dispose();
         });
         
        JButton viewButton = ButtonBuilder.createViewButton(() -> { });
        JButton addButton = ButtonBuilder.createAddButton(() -> {
        
            System.out.println("Add Button Clicked!");
        });
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> {
           
            System.out.println("Delete Button Clicked!");
        });
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> { 
           
            System.out.println("Update Button Clicked!");
         });
        JButton searchButton = ButtonBuilder.createSearchButton(() -> {
    
        });

        JPanel viewSearchButtonPanel = new JPanel(new FlowLayout());
        viewSearchButtonPanel.add(viewButton);
        viewSearchButtonPanel.add(searchButton);

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Orders
        JPanel panelView = new JPanel(new BorderLayout());
        addComponentsToPanelView(panelView);
        panelView.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("View Orders", null, panelView, "Click to view");

        // Tab 2: Add Orders
        JPanel panelAdd = new JPanel(new BorderLayout());
        addComponentsToPanelAdd(panelAdd);
        panelAdd.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Orders", null, panelAdd, "Click to add");

        // Tab 3: Update Orders
        JPanel panelUpdate = new JPanel(new BorderLayout());
        addComponentsToPanelUpdate(panelUpdate);
        panelUpdate.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Orders", null, panelUpdate, "Click to Update");

        // Tab 4: Delete Orders
        JPanel panelDelete = new JPanel(new BorderLayout());
        addComponentsToPanelDelete(panelDelete);
        panelDelete.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Orders", null, panelDelete, "Click to Delete");

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
        panelView.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> OrdersController.handleViewAllOrders());
        searchButton.addActionListener(e -> searchOrders());
        deleteButton.addActionListener(e -> deleteOrder());
        addButton.addActionListener(e -> addOrder());
        updateButton.addActionListener(e -> {
            int orderNumberToUpdate = Integer.parseInt(updateorderNumber.getText());
            String neworderDate = updateorderDate.getText();
            String newrequiredDate = updaterequiredDate.getText();
            String newshippedDate = updateshippedDate.getText();
            String newstatus = updatestatus.getText();
            String newcomments = updatecomments.getText();
        
            OrdersController.handleUpdateOrders(orderNumberToUpdate, neworderDate, newrequiredDate, newshippedDate, newstatus, newcomments);
            System.out.println(orderNumberToUpdate + neworderDate + newrequiredDate + newshippedDate + newstatus + newcomments);
        });
        loadOrdersLines();
    }

    private void updateOrder() {
    }

    private void addWindowListener(WindowAdapter windowAdapter) {
    }

    private void loadOrdersLines() {
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
        OrdersController.handleSearchOrders(orderNumber);
    }

    private void deleteOrder() {
        String orderNumber = searchNumberField.getText();
        boolean deletionSuccessful = OrdersController.handleDeleteOrders(orderNumber);
        if (deletionSuccessful) {
            JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Deletion completed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addOrder() {
        System.out.println("addOrder() called");
    
        String orderDateText = orderDate.getText();
        String requiredDateText = requiredDate.getText();
        String shippedDateText = shippedDate.getText();
        String statusText = status.getText();
        String commentsText = comments.getText();
        String customerNumberText = customerNumber.getText();
    
        System.out.println("Order Date: " + orderDateText);
        System.out.println("Required Date: " + requiredDateText);
        System.out.println("Shipped Date: " + shippedDateText);
        System.out.println("Status: " + statusText);
        System.out.println("Comments: " + commentsText);
        System.out.println("Customer Number: " + customerNumberText);
    
        if (orderDateText.isEmpty() || commentsText.isEmpty() || customerNumberText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Value for fields (except requiredDate and shippedDate) are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Parse the customerNumberText to an int (if applicable)
        int customerNumber = 0;
        if (!customerNumberText.isEmpty()) {
            try {
                customerNumber = Integer.parseInt(customerNumberText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error parsing Customer Number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    
        // Parsing and handling dates
        Date orderDate = null;
        Date requiredDate = null;
        Date shippedDate = null;
    
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
        try {
            orderDate = dateFormat.parse(orderDateText);
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing Order Date.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if parsing fails
        }
    
        if (!requiredDateText.isEmpty()) {
            try {
                requiredDate = dateFormat.parse(requiredDateText);
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error parsing Required Date.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if parsing fails
            }
        }
    
        if (!shippedDateText.isEmpty()) {
            try {
                shippedDate = dateFormat.parse(shippedDateText);
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error parsing Shipped Date.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if parsing fails
            }
        }
    
        // Ensure all dates are parsed correctly before proceeding
        if (orderDate == null || requiredDate == null || shippedDate == null) {
            JOptionPane.showMessageDialog(this, "Error parsing one or more dates.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        OrderDAO orderDAO = new OrderDAO();
        boolean additionSuccessful = orderDAO.addOrders(
            orderDate.toString(), 
            (requiredDate != null) ? requiredDate.toString() : null, 
            (shippedDate != null) ? shippedDate.toString() : null, 
            statusText, 
            commentsText, 
            customerNumber
        );
    
        if (additionSuccessful) {
            JOptionPane.showMessageDialog(this, "Order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields(); // Clear input fields upon successful addition
        } else {
            JOptionPane.showMessageDialog(this, "Error adding order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper method to clear input fields after successful addition
    private void clearFields() {
        orderNumber.setText("");
        orderDate.setText("");
        requiredDate.setText("");
        shippedDate.setText("");
        status.setText("");
        comments.setText("");
        customerNumber.setText("");
    }
    /**
 * Adds components to the panel for the addition of orders.
 *
 * @param panelAdd The panel to which components are added.
 */
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
        fieldPanel.add(status);
        labelPanel.add(new JLabel("Comments:"));
        fieldPanel.add(comments);
        labelPanel.add(new JLabel("Customer Number:"));
        fieldPanel.add(customerNumber);
    
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }
/**
 * Adds components to the panel for updating orders.
 *
 * @param panelUpdate The panel to which components are added.
 */
    private void addComponentsToPanelUpdate(JPanel panelUpdate) {
        panelUpdate.setLayout(new BorderLayout());

        JPanel inputPanelUpdate = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Order Number:"), gbc);

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
        inputPanelUpdate.add(new JLabel("Costumer Number:"), gbc);
        gbc.gridx = 1;
        updatecustomerNumber = new JTextField(10);
        inputPanelUpdate.add(updatecustomerNumber, gbc);

        // Add this to fill the space in the panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        inputPanelUpdate.add(new JPanel(), gbc);

        panelUpdate.add(inputPanelUpdate, BorderLayout.NORTH);

        // Create the scrollPane with the orderTable
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
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        inputPanel.add(new JLabel("Order Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        searchTextField = new JTextField(10);
        inputPanel.add(searchTextField, gbc);

        panelView.add(inputPanel, BorderLayout.NORTH);

        // Create the scrollPane with the orderTable
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
    }
/**
 * Adds components to the panel for deleting orders.
 *
 * @param panelDelete The panel to which components are added.
 */
    private void addComponentsToPanelDelete(JPanel panelDelete) {
        panelDelete.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        inputPanel.add(new JLabel("Order Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        searchNumberField = new JTextField(10);
        inputPanel.add(searchNumberField, gbc);

        panelDelete.add(inputPanel, BorderLayout.NORTH);

        // Create the scrollPane with the orderTable
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelDelete.add(tableContainer, BorderLayout.CENTER);
    }
/**
 * Displays a list of orders in a table.
 *
 * @param orderList The list of orders to be displayed.
 */
    public void displayOrders(List<String[]> orderList) {
        String[] columnNames = { "Order Number", "Order Date", "Required Date", "Shipped Date", "Status", "Comments", "Customer Number"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : orderList) {
            model.addRow(row);
        }
        ordersTable.setModel(model);

        System.out.println("Display orders: " + Arrays.deepToString(orderList.toArray()));
    }

    /**
 * The main method to start the Order Management application.
 *
 * @param args Command line arguments (not used in this application).
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame panel = new JFrame();
            panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            panel.setTitle("Order Management");
            panel.pack();
            panel.setVisible(true);
        });
    }
}


