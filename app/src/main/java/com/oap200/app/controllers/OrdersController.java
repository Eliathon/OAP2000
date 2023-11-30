// Created by Patrik

package com.oap200.app.controllers;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.views.OrderManagementPanel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
public class OrdersController {

   // Method for handling the display of products
private OrderDAO orderDAO;
private OrderManagementPanel orderManagementPanel;

public OrdersController(OrderDAO orderDAO, OrderManagementPanel orderManagementPanel) {
    this.orderDAO = orderDAO;
    this.orderManagementPanel = orderManagementPanel;
}
// Method to handle searching for orders by name
public void handleSearchOrders(String orderNumber) {
    List<String[]> searchResult = orderDAO.searchOrders(orderNumber);
    orderManagementPanel.displayOrders(searchResult);
}

// Method to handle displaying all orders
public void handleViewAllOrders() {
    List<String[]> allOrders = orderDAO.fetchOrders();
    orderManagementPanel.displayOrders(allOrders);
}

// Method to handle deleting a product
public boolean handleDeleteOrders(String orderNumber) {
    boolean deletionSuccessful = orderDAO.deleteOrders(orderNumber);
    if (deletionSuccessful) {
        return true;
    } else {
        // Handle errors here, for example, display an error message
        return false;
    }
}

// Method to handle adding a new product
public boolean handleAddOrder(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) {
    DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG);
    
    try {
      
        DateFormat OrderDate = new DateFormat(OrderDate);
        DateFormat RequiredDate = new DateFormat(RequiredDate);
        DateFormat ShippedDate = new DateFormat(ShippedDate);
        
        return orderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    } catch (NumberFormatException | DateFormat  ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(orderManagementPanel, "Error converting numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
}



