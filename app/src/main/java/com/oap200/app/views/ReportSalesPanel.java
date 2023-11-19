// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import com.oap200.app.utils.DbConnect;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;
import java.awt.print.PrinterException;

public class ReportSalesPanel extends JPanel  {
    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JSpinner startDateSpinner, endDateSpinner;


    public ReportSalesPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    private void initializeComponents() {
        // Initialize date spinners
        Calendar calendar = Calendar.getInstance();
        calendar.set(2003, Calendar.JANUARY, 1);
        Date initialDate = calendar.getTime();
        // Optioneel: Definieer de minimum en maximum datums voor de spinner
        Date earliestDate = calendar.getTime(); // 1 januari 2003
        Date latestDate = new Date(); // Huidige datum als maximum

        // Initialiseer de JSpinners met het nieuwe SpinnerDateModel
        startDateSpinner = new JSpinner(new SpinnerDateModel(initialDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH));
        endDateSpinner = new JSpinner(new SpinnerDateModel(latestDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH));

        // Stel de DateEditor in
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startDateEditor);
        endDateSpinner.setEditor(endDateEditor);
    
        // Initialize table and buttons
        generateReportButton = new JButton("Generate Sales Report");
        printButton = new JButton("Print Report");

        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        reportTable.setAutoCreateRowSorter(true);

        tableModel.addColumn("Date");
        tableModel.addColumn("Order Number");
        tableModel.addColumn("Customer Number");
        tableModel.addColumn("Units Sold");
        tableModel.addColumn("Total Sales");
        tableModel.addColumn("Average Sale Value");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Start Date:"));
        inputPanel.add(startDateSpinner);
        inputPanel.add(new JLabel("End Date:"));
        inputPanel.add(endDateSpinner);
        inputPanel.add(generateReportButton); //Button rechts bovenaan
        inputPanel.add(printButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);
 
    }

   private void addActionsToButtons() {
      generateReportButton.addActionListener(e -> generateReport());
      printButton.addActionListener(e -> printTable());
   }

    private void generateReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat.format(startDateSpinner.getValue());
        String endDate = dateFormat.format(endDateSpinner.getValue());

        DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
        tableModel.setRowCount(0);  // Clear existing rows

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            DbConnect dbConnect = new DbConnect();
            conn = dbConnect.getConnection();

            String sql = "SELECT o.orderDate, o.orderNumber, o.customerNumber, " +
                         "SUM(od.quantityOrdered) AS unitsSold, " +
                         "SUM(od.quantityOrdered * od.priceEach) AS totalSales, " +
                         "AVG(od.priceEach) AS averageSaleValue " +
                         "FROM orders o INNER JOIN orderdetails od ON o.orderNumber = od.orderNumber " +
                         "WHERE o.orderDate BETWEEN ? AND ? " +
                         "GROUP BY o.orderDate, o.orderNumber, o.customerNumber " +
                         "ORDER BY o.orderDate DESC;";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getDate("orderDate"),
                    rs.getInt("orderNumber"),
                    rs.getInt("customerNumber"),
                    rs.getInt("unitsSold"),
                    rs.getDouble("totalSales"),
                    rs.getDouble("averageSaleValue")
                });
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing the database: " + ex.getMessage(),
                                          "Database error", JOptionPane.ERROR_MESSAGE);
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
        try {
            reportTable.print(); // This will trigger the print dialog
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Print error: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}