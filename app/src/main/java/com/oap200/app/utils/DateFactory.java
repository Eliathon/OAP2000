package com.oap200.app.utils;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

/**
 * DateFactory class provides utility methods for date manipulation.
 * 
 * <p>This class includes methods for creating date spinners and 
 * calculating start and end dates based on year and quarter.</p>
 *
 * @author Dirkje J. van der Poel
 */
public class DateFactory {
    /**
     * Creates and returns a JSpinner for date selection.
     *
     * @param initialDate The initial date to be set in the spinner.
     * @param earliestDate The earliest date that can be selected.
     * @param latestDate The latest date that can be selected.
     * @return Configured JSpinner for date selection.
     */
    public static JSpinner createDateSpinner(Date initialDate, Date earliestDate, Date latestDate) {
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel(initialDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        return dateSpinner;
    }
    /**
     * Calculates and returns the start date of a given quarter in a year.
     *
     * @param year The year for which the start date is to be calculated.
     * @param quarter The quarter (Q1, Q2, Q3, Q4) for which the start date is to be calculated.
     * @return The start date of the specified quarter in the format 'yyyy-MM-dd'.
     */
    public static String calculateStartDate(String year, String quarter) {
        // Validation can be added here
        String startMonth;
        switch (quarter) {
            case "Q1":
                startMonth = "01";
                break;
            case "Q2":
                startMonth = "04";
                break;
            case "Q3":
                startMonth = "07";
                break;
            case "Q4":
                startMonth = "10";
                break;
            default:
                startMonth = "01";
                break;
        }
        return year + "-" + startMonth + "-01";
    }

    /**
     * Calculates and returns the end date of a given quarter in a year.
     *
     * @param year The year for which the end date is to be calculated.
     * @param quarter The quarter (Q1, Q2, Q3, Q4) for which the end date is to be calculated.
     * @return The end date of the specified quarter in the format 'yyyy-MM-dd'.
     */
    public static String calculateEndDate(String year, String quarter) {
        // Validation can be added here
        String endMonth;
        String endDay;
        switch (quarter) {
            case "Q1":
                endMonth = "03";
                endDay = "31";
                break;
            case "Q2":
                endMonth = "06";
                endDay = "30";
                break;
            case "Q3":
                endMonth = "09";
                endDay = "30";
                break;
            case "Q4":
                endMonth = "12";
                endDay = "31";
                break;
            default:
                endMonth = "12";
                endDay = "31";
                break;
        }
        return year + "-" + endMonth + "-" + endDay;
    }
}
