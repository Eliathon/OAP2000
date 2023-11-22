// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.ButtonBuilder; // Import the ButtonBuilder class
import java.sql.*;
import java.awt.*;
import java.awt.print.PrinterException;

public class ReportStockPanel extends JPanel {
    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private boolean isPrinting = false; // Flag to track printing status

    public ReportStockPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    private void initializeComponents() {
        // Use ButtonBuilder for button creation
        generateReportButton = ButtonBuilder.createStyledButton("Generate Stock Report", this::generateReport);
        printButton = ButtonBuilder.createStyledButton("Print Report", this::printTable);

        searchField = new JTextField(20); // Width of the search field
        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        reportTable.setRowSorter(sorter);

        tableModel.addColumn("Product Code");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Line");
        tableModel.addColumn("Quantity In Stock");
        tableModel.addColumn("Buy Price");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(generateReportButton);
        inputPanel.add(printButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                newFilter();
            }
            public void removeUpdate(DocumentEvent e) {
                newFilter();
            }
            public void insertUpdate(DocumentEvent e) {
                newFilter();
            }

            private void newFilter() {
                RowFilter<DefaultTableModel, Object> rf = null;
                try {
                    rf = RowFilter.regexFilter("(?i)" + searchField.getText(), 0, 1, 2);
                } catch (java.util.regex.PatternSyntaxException e) {
                    return;
                }
                sorter.setRowFilter(rf);
            }
        });
    }

    private void addActionsToButtons() {
        generateReportButton.addActionListener(e -> generateReport());
        printButton.addActionListener(e -> printTable());
    }

    private void generateReport() {
        DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
        tableModel.setRowCount(0);  // Clear existing rows
    
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            DbConnect dbConnect = new DbConnect();
            conn = dbConnect.getConnection();
            pstmt = conn.prepareStatement("SELECT productCode, productName, productLine, quantityInStock, buyPrice FROM products ORDER BY quantityInStock DESC;");
            
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("productCode"),
                    rs.getString("productName"),
                    rs.getString("productLine"),
                    rs.getInt("quantityInStock"),
                    rs.getDouble("buyPrice")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing the database: " + ex.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database driver not found: " + ex.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void printTable() {
        if (isPrinting) {
            return; // Exit the method if a print task is already in progress
        }

        try {
            isPrinting = true; // Set the flag to true, printing starts
            reportTable.print(); // This will trigger the print dialog
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Print error: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            isPrinting = false; // Reset the flag when printing is complete
        }
    }
}
