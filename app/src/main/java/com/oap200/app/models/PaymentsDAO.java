package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class PaymentsDAO {

    public List<String[]> fetchPayments() {
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
            ex.printStackTrace();
        }
        return payments;
    }


    
    public void insertPayment(String customerNumber, String checkNumber, String paymentDate, String amount) {
        try {
            DbConnect db = new DbConnect();
            Connection myConnection = db.getConnection();

 // Using a prepared statement to prevent SQL injection

            String sql = "INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = myConnection.prepareStatement(sql)) {
                pstmt.setString(1, customerNumber);
                pstmt.setString(2, checkNumber);
                pstmt.setString(3, paymentDate);
                pstmt.setString(4, amount);

// Execute the insert statement

pstmt.executeUpdate();
}
} catch (SQLException | ClassNotFoundException ex) {
ex.printStackTrace();
// Handle the exception appropriately based on your application's requirements
}
}



}
