// Created by Patrik

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
public boolean handleAddOrder(int orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, int customerNumber) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    try {
        
       
        Date parsedOrderDate = dateFormat.parse(orderDate);
        Date parsedRequiredDate = dateFormat.parse(requiredDate);
        Date parsedShippedDate = dateFormat.parse(shippedDate);

        Date sqlOrderDate = new java.sql.Date(parsedOrderDate.getTime());
        Date sqlRequiredDate = new java.sql.Date(parsedRequiredDate.getTime());
        Date sqlShippedDate = new java.sql.Date(parsedShippedDate.getTime());

        
        return orderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    } catch (NumberFormatException | ParseException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(orderManagementPanel, "Error Parse Date.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

    


    public boolean handleUpdateOrders(int orderNumberToUpdate, String neworderDate, String newrequiredDate,
    String newshippedDate, String newstatus, String newcomments, int newcustomerNumber) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date sqlShippedDate = null;

    try {
    sqlShippedDate = new java.sql.Date(dateFormat.parse(newshippedDate).getTime());
    } catch (ParseException e) {
    JOptionPane.showMessageDialog(orderManagementPanel, "Error parsing shipped date.", "Error", JOptionPane.ERROR_MESSAGE);
    return false;
    }

    try {
    boolean updateSuccessful = orderDAO.updateOrders(orderNumberToUpdate, neworderDate,
    newrequiredDate, sqlShippedDate.toString(), newstatus, newcomments, newcustomerNumber);

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


