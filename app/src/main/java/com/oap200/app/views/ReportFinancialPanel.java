package com.oap200.app.views;

import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DateFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

/**
 * ReportFinancialPanel is a panel that enables users to generate financial
 * reports based on year and quarter. It offers a user interface with dropdown menus for
 * selecting the year and quarter, and a button to generate the report.
 * The generated report is displayed in a JTable.
 *
 * @author Dirkje Jansje van der Poel
 * @version 1.0
 * @since 2023
 */
public class ReportFinancialPanel extends JPanel implements ReportGenerator {
    private JButton generateReportButton, saveReportButton;
    private JRadioButton csvRadioButton, txtRadioButton, yearlyReportRadioButton, quarterlyReportRadioButton;
    private JComboBox<String> yearComboBox, quarterComboBox;
    private JTable financialTable;
    private DefaultTableModel tableModel;

    public ReportFinancialPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    /**
     * Initializes components of the panel.
     */
    private void initializeComponents() {
        yearComboBox = new JComboBox<>(new String[]{"2003", "2004", "2005"});
        quarterComboBox = new JComboBox<>(new String[]{"Q1", "Q2", "Q3", "Q4", "Q All"});

        generateReportButton = ButtonBuilder.createStyledButton("Generate Financial Report", () -> generateReport());
        saveReportButton = ButtonBuilder.createStyledButton("Save Financial Report", () -> saveReportToFile());

        csvRadioButton = ButtonBuilder.createStyledRadioButton("Save as CSV", true);
        txtRadioButton = ButtonBuilder.createStyledRadioButton("Save as TXT", false);
        yearlyReportRadioButton = new JRadioButton("Yearly Report");
        quarterlyReportRadioButton = new JRadioButton("Quarterly Report", true);

        ButtonGroup formatButtonGroup = new ButtonGroup();
        formatButtonGroup.add(csvRadioButton);
        formatButtonGroup.add(txtRadioButton);

        ButtonGroup reportTypeButtonGroup = new ButtonGroup();
        reportTypeButtonGroup.add(yearlyReportRadioButton);
        reportTypeButtonGroup.add(quarterlyReportRadioButton);

        tableModel = new DefaultTableModel();
        financialTable = new JTable(tableModel);
        financialTable.setAutoCreateRowSorter(true);
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Total Amount");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearComboBox);
        inputPanel.add(new JLabel("Quarter:"));
        inputPanel.add(quarterComboBox);
        inputPanel.add(generateReportButton);
        inputPanel.add(saveReportButton);
        inputPanel.add(csvRadioButton);
        inputPanel.add(txtRadioButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(financialTable), BorderLayout.CENTER);
    }

     /**
     * Adds action listeners to buttons.
     */
    private void addActionsToButtons() {
        generateReportButton.addActionListener(e -> {
            String selectedYear = (String) yearComboBox.getSelectedItem();
            String selectedQuarter = (String) quarterComboBox.getSelectedItem();
    
            if (quarterlyReportRadioButton.isSelected()) {
                generateQuarterlyReport(selectedYear, selectedQuarter);
            } else {
                generateYearlyReport(selectedYear);
            }
        });
    
       // TODO: Dit is degene die voor een 2de scherm zorgt :(( saveReportButton.addActionListener(e -> saveReportToFile());
    }

     /**
     * Generates a quarterly financial report for the selected year and quarter.
     *
     * @param selectedYear  The year for which the report is generated.
     * @param selectedQuarter The quarter for which the report is generated.
     */     
    private void generateQuarterlyReport(String selectedYear, String selectedQuarter) {
        String startDate, endDate;
        if ("Q All".equals(selectedQuarter)) {
            startDate = selectedYear + "-01-01";
            endDate = selectedYear + "-12-31";
        } else {
            startDate = DateFactory.calculateStartDate(selectedYear, selectedQuarter);
            endDate = DateFactory.calculateEndDate(selectedYear, selectedQuarter);
        }

        generateReportWithDates(startDate, endDate);
    }

    /**
     * Generates a yearly financial report for the selected year.
     *
     * @param selectedYear The year for which the report is generated.
     */    
    private void generateYearlyReport(String selectedYear) {
        String startDate = selectedYear + "-01-01";
        String endDate = selectedYear + "-12-31";

        generateReportWithDates(startDate, endDate);
    }

    /**
     * Opens a file chooser and allows the user to save the report in a file.
     */
    private void saveReportToFile() {

        System.out.println("saveReportToFile is called");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report As...");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(fileToSave);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        bw.write(tableModel.getValueAt(i, j).toString());
                        if (csvRadioButton.isSelected()) {
                            if (j < tableModel.getColumnCount() - 1) {
                                bw.write(",");
                            }
                        } else if (txtRadioButton.isSelected()) {
                            if (j < tableModel.getColumnCount() - 1) {
                                bw.write("\t");
                            }
                        }
                    }
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Report saved successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    } 

    @Override
    public void generateReport() {
        String selectedYear = (String) yearComboBox.getSelectedItem();
        String selectedQuarter = (String) quarterComboBox.getSelectedItem();

        String startDate = DateFactory.calculateStartDate(selectedYear, selectedQuarter);
        String endDate = DateFactory.calculateEndDate(selectedYear, selectedQuarter);

        generateReportWithDates(startDate, endDate);
    }
    
     /**
     * Generates and displays the report based on the provided start and end dates.
     *
     * @param startDate The start date for the report.
     * @param endDate   The end date for the report.
     */
    private void generateReportWithDates(String startDate, String endDate) {
        DbConnect dbConnect = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            dbConnect = new DbConnect();
            conn = dbConnect.getConnection();
            String sql = "SELECT c.customerName, SUM(od.quantityOrdered * od.priceEach) AS totalSales "
                    + "FROM customers c "
                    + "JOIN orders o ON c.customerNumber = o.customerNumber "
                    + "JOIN orderdetails od ON o.orderNumber = od.orderNumber "
                    + "WHERE o.orderDate BETWEEN ? AND ? "
                    + "GROUP BY c.customerName "
                    + "ORDER BY totalSales DESC;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            rs = pstmt.executeQuery();

            tableModel.setRowCount(0); // Clear the table
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("customerName"),
                    rs.getBigDecimal("totalSales")
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                if (dbConnect != null) dbConnect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }}

