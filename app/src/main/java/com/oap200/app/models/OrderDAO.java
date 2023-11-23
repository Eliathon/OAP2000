// Created by Sindre

package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
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
                        myRs.getString("Order Number"),
                        myRs.getString("Order Date"),
                        myRs.getString("Required Date"),
                        myRs.getString("Shipped Date"),
                        myRs.getString("Status"),
                        myRs.getString("Comments"),
                        myRs.getString("Customer Number"),
                };
                orders.add(order);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return orders;
    }
}
