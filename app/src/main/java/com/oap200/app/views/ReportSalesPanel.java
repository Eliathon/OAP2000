package com.oap200.app.views;

import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.controllers.ReportSaleController;
//import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DateFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
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
        generateReport(); // Automatically load the data when opening the panel
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
        generateReportButton = ButtonBuilder.createButton("Filter", () -> generateReport());
        printButton = ButtonBuilder.createButton("Print", () -> handlePrintAction());

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

    ReportSaleController controller = new ReportSaleController();
    tableModel.setRowCount(0);

    try {
        List<Object[]> salesData = controller.getSalesData(startDate, endDate);
        for (Object[] row : salesData) {
            tableModel.addRow(row);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error accessing the database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
