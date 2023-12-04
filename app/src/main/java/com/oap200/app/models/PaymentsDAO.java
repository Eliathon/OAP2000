package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
*@author Jesper Solberg
*
*/

/**
 * Data Access Object (DAO) class for handling payments-related database operations.
 * This class provides methods to fetch payments from the database based on different criteria.
 */
public class PaymentsDAO {

    /**
     * Fetch payments from the database and return a list of String arrays.
     *
     * @return A list of String arrays representing payments.
     */
    public List<String[]> fetchPayments() {
        List<String[]> payments = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            // Create a SQL statement and execute the query to get all payments
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM payments");

            // Process the result set and populate the payments list
            while (myRs.next()) {
                String[] payment = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("checkNumber"),
                        myRs.getString("paymentDate"),
                        myRs.getString("amount"),

                };
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle exceptions by printing the stack trace
            ex.printStackTrace();
        }
        return payments;
    }

    /**
     * Fetch payments based on check information from the database.
     *
     * @return A list of String arrays representing payments based on check information.
     */
    public List<String[]> fetchCheck() {
        List<String[]> payments = new ArrayList<>();

        try {
            // Establish a database connection
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            // Create a SQL statement and execute the query to get all checks
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM payments");

            // Process the result set and populate the payments list
            while (myRs.next()) {
                String[] payment = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("checkNumber"),
                        myRs.getString("paymentDate"),
                        myRs.getString("amount"),

                };
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle exceptions by printing the stack trace
            ex.printStackTrace();
        }
        return payments;
    }

    /**
     * Fetch payments from the database based on payment date information.
     *
     * @return A list of String arrays representing payments based on payment date information.
     */
    public List<String[]> fetchDate() {
        List<String[]> payments = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM payments");

            while (myRs.next()) {
                String[] payment = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("checkNumber"),
                        myRs.getString("paymentDate"),
                        myRs.getString("amount"),

                };
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle exceptions by printing the stack trace
            ex.printStackTrace();
        }
        return payments;
    }

    /**
     * Fetch payments from the database based on amount information.
     *
     * @return A list of String arrays representing payments based on amount information.
     */
    public List<String[]> fetchAmount() {
        List<String[]> payments = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM payments");

            while (myRs.next()) {
                String[] payment = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("checkNumber"),
                        myRs.getString("paymentDate"),
                        myRs.getString("amount"),

                };
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle exceptions by printing the stack trace
            ex.printStackTrace();
        }
        return payments;
    }

    /**
     * Fetch payments from the database ordered by a specified column.
     *
     * @param orderByColumn The column to order payments by.
     * @return A list of String arrays representing payments ordered by the specified column.
     */
    public List<String[]> fetchPaymentsOrderedBy(String orderByColumn) {
        List<String[]> payments = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM payments ORDER BY " + orderByColumn);

            while (myRs.next()) {
                String[] payment = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("checkNumber"),
                        myRs.getString("paymentDate"),
                        myRs.getString("amount"),
                };
                payments.add(payment);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle exceptions by printing the stack trace
            ex.printStackTrace();
        }
        return payments;
    }
}
