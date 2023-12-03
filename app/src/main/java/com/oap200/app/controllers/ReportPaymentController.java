package com.oap200.app.controllers;

import com.oap200.app.models.ReportPaymentDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller class for managing payment data retrieval and processing.
 * Acts as an intermediary between the view layer and the data access layer.
 * Handles business logic related to payment data.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportPaymentController {

    private ReportPaymentDAO model;

    /**
     * Constructs a new ReportPaymentController.
     */
    public ReportPaymentController() {
        this.model = new ReportPaymentDAO();
    }

    /**
     * Retrieves payment data for the specified date range.
     * 
     * @param startDate The start date for the payment data range.
     * @param endDate The end date for the payment data range.
     * @return A list of payment data records.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the JDBC driver class is not found.
     */
    public List<Object[]> getPaymentData(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        return model.getPaymentData(startDate, endDate);
    }
}
