//Created by Patrik
package com.oap200.app.views;

import com.oap200.app.models.OrderDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.oap200.app.utils.ButtonBuilder;
import java.util.Arrays;
import java.util.List;
import com.oap200.app.controllers.OrdersController;

public class OrderManagementPanel extends JPanel {

    private JTable ordersTable;
    private JTextField searchTextField, searchNumberField;
    private JTextField orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber;
    private JTextField updateOrderNumber, updateOrderDate, updateRequiredDate, updateShippedDate, updateStatus,
            updateComments, updateCustomerNumber;
    private OrdersController ordersController;

    public OrderManagementPanel(JFrame parentFrame) {
        ordersController = new OrdersController(new OrderDAO(), this);

        initializeFields();
        setLayout(new BorderLayout());

        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
<<<<<<< HEAD
            // Get the top-level frame that contains this panel
            Window window = SwingUtilities.getWindowAncestor(OrderManagementPanel.this);
            if (window != null) {
                window.dispose(); // This will close the window
            }
=======
            // Hide or remove this panel
            this.setVisible(false); // or parentFrame.remove(this);
>>>>>>> c8a3ea1 (x)
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            // Close the parent frame and open the LoginPanel
            parentFrame.dispose();
            openLoginPanel();
        });
        JButton viewButton = ButtonBuilder.createViewButton(() -> viewOrders());
        JButton addButton = ButtonBuilder.createAddButton(() -> addOrder());
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> deleteOrder());
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> updateOrder());
        JButton searchButton = ButtonBuilder.createSearchButton(() -> searchOrders());

        JPanel viewSearchButtonPanel = createViewSearchButtonPanel(viewButton, searchButton);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Orders", null, createViewPanel(viewSearchButtonPanel), "Click to view");
        tabbedPane.addTab("Add Orders", null, createAddPanel(addButton), "Click to add");
        tabbedPane.addTab("Update Orders", null, createUpdatePanel(updateButton), "Click to Update");
        tabbedPane.addTab("Delete Orders", null, createDeletePanel(deleteButton), "Click to Delete");

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
        searchNumberField = new JTextField(10);

        orderNumber = new JTextField(10);
        orderDate = new JTextField(10);
        requiredDate = new JTextField(10);
        shippedDate = new JTextField(10);
        status = new JTextField(10);
        comments = new JTextField(10);
        customerNumber = new JTextField(10);

        updateOrderNumber = new JTextField(10);
        updateOrderDate = new JTextField(10);
        updateRequiredDate = new JTextField(10);
        updateShippedDate = new JTextField(10);
        updateStatus = new JTextField(10);
        updateComments = new JTextField(10);
        updateCustomerNumber = new JTextField(10);

        ordersTable = new JTable();
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

    private JPanel createViewSearchButtonPanel(JButton viewButton, JButton searchButton) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(viewButton);
        panel.add(searchButton);
        return panel;
    }

    private JPanel createViewPanel(JPanel viewSearchButtonPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAddPanel(JButton addButton) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        inputPanel.add(new JLabel("Order Number:"));
        inputPanel.add(orderNumber);
        inputPanel.add(new JLabel("Order Date:"));
        inputPanel.add(orderDate);
        inputPanel.add(new JLabel("Required Date:"));
        inputPanel.add(requiredDate);
        inputPanel.add(new JLabel("Shipped Date:"));
        inputPanel.add(shippedDate);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(status);
        inputPanel.add(new JLabel("Comments:"));
        inputPanel.add(comments);
        inputPanel.add(new JLabel("Customer Number:"));
        inputPanel.add(customerNumber);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createUpdatePanel(JButton updateButton) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        inputPanel.add(new JLabel("Order Number:"));
        inputPanel.add(updateOrderNumber);
        inputPanel.add(new JLabel("Order Date:"));
        inputPanel.add(updateOrderDate);
        inputPanel.add(new JLabel("Required Date:"));
        inputPanel.add(updateRequiredDate);
        inputPanel.add(new JLabel("Shipped Date:"));
        inputPanel.add(updateShippedDate);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(updateStatus);
        inputPanel.add(new JLabel("Comments:"));
        inputPanel.add(updateComments);
        inputPanel.add(new JLabel("Customer Number:"));
        inputPanel.add(updateCustomerNumber);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(updateButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDeletePanel(JButton deleteButton) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(1, 2));

        inputPanel.add(new JLabel("Order Number:"));
        inputPanel.add(searchNumberField);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.SOUTH);
        return panel;
    }

    private void viewOrders() {
        try {
            ordersController.handleViewAllOrders();
        } catch (Exception e) {
            e.printStackTrace(); // Or handle the exception as needed
            JOptionPane.showMessageDialog(this, "Error viewing orders.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchOrders() {
        try {
            String orderNumber = searchTextField.getText();
            ordersController.handleSearchOrders(orderNumber);
        } catch (Exception e) {
            e.printStackTrace(); // Or handle the exception as needed
            JOptionPane.showMessageDialog(this, "Error searching orders.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        String orderNum = searchNumberField.getText();
        if (orderNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Order Number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean deletionSuccessful = ordersController.handleDeleteOrder(orderNum);
        displayDeletionMessage(deletionSuccessful);
    }

    private void addOrder() {
        // Validate and retrieve data from fields
        // Call ordersController.addOrder with data
        boolean additionSuccessful = ordersController.addOrder(orderNumber.getText(), orderDate.getText(),
                requiredDate.getText(), shippedDate.getText(), status.getText(), comments.getText(),
                customerNumber.getText());
        displayAdditionMessage(additionSuccessful);
    }

    private void updateOrder() {
        // Validate and retrieve data from fields
        // Call ordersController.updateOrder with data
        boolean updateSuccessful = ordersController.updateOrder(updateOrderNumber.getText(), updateOrderDate.getText(),
                updateRequiredDate.getText(), updateShippedDate.getText(), updateStatus.getText(),
                updateComments.getText(), updateCustomerNumber.getText());
        displayUpdateMessage(updateSuccessful);
    }

    // Helper methods to display dialog messages
    private void displayDeletionMessage(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAdditionMessage(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "Order added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error adding order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayUpdateMessage(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "Order updated successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error updating order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayOrders(List<String[]> orderList) {
        String[] columnNames = { "Order Number", "Order Date", "Required Date", "Shipped Date", "Status", "Comments",
                "Customer Number" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : orderList) {
            model.addRow(row);
        }
        ordersTable.setModel(model);

        System.out.println("Display orders: " + Arrays.deepToString(orderList.toArray()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame orderFrame = new JFrame("Order Frame");
            OrderManagementPanel panel = new OrderManagementPanel(orderFrame);
            orderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            orderFrame.setTitle("Order Management");
            orderFrame.pack();
            orderFrame.setVisible(true);
        });
    }
}