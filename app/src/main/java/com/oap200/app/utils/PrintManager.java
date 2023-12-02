package com.oap200.app.utils;

import javax.swing.*;
import java.awt.print.PrinterException;

/**
 * Manages printing tasks for tables in the application.
 * <p>
 * This class provides functionality to print data from JTable
 * and handles the printing status to avoid concurrent printing jobs.
 * </p>
 * 
 * @author Dirkje J. van der Poel
 */
public class PrintManager {
    private static boolean isPrinting = false; // Flag to track printing status

    /**
     * Checks if a printing operation is currently ongoing.
     *
     * @return true if printing is in progress, false otherwise.
     */
    public static boolean isPrinting() {
        return isPrinting;
    }

    /**
     * Prints the content of a provided JTable.
     * <p>
     * If a printing operation is already in progress, this method will return without doing anything.
     * Otherwise, it starts a print job for the provided table.
     * </p>
     *
     * @param table The JTable to be printed.
     */
    public static void printTable(JTable table) {
        if (isPrinting) {
            return; // Do nothing if a print job is already in progress
        }

        try {
            isPrinting = true; // Set the flag to indicate the start of a print job
            boolean complete = table.print();
            if (complete) {
                JOptionPane.showMessageDialog(table, "Printing completed", "Printing", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(table, "Printing cancelled", "Printing", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(table, "Error during printing: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            isPrinting = false; // Reset the flag when the print job is done
        }
    }
}
