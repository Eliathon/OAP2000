package com.oap200.app.controllers;

import com.oap200.app.models.ReportFinancialDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for managing financial report generation.
 * Acts as an intermediary between the financial report view and the data access object.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportFinancialController {

    private ReportFinancialDAO model;

    public ReportFinancialController() {
        this.model = new ReportFinancialDAO();
    }

    /**
     * Retrieves financial data for the specified date range.
     *
     * @param startDate The start date for the financial data range.
     * @param endDate The end date for the financial data range.
     * @return A list of financial data records.
     * @throws SQLException If a database access error occurs.
     */
    public List<Object[]> getFinancialData(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        return model.getFinancialData(startDate, endDate);
    }
}
