// Created by Patrik

package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;  
import com.oap200.app.views.OrderManagementPanel;

import com.oap200.app.utils.DbConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<String[]> fetchOrders() {
        List<String[]> orders = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM orders");

            while (myRs.next()) {
                String[] order = new String[] {
                        myRs.getString("orderNumber"),
                        myRs.getString("orderDate"),
                        myRs.getString("requiredDate"),
                        myRs.getString("shippedDate"),
                        myRs.getString("status"),
                        myRs.getString("comments"),
                        myRs.getString("customerNumber"),
                };
                orders.add(order);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public boolean addOrders() {
        String orderNumber = orderNumberField.getText();
        String orderDate = orderDateField.getText();
        String requiredDate = requiredDateField.getText();
        String shippedDate = shippedDateField.getText();
        String status = statusField.getText();
        String comments = commentsField.getText();
        String customerNumber = customerNumberField.getText();
    
        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            
        String addquery = "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber)" + "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = myConnection.prepareStatement(addquery);

        statement.setString(1, orderNumber);
        statement.setString(2, orderDate);
        statement.setString(3, requiredDate);
        statement.setString(4, shippedDate);
        statement.setString(5, status);
        statement.setString(6, comments);
        statement.setString(7, customerNumber);
    
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new order has been added!");
            return true; // New Order added successfully
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Failed to add order
    }

    public boolean deleteOrders(int orderNumber) {
        if (hasReports(orderNumber)) {
            return false;
        }

        String updateSql = "UPDATE orders SET orderNumber = NULL WHERE orderNumber = ?";
        String deleteSql = "DELETE FROM orders WHERE orderNumber = ?";
        
        try (Connection conn = new DbConnect().getConnection()) {
    
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, orderNumber);
                pstmt.executeUpdate();
            }

            // Delete order
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, orderNumber);
                pstmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
