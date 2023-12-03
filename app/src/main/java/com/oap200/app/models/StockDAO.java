package com.oap200.app.models;

import com.oap200.app.utils.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for stock.
 * Provides methods to interact with the database to manage stock data.
 * 
 * @author Dirkje Jansje van der Poel
 */
public class StockDAO {

    /**
     * Retrieves stock data from the database based on search text.
     * 
     * @param searchText The search criteria for stock data.
     * @return A list of stock data objects.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the database driver class is not found.
     */
    public List<Object[]> getStockData(String searchText) throws SQLException, ClassNotFoundException {
        List<Object[]> stockData = new ArrayList<>();
        String query = "SELECT productCode, productName, productLine, quantityInStock, buyPrice " +
                       "FROM products " +
                       "WHERE LOWER(productName) LIKE ? OR LOWER(productLine) LIKE ? " +
                       "ORDER BY quantityInStock DESC;";
    
        try (DbConnect dbConnect = new DbConnect();
             Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            pstmt.setString(1, "%" + searchText.toLowerCase() + "%");
            pstmt.setString(2, "%" + searchText.toLowerCase() + "%");
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stockData.add(new Object[]{
                            rs.getString("productCode"),
                            rs.getString("productName"),
                            rs.getString("productLine"),
                            rs.getInt("quantityInStock"),
                            rs.getDouble("buyPrice")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
        return stockData;
    }
}
