// Created by Patrik

package main.java.com.oap200.app.controllers;

import javax.swing.JTextField;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.views.OrderManagementPanel;

public class OrdersController {
    private OrderDAO OrderDAO;
    public OrderController() {
        this.OrderDAO = new OrderDAO();
        new OrderManagementPanel();
    }

    public void fetchAndDisplayOrders() {
        // Fetch orders from the database
        OrderDAO.fetchOrders().forEach(order -> {
            // Display each order in the view
            // Displaying logic can be added here based on the view requirements
            for (String data : order) {
                System.out.print(data + " ");
            }
            System.out.println(); // Move to the next line for the next order
        });
    }

    public void addOrder(JTextField orderNumberField, JTextField orderDateField, JTextField requiredDateField, JTextField shippedDateField, JTextField statusField, JTextField commentsField, JTextField customerNumberField) {
        // Add a new order to the database
        OrderDAO.addOrders(orderNumberField, orderDateField, requiredDateField, shippedDateField, statusField, commentsField, customerNumberField);
    }

    public boolean addOrder(String orderNumber, String orderDate, String requiredDate,
                            String shippedDate, String status, String comments, String customerNumber) {
        // Add a new order to the database
        return OrderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    }
}
