package com.oap200.app.controllers;

import com.oap200.app.models.ReportSaleDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for managing sales data.
 */
public class ReportSaleController {
    private ReportSaleDAO model;

    public ReportSaleController() {
        this.model = new ReportSaleDAO();
    }

    /**
     * Retrieves sales data for the specified date range.
     *
     * @param startDate The start date for the sales data range.
     * @param endDate The end date for the sales data range.
     * @return A list of sales data records.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
    */
    public List<Object[]> getSalesData(String startDate, String endDate) throws SQLException, ClassNotFoundException  {
        return model.getSalesData(startDate, endDate);
    }
}
