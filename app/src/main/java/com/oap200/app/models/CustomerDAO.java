// Created by Johnny
package com.oap200.app.models;


import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class CustomerDAO {

    public List<String[]> Customers() {
        List<String[]> customers = new ArrayList<>();
     try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM customers");

            while (myRs.next()) {
                String[] customer = new String[] {
                        myRs.getString("customerNumber"),
                        myRs.getString("customerName"),
                        myRs.getString("contactLastName"),
                        myRs.getString("contactFirstName"),
                        myRs.getString("phone"),
                        myRs.getString("adressLine1"),
                        myRs.getString("adressLine2"),
                        myRs.getString("city"),
						myRs.getString("state"),
                        myRs.getString("postalCode"),
                        myRs.getString("country"),
                        myRs.getString("salesRepEmployeeNumber"),
						myRs.getString("creditLimit"),
						
						
						
						
						
                };
                customers.add(customer);
            }
                 } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return customers;
    }
}