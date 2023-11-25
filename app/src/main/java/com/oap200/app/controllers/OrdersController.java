// Created by Patrik

package main.java.com.oap200.app.controllers;

import javax.swing.JTextField;
import javax.xml.bind.ParseConversionEvent;
import javax.swing. JOptionPane;

import java.text.ParseException;
import java.util.List;
import java.util.zip.DataFormatException;

import com.oap200.app.models.OrderDAO;
import com.oap200.app.views.OrderManagementPanel;

public class OrdersController {
    private OrderDAO OrderDAO;
    private OrderManagementPanel OrderManagementPanel;
    
    public OrdersController() {
        this.OrderDAO = new OrderDAO();
        this.OrderManagementPanel = new OrderManagementPanel();
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
    
        List<String[]> ordersResult = OrderDAO.searchOrders(orderNumber);
        OrderManagementPanel.displayOrders(ordersResult);

        String orderNumber = orderManagementPanel.getOrderNumberTextField().getText();
        String orderDate = orderManagementPanel.getOrderDateTextField().getText();
        String requiredDate = orderManagementPanel.getRequiredDateTextField().getText();
        String shippedDate = orderManagementPanel.getShippedDateTextField().getText();
        String status = orderManagementPanel.getStatusTextField().getText();
        String comments = orderManagementPanel.getCommentsTextField().getText();
        String customerNumber = orderManagementPanel.getCustomerNumberTextField().getText();
    }

    //Handle a new order
    public boolean handleAddOrder(String orderNumber, String orderDate, String requiredDate,
    String shippedDate, String status, String comments, String customerNumber) {

        try {
            int orderNumberInt = Integer.parseInt(orderNumber);
            int customerNumberInt = Integer.parseInt(customerNumber);
            ParseConversionEvent orderDate = new ParseConversionEvent(orderDate);
            ParseConversionEvent requiredDate = new ParseConversionEvent(requiredDate);
        // Add a new order to the database
        return OrderDAO.addOrders(orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    } catch (NumberFormatException | DataFormatException | ParseException e) {
        ex.printStackTrace();
        
        JOptionPane.showMessageDialog("OrderManagementPanel", "Invalid input", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
}
