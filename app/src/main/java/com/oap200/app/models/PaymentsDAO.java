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


    
    public boolean deletePayments(int customerNumber) {
        if (hasReports(customerNumber)) {
            return false;
        }

        String updateSql = "UPDATE payments SET checkNumber = NULL WHERE checkNumber = ?";
        String deleteSql = "DELETE FROM payments WHERE checkNumber = ?";
        
        try (Connection conn = new DbConnect().getConnection()) {
            // Set customers' salesRepEmployeeNumber to NULL where this employee is the sales rep
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, customerNumber);
                pstmt.executeUpdate();
            }

            // Delete the employee
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, customerNumber);
                pstmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
