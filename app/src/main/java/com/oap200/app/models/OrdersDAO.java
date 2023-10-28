package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {

    public List<String> fetchOrders() {
        List<String> orders = new ArrayList<>();

        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();
            Statement myStmt = myConnection.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM orders");

            while (myRs.next()) {
                orders.add(myRs.getString("orderNumber"));
                orders.add(myRs.getString("orderDate"));
                orders.add(myRs.getString("requiredDate"));
                orders.add(myRs.getString("shippedDate"));
                orders.add(myRs.getString("status"));
                orders.add(myRs.getString("comments"));
                orders.add(myRs.getString("customerNumber"));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return orders;
    }
}
