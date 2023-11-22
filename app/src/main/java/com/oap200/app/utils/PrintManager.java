package com.oap200.app.utils;

import javax.swing.*;
import java.awt.print.PrinterException;

public class PrintManager {
    /**
     * Print de gegeven JTable. Toont een dialoogvenster voor de printer en 
     * handelt de printtaak af.
     * 
     * @param table De JTable die geprint moet worden.
     */
    public static void printTable(JTable table) {
        try {
            boolean complete = table.print();
            if (complete) {
                // Optioneel: Toon een dialoogvenster of bericht dat het printen is geslaagd.
                JOptionPane.showMessageDialog(table, "Afdrukken voltooid", "Printen", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Handel de situatie af waar de gebruiker het printen annuleert.
                JOptionPane.showMessageDialog(table, "Afdrukken geannuleerd", "Printen", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();
            // Toon foutbericht bij een printerfout.
            JOptionPane.showMessageDialog(table, "Fout tijdens het afdrukken: " + ex.getMessage(), "Printfout", JOptionPane.ERROR_MESSAGE);
        }
    }
}
