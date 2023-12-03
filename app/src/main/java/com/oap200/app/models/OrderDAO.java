package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;  
import com.oap200.app.views.OrderManagementPanel;
import com.oap200.app.controllers.OrdersController; 
import com.oap200.app.utils.DbConnect;

import java.lang.Integer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Data Access Object for handling operations related to orders in the database.
 * @author Patrik.
 */
public class OrderDAO {

    /**
     * Fetches all orders from the database and returns them as a list of string arrays.
     *
     * @return List of string arrays representing orders.
     */
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

     /**
     * Searches for specific orders in the database based on the provided order number.
     *
     * @param orderNumber The order number to search for.
     * @return List of string arrays representing search results.
     */
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

public int fetchLatestOrderNumber() {
    try {
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        Statement statement = myConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT MAX(orderNumber) AS MaxOrderNumber FROM orders");

        if (resultSet.next()) {
            return resultSet.getInt("MaxOrderNumber");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return 0; // Return 0 if there's an issue or no orders exist yet
}
     /**
     * Adds a new order to the database.
     * @param orderNumber    The order number.
     * @param orderDate      The order date.
     * @param requiredDate   The required date.
     * @param shippedDate    The shipped date.
     * @param status         The order status.
     * @param comments       The order comments.
     * @param customerNumber The customer number.
     * @return True if the order is added successfully, false otherwise.
     */

    public boolean addOrders(String orderDate, String requiredDate, String shippedDate, String status, String comments, int customerNumber) {
    Connection myConnection = null;
    
    String dateString = "yyyy-mm-dd"; // Replace this with your date string
    try {
        Date parsedDate = java.sql.Date.valueOf(dateString);
        // Use the parsedDate
    } catch (IllegalArgumentException e) {
        // Handle the exception (e.g., display an error message)
        e.printStackTrace();
    }
    try {
        DbConnect db = new DbConnect();
        myConnection = db.getConnection();

        List<String> insertColumns = new ArrayList<>();
        List<Object> insertValues = new ArrayList<>();

        // Validate and convert orderDate
        if (orderDate != null && !orderDate.trim().isEmpty()) {
            insertColumns.add("orderDate");
            insertValues.add(java.sql.Date.valueOf(orderDate));
        }

        // Validate and convert requiredDate
        if (requiredDate != null && !requiredDate.trim().isEmpty()) {
            insertColumns.add("requiredDate");
            insertValues.add(java.sql.Date.valueOf(requiredDate));
        }

        // Validate and convert shippedDate
        if (shippedDate != null && !shippedDate.trim().isEmpty()) {
            insertColumns.add("shippedDate");
            insertValues.add(java.sql.Date.valueOf(shippedDate));
        }

        // Validate and convert status
        if (status != null && !status.trim().isEmpty()) {
            insertColumns.add("status");
            insertValues.add(status);
        }

        // Validate and convert comments
        if (comments != null && !comments.trim().isEmpty()) {
            insertColumns.add("comments");
            insertValues.add(comments);
        }

        // Add customer number
        insertColumns.add("customerNumber");
        insertValues.add(customerNumber);

        // Build the dynamic insert SQL statement
        StringBuilder insertSql = new StringBuilder("INSERT INTO orders (");
        insertSql.append(String.join(", ", insertColumns));
        insertSql.append(") VALUES (");
        insertSql.append(String.join(", ", Collections.nCopies(insertColumns.size(), "?")));
        insertSql.append(")");

        // Prepare the statement
        PreparedStatement preparedStatement = myConnection.prepareStatement(insertSql.toString());

        // Set parameter values
        for (int i = 0; i < insertValues.size(); i++) {
            preparedStatement.setObject(i + 1, insertValues.get(i));
        }

        // Execute the insertion
        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}
/**
     * Validates the date format based on the provided date string and format.
     *
     * @param dateStr    The date string to validate.
     * @param dateFormat The expected date format.
     * @return True if the date is valid, false otherwise.
     */
public boolean isValidDateFormat(String dateStr) {
     try {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");;
    sdf.setLenient(false);
    sdf.parse(dateStr);
    return true; // Data String in the correct format
    } catch (ParseException e) {
        return false; // Data String in the incorrect format
    }
}

/**
 * Deletes orders and related order details from the database based on the provided order number.
 *
 * This method first deletes associated rows in the 'orderdetails' table and then deletes the order itself.
 *
 * @param orderNumber The order number to be deleted.
 * @return True if deletion is successful, false otherwise.
 */
public boolean deleteOrders(String orderNumber) {
    try {
        DbConnect db = new DbConnect();
        Connection myConnection = db.getConnection();

        // First, delete related rows in orderdetails to allow for deleting of an order in the database
        String deleteOrderDetailsQuery = "DELETE FROM orderdetails WHERE orderNumber = ?";
        PreparedStatement deleteOrderDetailsStatement = myConnection.prepareStatement(deleteOrderDetailsQuery);
        deleteOrderDetailsStatement.setString(1, orderNumber);
        int rowsAffectedOrderDetails = deleteOrderDetailsStatement.executeUpdate();

        // Then, delete the order
        String deleteOrderQuery = "DELETE FROM orders WHERE orderNumber = ?";
        PreparedStatement deleteOrderStatement = myConnection.prepareStatement(deleteOrderQuery);
        deleteOrderStatement.setString(1, orderNumber);
        int rowsAffectedOrders = deleteOrderStatement.executeUpdate();

        return rowsAffectedOrders > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * Updates orders in the database based on the provided parameters.
 *
 * This method allows for selective updates of order attributes, validating and converting data as needed.
 *
 * @param orderNumberToUpdate The order number to update.
 * @param neworderDate        The new order date (format: "yyyy-MM-dd").
 * @param newrequiredDate     The new required date (format: "yyyy-MM-dd").
 * @param newshippedDate      The new shipped date (format: "yyyy-MM-dd").
 * @param newstatus           The new order status.
 * @param newcomments         The new order comments.
 * @return True if the update is successful, false otherwise.
 */
public boolean updateOrders(int orderNumberToUpdate, String neworderDate, String newrequiredDate,
        String newshippedDate, String newstatus, String newcomments) {
    Connection myConnection = null;

    try {
        DbConnect db = new DbConnect();
        myConnection = db.getConnection();

        List<String> updateColumns = new ArrayList<>();
        List<Object> updateValues = new ArrayList<>();

        // Validate and convert orderDate
        if (neworderDate != null && !neworderDate.trim().isEmpty()) {
            updateColumns.add("orderDate = ?");
            updateValues.add(java.sql.Date.valueOf(neworderDate));
        }

        // Validate and convert requiredDate
        if (newrequiredDate != null && !newrequiredDate.trim().isEmpty()) {
            updateColumns.add("requiredDate = ?");
            updateValues.add(java.sql.Date.valueOf(newrequiredDate));
        }

        // Validate and convert shippedDate
        if (newshippedDate != null && !newshippedDate.trim().isEmpty()) {
            updateColumns.add("shippedDate = ?");
            updateValues.add(java.sql.Date.valueOf(newshippedDate));
        }

        // Validate and convert status
        if (newstatus != null && !newstatus.trim().isEmpty()) {
            updateColumns.add("status = ?");
            updateValues.add(newstatus);
        }

        // Validate and convert comments
        if (newcomments != null && !newcomments.trim().isEmpty()) {
            updateColumns.add("comments = ?");
            updateValues.add(newcomments);
        }

        // Build the dynamic update SQL statement
        StringBuilder updateSql = new StringBuilder("UPDATE orders SET ");
        updateSql.append(String.join(", ", updateColumns));
        updateSql.append(" WHERE orderNumber = ?");

        // Prepare the statement
        PreparedStatement preparedStatement = myConnection.prepareStatement(updateSql.toString());

        // Set parameter values
        for (int i = 0; i < updateValues.size(); i++) {
            preparedStatement.setObject(i + 1, updateValues.get(i));
        }
        preparedStatement.setInt(updateValues.size() + 1, orderNumberToUpdate);

        // Execute the update
        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean addOrders(int orderNumber, String orderDate, String requiredDate, String shippedDate, String status,
        String comments, int customerNumber) {
    return false;
}

   }