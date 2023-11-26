package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<String[]> fetchCustomers() {
        List<String[]> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        
        try (Connection myConnection = new DbConnect().getConnection();
             Statement myStmt = myConnection.createStatement();
             ResultSet myRs = myStmt.executeQuery(query)) {

            while (myRs.next()) {
                String[] customer = new String[]{
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
}