package com.oap200.app.Interfaces;

/**
 * Interface defining the contract for report generation in the application.
 * <p>
 * This interface provides a standardized method for generating reports,
 * ensuring that all report generators in the application follow a consistent structure.
 * </p>
 * 
 * @author Dirkje J. van der Poel
 */
public interface ReportGenerator {
    /**
     * Generates a report based on implemented logic in the implementing classes.
     */
    void generateReport();
}
