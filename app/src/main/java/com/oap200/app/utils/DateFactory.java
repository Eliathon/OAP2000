package com.oap200.app.utils;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

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
    public static String calculateStartDate(String year, String quarter) {
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

public static String calculateEndDate(String year, String quarter) {
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