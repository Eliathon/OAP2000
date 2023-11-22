package com.oap200.app.utils;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class DateSpinnerFactory {

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
}
