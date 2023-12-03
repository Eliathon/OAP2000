package com.oap200.app.controllers;


import com.oap200.app.models.OrderDAO;
import com.oap200.app.views.OrderManagementPanel;

import java.lang.Integer;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Controller class responsible for handling operations related to orders, linking the data access layer and the view.
 * 
 * This class manages the interaction between the OrderDAO (data access) and OrderManagementPanel (view) components.
 * 
 * @author Patrik
 */
public class OrdersController {

   
private OrderDAO orderDAO;
private OrderManagementPanel orderManagementPanel;

/**
     * Constructor for OrdersController.
     * 
     * @param orderDAO The data access object for orders.
     * @param orderManagementPanel The panel responsible for displaying and managing orders.
     */
public OrdersController(OrderDAO orderDAO, OrderManagementPanel orderManagementPanel) {
    this.orderDAO = orderDAO;
    this.orderManagementPanel = orderManagementPanel;
}

/**
     * Handles the search for orders by order number.
     * 
     * @param orderNumber The order number to search for.
     */
public void handleSearchOrders(String orderNumber) {
    List<String[]> searchResult = orderDAO.searchOrders(orderNumber);
    orderManagementPanel.displayOrders(searchResult);
}

 /**
     * Handles the display to view all orders.
     */
public void handleViewAllOrders() {
    List<String[]> allOrders = orderDAO.fetchOrders();
    orderManagementPanel.displayOrders(allOrders);
}

/**
     * Handles the deletion of an order.
     * 
     * @param orderNumber The order number to be deleted.
     * @return True if deletion is successful, false otherwise.
     */
public boolean handleDeleteOrders(String orderNumber) {
    boolean deletionSuccessful = orderDAO.deleteOrders(orderNumber);
    if (deletionSuccessful) {
        return true;
    } else {
        return false;
    }
}

/**
     * Handles the addition of a new order.
     * 
     * @param orderNumber The order number.
     * @param orderDate The order date (format: "yyyy-MM-dd").
     * @param requiredDate The required date (format: "yyyy-MM-dd").
     * @param shippedDate The shipped date (format: "yyyy-MM-dd").
     * @param status The order status.
     * @param comments The order comments.
     * @param customerNumber The customer number associated with the order.
     * @return True if addition is successful, false otherwise.
     */

public boolean handleAddOrder(int orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, int customerNumber) {
    try {
        
        return orderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    } catch (NumberFormatException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(orderManagementPanel, "Error Parse Date.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

    /**
     * Handles the update of an order.
     * 
     * @param orderNumberToUpdate The order number to update.
     * @param neworderDate The new order date (format: "yyyy-MM-dd").
     * @param newrequiredDate The new required date (format: "yyyy-MM-dd").
     * @param newshippedDate The new shipped date (format: "yyyy-MM-dd").
     * @param newstatus The new order status.
     * @param newcomments The new order comments.
     * @return True if the update is successful, false otherwise.
     * 
     * @author Patrik and Sebastian
     */
public boolean handleUpdateOrders(int orderNumberToUpdate, String neworderDate, String newrequiredDate,
        String newshippedDate, String newstatus, String newcomments) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date sqlShippedDate = null;

    try {
        if (!newshippedDate.isEmpty()) {
            sqlShippedDate = new java.sql.Date(dateFormat.parse(newshippedDate).getTime());
        }
    } catch (ParseException e) {
        JOptionPane.showMessageDialog(orderManagementPanel, "Error parsing shipped date.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        return false;
    }

    try {
        System.out.println("Updating order...");
        System.out.println("Order Number: " + orderNumberToUpdate);
        System.out.println("New Order Date: " + neworderDate);
        System.out.println("New Required Date: " + newrequiredDate);
        System.out.println("New Shipped Date: " + sqlShippedDate);
        System.out.println("New Status: " + newstatus);
        System.out.println("New Comments: " + newcomments);

        boolean updateSuccessful = orderDAO.updateOrders(orderNumberToUpdate, neworderDate,
                newrequiredDate, (sqlShippedDate != null) ? sqlShippedDate.toString() : null, newstatus, newcomments);

        if (updateSuccessful) {
            JOptionPane.showMessageDialog(orderManagementPanel, "Order updated successfully.", "Update Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(orderManagementPanel, "Error updating order.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return updateSuccessful;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(orderManagementPanel, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        return false;
    }
}


}
