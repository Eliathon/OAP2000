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

    // Method for fetching all orders and displaying them in a table
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

    // Method for searching for specific orders
    public List<String[]> searchOrders(String orderNumber) {
    List<String[]> ordersResult = new ArrayList<>();

    try {
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        String searchSql = "SELECT * FROM orders WHERE orderNumber = ? OR orderDate = ? OR requiredDate = ? OR shippedDate = ? OR status = ? OR comments = ? OR customerNumber = ?";
        PreparedStatement statement = myConnection.prepareStatement(searchSql);

        for (int i = 1; i <= 7; i++) {
            statement.setString(i, orderNumber); // Assuming searchCriteria is a string for simplicity
        }

        ResultSet myRs = statement.executeQuery();

        while (myRs.next()) {
            String[] order = new String[] {
                    myRs.getString("orderNumber"),
                    myRs.getString("orderDate"),
                    myRs.getString("customerNumber"),
            };
            orders.add(order);
        }
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
    }
    return ordersResult;
}
    

    // Method for adding orders to the database
   public boolean addOrders(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) {
    myConnection myConnection = null;
 
    try {
       DbConnect db = new DbConnect();
       myConnection = db.getConnection();
 

        String addsql = "INSERT INTO orders (orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = myConnection.prepareStatement(addsql);
        statement.setString(1, orderNumber);
        statement.setString(2, orderDate);
        statement.setString(3, requiredDate);
        statement.setString(4, shippedDate);
        statement.setString(5, status);
        statement.setString(6, comments);
        statement.setString(7, customerNumber);

        myConnection.setAutoCommit(false);
       int rowsAffected = statement.executeUpdate();

       if (rowsAffected > 0) {
        myConnection.commit();
        System.out.println("Order has been added successfully!");
       
       } else {
        myConnection.rollback();
        System.out.println("Order has not been added!");
       }

       System.out.println("Rows affected: " + rowsAffected);

       return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }

    finally {
        try {
            if (myConnection!= null) {
                myConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    // Method for deleting orders from the database
    public boolean deleteOrders (String orderNumber) {
        
    try {
    DbConnect db = new DbConnect();
    Connection myConnection = db.getConnection();
     
    PreparedStatement statement = myConnection.prepareStatement("DELETE FROM orders WHERE orderNumber =?");
    statement.setString(1, orderNumber);
               
    int rowsAffected = statement.executeUpdate();
    return rowsAffected > 0;
} catch (SQLException | ClassNotFoundException e) {
    e.printStackTrace();
    return false;
    }
        
}
}
