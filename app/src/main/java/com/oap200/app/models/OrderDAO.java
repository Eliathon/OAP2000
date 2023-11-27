// Created by Patrik


package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    // Method for fetching all orders from database and viewing them
    public List<String[]> fetchOrders() throws Exception {
        List<String[]> orders = new ArrayList<>();

        try (DbConnect db = new DbConnect();
             Connection myConnection = db.getConnection();
             PreparedStatement statement = myConnection.prepareStatement("SELECT * FROM orders");
             ResultSet myRs = statement.executeQuery()) {

            while (myRs.next()) {
                String[] order = new String[]{
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
    
    // Method for searching for an order by orderNumber
    public List<String[]> searchOrder(String orderNumber) throws Exception {
        List<String[]> searchResult = new ArrayList<>();

        try (DbConnect db = new DbConnect();
             Connection myConnection = db.getConnection();
             PreparedStatement statement = myConnection.prepareStatement("SELECT * FROM orders WHERE orderNumber LIKE ?")) {

            statement.setString(1, "%" + orderNumber + "%");
            try (ResultSet myRs = statement.executeQuery()) {
                while (myRs.next()) {
                    String[] order = new String[]{
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
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResult;
    }

    // Method for adding order
    public boolean addOrder(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) throws Exception {
        try (DbConnect db = new DbConnect();
             Connection myConnection = db.getConnection();
             PreparedStatement preparedStatement = myConnection.prepareStatement("INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, orderNumber);
            preparedStatement.setString(2, orderDate);
            preparedStatement.setString(3, requiredDate);
            preparedStatement.setString(4, shippedDate);
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, comments);
            preparedStatement.setString(7, customerNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

  // Method to delete an order from the database
  public boolean deleteOrder(String orderNumber) {
    try {
        // Establish a database connection
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // Execute prepared statement to delete a product with a given code
        PreparedStatement preparedStatement = myConnection
                .prepareStatement("DELETE FROM orders WHERE orderNumber = ?");
        preparedStatement.setString(1, orderNumber);

        // Get the number of rows affected after the deletion
        int rowsAffected = preparedStatement.executeUpdate();

        // Return true if deletion was successful
        return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
        return false;
    }
}
    // Method for updating an order
    public boolean updateOrder(String orderNumber, String orderDate, String requiredDate, String shippedDate, String status, String comments, String customerNumber) throws Exception {
        try (DbConnect db = new DbConnect();
             Connection myConnection = db.getConnection();
             PreparedStatement preparedStatement = myConnection.prepareStatement(
                     "UPDATE orders SET orderDate=?, requiredDate=?, shippedDate=?, status=?, comments=?, customerNumber=? WHERE orderNumber=?")) {

            preparedStatement.setString(1, orderDate);
            preparedStatement.setString(2, requiredDate);
            preparedStatement.setString(3, shippedDate);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, comments);
            preparedStatement.setString(6, customerNumber);
            preparedStatement.setString(7, orderNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
