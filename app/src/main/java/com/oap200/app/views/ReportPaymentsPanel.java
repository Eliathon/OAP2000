// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.utils.ButtonBuilder; // Importeer ButtonBuilder
import com.oap200.app.utils.DateFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;

public class ReportPaymentsPanel extends JPanel implements ReportGenerator {
    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JSpinner startDateSpinner, endDateSpinner;

    public ReportPaymentsPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    private void initializeComponents() {
        // Initialize date spinners
        Calendar calendar = Calendar.getInstance();
        calendar.set(2003, Calendar.JANUARY, 1);
        Date initialDate = calendar.getTime();

        // Optioneel: Definieer de minimum en maximum datums voor de spinn
        Date earliestDate = calendar.getTime(); // 1 januari 2003
        Date latestDate = new Date(); // 

        // Initialize DateSpinnerFactory spinners
        startDateSpinner = DateFactory.createDateSpinner(initialDate, earliestDate, latestDate);
        endDateSpinner = DateFactory.createDateSpinner(latestDate, earliestDate, latestDate);

        // Stel de DateEditor in
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startDateEditor);
        endDateSpinner.setEditor(endDateEditor);

        // Initialize table and buttons
        generateReportButton = ButtonBuilder.createStyledButton("Generate Payment Report", this::generateReport);
        printButton = ButtonBuilder.createStyledButton("Print Report", () -> PrintManager.printTable(reportTable));

        
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
        // Voeg deze regel toe aan je methode voor het instellen van button acties.
        printButton.addActionListener(e -> handlePrintAction());

    }
    
    private void handlePrintAction() {
        if (PrintManager.isPrinting()) {
            return; // Als er al een printtaak bezig is, doe dan niets
        }
        PrintManager.printTable(reportTable);
    }

@Override
public void generateReport() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String startDate = dateFormat.format(startDateSpinner.getValue());
    String endDate = dateFormat.format(endDateSpinner.getValue());

    DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
    tableModel.setRowCount(0); // Clear existing rows

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        DbConnect dbConnect = new DbConnect();
        conn = dbConnect.getConnection();

        String sql = "SELECT customerNumber, checkNumber, paymentDate, amount " +
                     "FROM payments WHERE paymentDate BETWEEN ? AND ? " +
                     "ORDER BY paymentDate DESC;";

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, startDate);
        pstmt.setString(2, endDate);

        rs = pstmt.executeQuery();

        while (rs.next()) {
            tableModel.addRow(new Object[]{
                rs.getInt("customerNumber"),
                rs.getString("checkNumber"),
                rs.getDate("paymentDate"),
                rs.getDouble("amount")
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
}

     


