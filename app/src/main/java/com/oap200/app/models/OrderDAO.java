// Created by Patrik

package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;  


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public boolean deleteOrders(int orderNumber) {
        if (hasReports(orderNumber)) {
            return false;
        }

        String updateSql = "UPDATE orders SET orderNumber = NULL WHERE orderNumber = ?";
        String deleteSql = "DELETE FROM orders WHERE orderNumber = ?";
        
        try (Connection conn = new DbConnect().getConnection()) {
    
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, orderNumber);
                pstmt.executeUpdate();
            }

            // Delete order
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, orderNumber);
                pstmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
