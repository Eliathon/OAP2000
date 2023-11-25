// Created by Patrik

package main.java.com.oap200.app.controllers;

import javax.swing.JTextField;
import javax.swing. JOptionPane;
import java.util.List;
import java.util.zip.DataFormatException;

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

    public void handlesearchOrders(String orderNumber) {
    
        List<String[]> ordersResult = OrderDao.searchOrders(orderNumber);
        OrderManagementPanel.displayOrders(ordersResult);
    }

    //Handle a new order
    public boolean handleAddOrder(String orderNumber, String orderDate, String requiredDate,
    String shippedDate, String status, String comments, String customerNumber) {

        try {
            int orderNumberInt = Integer.parseInt(orderNumber);
            int customerNumberInt = Integer.parseInt(customerNumber);
            Date orderdate = new Date(orderDate);
            Date requireddate = new Date(requiredDate);
        // Add a new order to the database
        return OrderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    } catch (NumberFormatException | DataFormatException  e) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(OrderManagementPanel, "Invalid input", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
}
