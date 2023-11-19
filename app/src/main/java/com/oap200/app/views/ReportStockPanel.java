package com.oap200.app.views;

import com.oap200.app.utils.DbConnect;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.print.PrinterException;

public class ReportStockPanel extends JPanel {
    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;

    public ReportStockPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    private void initializeComponents() {
        generateReportButton = new JButton("Generate Stock Report");
        printButton = new JButton("Print Report");

        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        reportTable.setAutoCreateRowSorter(true);

        tableModel.addColumn("Product Code");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Line");
        tableModel.addColumn("Quantity In Stock");
        tableModel.addColumn("Buy Price");

        JPanel inputPanel = new JPanel();
        inputPanel.add(generateReportButton);
        inputPanel.add(printButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);
    }

    private void addActionsToButtons() {
        generateReportButton.addActionListener(e -> generateReport());
        printButton.addActionListener(e -> printTable());
    }

    private void generateReport() {
        DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
        tableModel.setRowCount(0);  // Clear existing rows

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            DbConnect dbConnect = new DbConnect();
            conn = dbConnect.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT productCode, productName, productLine, quantityInStock, buyPrice FROM products ORDER BY quantityInStock DESC;";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("productCode"),
                    rs.getString("productName"),
                    rs.getString("productLine"),
                    rs.getInt("quantityInStock"),
                    rs.getDouble("buyPrice")
                });
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing the database: " + ex.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void printTable() {
        try {
            reportTable.print();  // This will trigger the print dialog
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Print error: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
