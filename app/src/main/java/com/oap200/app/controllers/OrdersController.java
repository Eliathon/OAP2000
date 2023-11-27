// Created by Patrik

package main.java.com.oap200.app.controllers;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.views.OrderManagementPanel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

public class OrdersController {

    private OrderDAO orderDAO;
    private OrderManagementPanel orderManagementPanel;

    public OrdersController(OrderDAO orderDAO, OrderManagementPanel orderManagementPanel) {
        this.orderDAO = orderDAO;
        this.orderManagementPanel = orderManagementPanel;
    }


    public void handleSearchOrder(String orderNumber) throws Exception {
        try {
            List<String[]> searchResult = orderDAO.searchOrder(orderNumber);
            orderManagementPanel.displayOrders(searchResult);
        } catch (Exception ex) {
          
            ex.printStackTrace(); // This prints the exception details to the console
          
            JOptionPane.showMessageDialog(orderManagementPanel, "An error occurred while searching for orders.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleViewAllOrder() throws Exception {
        List<String[]> allOrders = orderDAO.fetchOrders();
        orderManagementPanel.displayOrders(allOrders);
    }

    
    // Method to handle deleting an Order
    public boolean handleDeleteOrder(String orderNumber) {
        boolean deletionSuccessful = orderDAO.deleteOrder(orderNumber);
        if (deletionSuccessful) {
            return true;
        } else {
            // Handle errors here, for example, display an error message
            return false;
        }
    }


        public boolean addOrder(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) {
            try {
                int orderNum = Integer.parseInt(orderNumber);
                int customerNum = Integer.parseInt(customerNumber);
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
                java.util.Date parsedOrderDate = dateFormat.parse(orderDate);
                java.util.Date parsedRequiredDate = dateFormat.parse(requiredDate);
                java.util.Date parsedShippedDate = dateFormat.parse(shippedDate);
    
                java.sql.Date sqlOrderDate = new java.sql.Date(parsedOrderDate.getTime());
                java.sql.Date sqlRequiredDate = new java.sql.Date(parsedRequiredDate.getTime());
                java.sql.Date sqlShippedDate = new java.sql.Date(parsedShippedDate.getTime());
    
                return orderDAO.addOrder(String.valueOf(orderNum), sqlOrderDate.toString(), sqlRequiredDate.toString(), sqlShippedDate.toString(), status, comments, String.valueOf(customerNum));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(orderManagementPanel, "Error converting numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(orderManagementPanel, "Error parsing dates.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(orderManagementPanel, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            return false;
        }
        
    

    // Method for updating an order
    public boolean updateOrder(String neworderNumber, String neworderDate, String newrequiredDate, String newshippedDate, String newstatus, String newcomments, String newcustomerNumber) {
        try {
            // Check if all input values are empty
            if (neworderNumber.isEmpty() && neworderDate.isEmpty() && newrequiredDate.isEmpty() && newshippedDate.isEmpty() && newstatus.isEmpty() && newcomments.isEmpty() && newcustomerNumber.isEmpty()) {
                JOptionPane.showMessageDialog(orderManagementPanel, "You need to write at least one input.",
                        "Update Failed", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Call the updateOrder method in the DAO with the updated values
            boolean updateSuccessful = orderDAO.updateOrder(neworderNumber, neworderDate, newrequiredDate, newshippedDate, newstatus, newcomments, newcustomerNumber);

            // Display a message dialog based on the update result
            if (updateSuccessful) {
                JOptionPane.showMessageDialog(orderManagementPanel, "Order updated successfully.",
                        "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                JOptionPane.showMessageDialog(orderManagementPanel, "Failed to update order.", "Update Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
            return updateSuccessful;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
