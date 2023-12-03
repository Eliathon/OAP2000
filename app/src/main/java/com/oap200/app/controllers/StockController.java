package com.oap200.app.controllers;

import com.oap200.app.models.StockDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for handling stock data operations.
 * It acts as an intermediary between the view and the data access object (DAO).
 * 
 * @author Dirkje Jansje van der Poel
 */
public class StockController {

    private StockDAO model;

    /**
     * Constructs a new StockController.
     */
    public StockController() {
        this.model = new StockDAO();
    }

    /**
     * Retrieves stock data based on the search text.
     * 
     * @param searchText The search criteria for stock data.
     * @return A list of stock data objects.
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
