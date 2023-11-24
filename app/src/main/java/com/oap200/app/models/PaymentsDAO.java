package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
