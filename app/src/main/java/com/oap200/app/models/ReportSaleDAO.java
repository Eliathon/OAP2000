package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling sales data retrieval from the database.
 */
public class ReportSaleDAO {

    /**
     * Retrieves sales data from the database based on the provided date range.
     *
     * @param startDate Start date for the sales data range.
     * @param endDate End date for the sales data range.
     * @return List of sales data records.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
    */
    public List<Object[]> getSalesData(String startDate, String endDate) throws SQLException, ClassNotFoundException  {
        List<Object[]> salesData = new ArrayList<>();
        String query = "SELECT customerNumber, checkNumber, paymentDate, amount " +
                       "FROM payments WHERE paymentDate BETWEEN ? AND ? ORDER BY paymentDate DESC;";

        try (DbConnect dbConnect = new DbConnect();
             Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    salesData.add(new Object[]{
                        rs.getInt("customerNumber"),
                        rs.getString("checkNumber"),
                        rs.getDate("paymentDate"),
                        rs.getDouble("amount")
                    });
                }
            }
        }
        return salesData;
    }
}
