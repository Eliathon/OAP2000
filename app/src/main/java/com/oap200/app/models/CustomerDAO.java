//Created by Johnny
package com.oap200.app.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oap200.app.utils.DbConnect;

public class CustomerDAO {

    // Method to fetch customers from the database
    public List<String[]> fetchCustomers() {
        List<String[]> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection myConnection = new DbConnect().getConnection();
                Statement myStmt = myConnection.createStatement();
                ResultSet myRs = myStmt.executeQuery(query)) {

            while (myRs.next()) {
                String[] customer = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("customerName"),
                        myRs.getString("contactLastName"),
                        myRs.getString("contactFirstName"),
                        myRs.getString("phone"),
                        myRs.getString("addressLine1"),
                        myRs.getString("addressLine2"),
                        myRs.getString("city"),
                        myRs.getString("state"),
                        myRs.getString("postalCode"),
                        myRs.getString("country"),
                        myRs.getString("salesRepEmployeeNumber"),
                        myRs.getString("creditLimit")
                };
                customers.add(customer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Consider more sophisticated error handling
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public String getLatestCustomerNumber() throws SQLException, ClassNotFoundException {
        String query = "SELECT MAX(customerNumber) FROM customers";
        try (Connection myConnection = new DbConnect().getConnection();
             Statement myStmt = myConnection.createStatement();
             ResultSet myRs = myStmt.executeQuery(query)) {

            if (myRs.next()) {
                return myRs.getString(1);
            } else {
                // Handle the case where no customer exists yet
                return "0"; // or any default value
            }
        }
    }

    // Method to search for customers by name in the database
    public static List<String[]> searchCustomer(String customerName) {
        List<String[]> searchResults = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();

            // Execute prepared statement to search for customers with a given name
            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("SELECT * FROM customers WHERE customerName LIKE ?");
            preparedStatement.setString(1, "%" + customerName + "%");
            ResultSet myRs = preparedStatement.executeQuery();

            // Process the retrieved data and populate the 'searchResults' list
            while (myRs.next()) {
                String[] customer = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("customerName"),
                        myRs.getString("contactLastName"),
                        myRs.getString("contactFirstName"),
                        myRs.getString("phone"),
                        myRs.getString("addressLine1"),
                        myRs.getString("addressLine2"),
                        myRs.getString("city"),
                        myRs.getString("state"),
                        myRs.getString("postalCode"),
                        myRs.getString("country"),
                        myRs.getString("salesRepEmployeeNumber"),
                        myRs.getString("creditLimit")
                };
                searchResults.add(customer);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }

    // Method to delete a customer from the database
    public boolean deleteCustomer(String customerNumber) {
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();

            // Execute prepared statement to delete a customer with a given number
            PreparedStatement preparedStatement = myConnection
                    .prepareStatement("DELETE FROM customers WHERE customerNumber = ?");
            preparedStatement.setString(1, customerNumber);

            // Get the number of rows affected after the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if deletion was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Method to add a new customer to the database
    public boolean addCustomer(
            String customerNumber, String customerName, String contactLastName, String contactFirstName,
            String phone, String addressLine1, String addressLine2, String city, String state,
            String postalCode, String country, int salesRepEmployeeNumber, BigDecimal creditLimit) {
        Connection myConnection = null;

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            myConnection = db.getConnection();

            // Prepare the INSERT query for a customer
            PreparedStatement preparedStatement = myConnection.prepareStatement(
                    "INSERT INTO customers (customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, customerNumber);
            preparedStatement.setString(2, customerName);
            preparedStatement.setString(3, contactLastName);
            preparedStatement.setString(4, contactFirstName);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, addressLine1);
            preparedStatement.setString(7, addressLine2);
            preparedStatement.setString(8, city);
            preparedStatement.setString(9, state);
            preparedStatement.setString(10, postalCode);
            preparedStatement.setString(11, country);
            preparedStatement.setInt(12, salesRepEmployeeNumber);
            preparedStatement.setBigDecimal(13, creditLimit);

            // Start transaction
            myConnection.setAutoCommit(false);

            // Execute the INSERT query
            int rowsAffected = preparedStatement.executeUpdate();

            // Confirm the transaction if the addition was successful
            if (rowsAffected > 0) {
                myConnection.commit();
                System.out.println("Customer added successfully");
            } else {
                // Roll back the transaction if the addition failed
                myConnection.rollback();
                System.out.println("Failed to add customer");
            }

            // Log the number of affected rows (for troubleshooting)
            System.out.println("Rows affected: " + rowsAffected);

            // Return true if the addition was successful
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            // Log any exceptions
            ex.printStackTrace();

            // Rollback in case of exception
            if (myConnection != null) {
                try {
                    myConnection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return false;

        } finally {
            try {
                if (myConnection != null) {
                    // Set back to autocommit=true
                    myConnection.setAutoCommit(true);
                    myConnection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to update a customer in the database
    public boolean UpdateCustomer(
            String customerName, String contactLastName, String contactFirstName, String addressLine1,
            String addressLine2, String city, String state, String postalCode, String country,
            int salesRepEmployeeNumber, BigDecimal creditLimit) {
        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            PreparedStatement preparedStatement = myConnection.prepareStatement(
                    "UPDATE customers SET contactLastName=?, contactFirstName=?, addressLine1=?, addressLine2=?, city=?, state=?, postalCode=?, country=?, salesRepEmployeeNumber=?, creditLimit=? WHERE customerName=?");

            // Set parameters for the UPDATE query
            preparedStatement.setString(1, contactLastName);
            preparedStatement.setString(2, contactFirstName);
            preparedStatement.setString(3, addressLine1);
            preparedStatement.setString(4, addressLine2);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, state);
            preparedStatement.setString(7, postalCode);
            preparedStatement.setString(8, country);
            preparedStatement.setInt(9, salesRepEmployeeNumber);
            preparedStatement.setBigDecimal(10, creditLimit);
            preparedStatement.setString(11, customerName);

            // Execute the UPDATE query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if the update was successful
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}