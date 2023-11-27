package com.oap200.app.utils;

import javax.swing.*;
import java.awt.print.PrinterException;

public class PrintManager {
    private static boolean isPrinting = false; // Vlag om bij te houden of er geprint wordt

    public static boolean isPrinting() {
        return isPrinting; // Return de huidige status van de printvlag
    }

    public static void printTable(JTable table) {
        if (isPrinting) {
            return; // Als er al een printtaak bezig is, doe dan niets
        }

        try {
            isPrinting = true; // Zet de vlag dat het printen begonnen is
            boolean complete = table.print();
            if (complete) {
                JOptionPane.showMessageDialog(table, "Printing completed", "Printen", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(table, "Printing canceled", "Printen", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(table, "Printing error: " + ex.getMessage(), "Print error", JOptionPane.ERROR_MESSAGE);
        } finally {
            isPrinting = false; // Reset de printvlag wanneer het printen klaar is
        }
    }
}
