package com.oap200.app.views;

import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DateFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;

/**
 * ReportSalesPanel is responsible for generating and displaying sales reports.
 * It allows users to select a date range and generate corresponding sales data.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportSalesPanel extends JPanel implements ReportGenerator {

    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JSpinner startDateSpinner, endDateSpinner;

    /**
     * Constructs the ReportSalesPanel with layout and component initialization.
     */
    public ReportSalesPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initializeComponents() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2003, Calendar.JANUARY, 1);
        Date initialDate = calendar.getTime();
        Date earliestDate = initialDate;
        Date latestDate = new Date();

        startDateSpinner = DateFactory.createDateSpinner(initialDate, earliestDate, latestDate);
        endDateSpinner = DateFactory.createDateSpinner(latestDate, earliestDate, latestDate);

        // Initialize buttons and table
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

    /**
     * Handles the action to print the report.
     */
    private void handlePrintAction() {
        if (PrintManager.isPrinting()) {
            return;
        }
        PrintManager.printTable(reportTable);
    }

    /**
     * Generates the report based on selected dates.
     */
    @Override
    public void generateReport() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat.format(startDateSpinner.getValue());
        String endDate = dateFormat.format(endDateSpinner.getValue());

        tableModel.setRowCount(0);

        try (DbConnect dbConnect = new DbConnect();
             Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT customerNumber, checkNumber, paymentDate, amount FROM payments " +
                     "WHERE paymentDate BETWEEN ? AND ? ORDER BY paymentDate DESC;")) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getInt("customerNumber"),
                        rs.getString("checkNumber"),
                        rs.getDate("paymentDate"),
                        rs.getDouble("amount")
                    });
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing the database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error closing database connection: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
