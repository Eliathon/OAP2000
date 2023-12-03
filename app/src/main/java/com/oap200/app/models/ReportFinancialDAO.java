package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for managing financial report data retrieval from the database.
 * Provides methods to interact with the database for financial reporting.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportFinancialDAO {

    /**
     * Retrieves financial data from the database based on the provided date range.
     *
     * @param startDate The start date for the financial data range.
     * @param endDate The end date for the financial data range.
     * @return A list of financial data records.
     * @throws SQLException If a database access error occurs.
     */
    public List<Object[]> getFinancialData(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        List<Object[]> financialData = new ArrayList<>();
        String query = "SELECT c.customerName, SUM(od.quantityOrdered * od.priceEach) AS totalSales "
                     + "FROM customers c "
                     + "JOIN orders o ON c.customerNumber = o.customerNumber "
                     + "JOIN orderdetails od ON o.orderNumber = od.orderNumber "
                     + "WHERE o.orderDate BETWEEN ? AND ? "
                     + "GROUP BY c.customerName "
                     + "ORDER BY totalSales DESC;";

        try (DbConnect dbConnect = new DbConnect();
             Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    financialData.add(new Object[]{
                        rs.getString("customerName"),
                        rs.getBigDecimal("totalSales")
                    });
                }
            }
        }
        return financialData;
    }
}
