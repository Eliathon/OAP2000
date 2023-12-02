package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;  
import com.oap200.app.views.OrderManagementPanel;
import com.oap200.app.controllers.OrdersController; 
import com.oap200.app.utils.DbConnect;

import java.lang.Integer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// Created by Patrik
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
        statement.setString(1, "%" + orderNumber + "%");
        ResultSet myRs = statement.executeQuery();

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
    

public boolean addOrders(int orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, int customerNumber) {
    Connection myConnection = null;

    try {
        DbConnect db = new DbConnect();
        myConnection = db.getConnection();

        // Fetch the highest orderNumber from the orders table
        PreparedStatement getMaxOrderNumber = myConnection.prepareStatement("SELECT MAX(orderNumber) AS OrderNumber FROM orders");
        ResultSet resultSet = getMaxOrderNumber.executeQuery();
        int maxOrderNumber = 0;
        if (resultSet.next()) {
            maxOrderNumber = resultSet.getInt("maxOrderNumber");
        }
        // Increment the maxOrderNumber to get the new orderNumber
        int newOrderNumber = maxOrderNumber + 1;

        java.sql.Date orderDateSQL = java.sql.Date.valueOf(orderDate);
        java.sql.Date requiredDateSQL = java.sql.Date.valueOf(requiredDate);
        java.sql.Date shippedDateSQL = java.sql.Date.valueOf(shippedDate);

        PreparedStatement statement = myConnection.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setInt(1, newOrderNumber);
        statement.setDate(2, orderDateSQL);
        statement.setDate(3, requiredDateSQL);
        statement.setDate(4, shippedDateSQL);
        statement.setString(5, status);
        statement.setString(6, comments);
        statement.setInt(7, customerNumber);

        // Validate input data
        if (orderDate == null || orderDate.trim().isEmpty() || requiredDate == null || requiredDate.trim().isEmpty() || shippedDate == null || shippedDate.trim().isEmpty() || status == null || status.trim().isEmpty() || comments == null || comments.trim().isEmpty()) {
            System.out.println("Order details are required.");
            return false;
        }

        if (customerNumber <= 0) {
            System.out.println("CustomerNumber is required.");
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
    } finally {
        try {
            if (myConnection != null) {
                myConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Method to validate date format
public boolean isValidDate(String dateStr, String dateFormat) {
    DateFormat sdf = new SimpleDateFormat(dateFormat);
    sdf.setLenient(false);

    try {
        sdf.parse(dateStr);
        return true;
    } catch (ParseException e) {
        return false;
    }
}


    // Method for deleting orders from the database
    public boolean deleteOrders (String orderNumber) {
        
    try {
    DbConnect db = new DbConnect();
    Connection myConnection = db.getConnection();
     
    PreparedStatement preparedStatement = myConnection.prepareStatement("DELETE FROM orders WHERE orderNumber = ?");
    preparedStatement.setString(1, orderNumber);

    int rowsAffected = preparedStatement.executeUpdate();

    return rowsAffected > 0;
} catch (SQLException | ClassNotFoundException e) {
    e.printStackTrace();
    return false;
    }
        
}

// Method for updating orders to the database
   public boolean updateOrders(int orderNumberToUpdate, String orderDate, String requiredDate, String shippedDate, String status, String comments, int newcustomerNumber) {
    Connection myConnection = null;
 
    try {
       DbConnect db = new DbConnect();
       myConnection = db.getConnection();

       java.sql.Date orderDateSQL = java.sql.Date.valueOf(orderDate); 
       java.sql.Date requiredDateSQL = java.sql.Date.valueOf(requiredDate); 
       java.sql.Date shippedDateSQL = java.sql.Date.valueOf(shippedDate); 

       PreparedStatement preparedStatement = myConnection.prepareStatement(
        "UPDATE orders SET orderNumber=?, orderDate=?, requiredDate=?, shippedDate =?, status=?, comments=?, customerNumber=? WHERE orderNumber=?");

       
        preparedStatement.setInt(1, orderNumberToUpdate);
        preparedStatement.setDate(2, orderDateSQL);
        preparedStatement.setDate(3, requiredDateSQL);
        preparedStatement.setDate(4, shippedDateSQL);
        preparedStatement.setString(5, status);
        preparedStatement.setString(6, comments);
        preparedStatement.setInt(7, newcustomerNumber);

        // Validate input data
        if (orderDate == null || orderDate.trim().isEmpty() || requiredDate == null || requiredDate.trim().isEmpty() || shippedDate == null || shippedDate.trim().isEmpty() || status == null || status.trim().isEmpty() || comments == null || comments.trim().isEmpty()) {
            System.out.println("OrderDetails are required.");
            return false;
        }

        // Validate input data for int
        if (orderNumberToUpdate <= 0 || newcustomerNumber <= 0) {
            System.out.println("OrderNumber and CustomerNumber are required.");
            return false;
        }
        
       int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
     } catch (SQLException | ClassNotFoundException | ParseException | Integer e) {
        e.printStackTrace();
        return false;
    }

   }}