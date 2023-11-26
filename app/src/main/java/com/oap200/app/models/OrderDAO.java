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
import java.swing.table.DefaultTableModel;
import java.text.DateFormat;


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
    List<String[]> searchResult = new ArrayList<>();

    try {
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        PreparedStatement statement = myConnection.prepareStatement("SELECT * FROM orders WHERE orderNumber LIKE ? ");
        preparedStatement.setInt(1, "%" + orderNumber + "%");
        ResultSet myRs = preparedStatement.executeQuery();

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
            searchResult.add(order);
        }
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
    }
    return searchResult;
}
    

    // Method for adding orders to the database
   public boolean addOrders(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) {
    Connection myConnection = null;
 
    try {
       DbConnect db = new DbConnect();
       myConnection = db.getConnection();
 

       PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
       
        preparedStatement.setInt(1, orderNumber);
        preparedStatement.setDate(2, orderDate);
        preparedStatement.setDate(3, requiredDate);
        preparedStatement.setDate(4, shippedDate);
        preparedStatement.setString(5, status);
        preparedStatement.setString(6, comments);
        preparedStatement.setInt(7, customerNumber);

        // Validate input data
        if (orderNumber == null || orderNumber.trim().isEmpty() || orderDate == null || orderDate.trim().isEmpty() || requiredDate == null || requiredDate.trim().isEmpty() || shippedDate == null || shippedDate.trim().isEmpty() || status == null || status.trim().isEmpty() || comments == null || comments.trim().isEmpty() || customerNumber == null || customerNumber.trim().isEmpty()) {
            System.out.println("OrderDetails are required.");
            return false;
        }
        
        
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
     
    PreparedStatement preparedStatement = myConnection.prepareStatement("DELETE FROM orders WHERE orderNumber = ?");
    preparedStatement.setString(1, orderNumber);

    int rowsAffected = preparedstatement.executeUpdate();

    return rowsAffected > 0;
} catch (SQLException | ClassNotFoundException e) {
    e.printStackTrace();
    return false;
    }
        
}

// Method for adding orders to the database
   public boolean updateOrders(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) {
    Connection myConnection = null;
 
    try {
       DbConnect db = new DbConnect();
       myConnection = db.getConnection();
       PreparedStatement preparedStatement = myConnection.prepareStatement(
        "UPDATE orders SET orderNumber=?, orderDate=?, requiredDate=?, shippedDate =?, status=?, comments=?, customerNumber=? WHERE orderNumber=?");

       
        preparedStatement.setInt(1, orderNumber);
        preparedStatement.setDate(2, orderDate);
        preparedStatement.setDate(3, requiredDate);
        preparedStatement.setDate(4, shippedDate);
        preparedStatement.setString(5, status);
        preparedStatement.setString(6, comments);
        preparedStatement.setInt(7, customerNumber);

        // Validate input data
        if (orderNumber == null || orderNumber.trim().isEmpty() || orderDate == null || orderDate.trim().isEmpty() || requiredDate == null || requiredDate.trim().isEmpty() || shippedDate == null || shippedDate.trim().isEmpty() || status == null || status.trim().isEmpty() || comments == null || comments.trim().isEmpty() || customerNumber == null || customerNumber.trim().isEmpty()) {
            System.out.println("OrderDetails are required.");
            return false;
        }
        
       int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;
     } catch (SQLException | ClassNotFoundException | DateFormat | Integer e) {
        e.printStackTrace();
        return false;
    }

   }}