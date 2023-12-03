package com.oap200.app.controllers;

import com.oap200.app.models.ReportStockDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for handling stock data operations.
 * It acts as an intermediary between the view and the data access object (DAO).
 * 
 * @author Dirkje Jansje van der Poel
 */
public class ReportStockController {

    private ReportStockDAO model;

    /**
     * Constructs a new StockController.
     */
    public ReportStockController() {
        this.model = new ReportStockDAO();
    }

    /**
     * Retrieves stock data based on the search text.
     * 
     * @param searchText The search criteria for stock data.
     * @return A list of stock data objects.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
    */
    public List<Object[]> getStockData(String searchText) {
        try {
            return model.getStockData(searchText);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Consider logging this exception and/or rethrowing it as a custom exception
            return null;
        }
    }
}
