// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DateFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;

public class ReportSalesPanel extends JPanel implements ReportGenerator {
    
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(2003, Calendar.JANUARY, 1);
        Date initialDate = calendar.getTime();
        Date earliestDate = calendar.getTime(); // January 1, 2003
        Date latestDate = new Date(); // Current date

        startDateSpinner = DateFactory.createDateSpinner(initialDate, earliestDate, latestDate);
        endDateSpinner = DateFactory.createDateSpinner(latestDate, earliestDate, latestDate);

        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startDateEditor);
        endDateSpinner.setEditor(endDateEditor);

        generateReportButton = ButtonBuilder.createStyledButton("Generate Sales Report", this::generateReport);
        printButton = ButtonBuilder.createStyledButton("Print Report", this::handlePrintAction);

        tableModel = new DefaultTableModel();
        reportTable = new JTable(tableModel);
        reportTable.setAutoCreateRowSorter(true);

        tableModel.addColumn("Customer Number");
        tableModel.addColumn("Check Number");
        tableModel.addColumn("Payment Date");
        tableModel.addColumn("Amount");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Start Date:"));
        inputPanel.add(startDateSpinner);
        inputPanel.add(new JLabel("End Date:"));
        inputPanel.add(endDateSpinner);
        inputPanel.add(generateReportButton);
        inputPanel.add(printButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);
    }

    private void addActionsToButtons() {
        generateReportButton.addActionListener(e -> generateReport());
        printButton.addActionListener(e -> handlePrintAction());
    }

    private void handlePrintAction() {
        if (PrintManager.isPrinting()) {
            return; // Do nothing if a print job is already in progress
        }
        PrintManager.printTable(reportTable);
    }

   @Override
public void generateReport() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String startDate = dateFormat.format(startDateSpinner.getValue());
    String endDate = dateFormat.format(endDateSpinner.getValue());

    DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
    tableModel.setRowCount(0);  // Clear existing rows

    try {
        DbConnect dbConnect = new DbConnect(); // Maak een nieuwe instantie van DbConnect
        Connection conn = dbConnect.getConnection(); // Gebruik de instantie om de verbinding op te halen

        PreparedStatement pstmt = conn.prepareStatement("SELECT customerNumber, checkNumber, paymentDate, amount " +
                                      "FROM payments WHERE paymentDate BETWEEN ? AND ? " +
                                      "ORDER BY paymentDate DESC;");
        pstmt.setString(1, startDate);
        pstmt.setString(2, endDate);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            tableModel.addRow(new Object[]{
                rs.getInt("customerNumber"),
                rs.getString("checkNumber"),
                rs.getDate("paymentDate"),
                rs.getDouble("amount")
            });
        }

        rs.close();
        pstmt.close();
        conn.close();
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error accessing the database: " + ex.getMessage(),
                                      "Database error", JOptionPane.ERROR_MESSAGE);
     }
  }
}


