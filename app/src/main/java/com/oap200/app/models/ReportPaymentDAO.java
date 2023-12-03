package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling payment data retrieval from the database.
 * Provides methods to interact with the database and retrieve payment information.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportPaymentDAO {

    /**
     * Retrieves payment data from the database based on the provided date range.
     * This method fetches payment details like customer number, check number, payment date, and amount.
     *
     * @param startDate The start date for the payment data range.
     * @param endDate The end date for the payment data range.
     * @return A list of arrays, each array representing a payment record.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
    */
    public List<Object[]> getPaymentData(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        List<Object[]> paymentData = new ArrayList<>();
        String query = "SELECT customerNumber, checkNumber, paymentDate, amount " +
                       "FROM payments WHERE paymentDate BETWEEN ? AND ? ORDER BY paymentDate DESC;";

        try (DbConnect dbConnect = new DbConnect();
             Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    paymentData.add(new Object[]{
                        rs.getInt("customerNumber"),
                        rs.getString("checkNumber"),
                        rs.getDate("paymentDate"),
                        rs.getDouble("amount")
                    });
                }
            }
        }
        return paymentData;
    }
}
