package com.oap200.app.views;

import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.controllers.ReportFinancialController;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DateFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
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

    private ReportFinancialController controller;

    public ReportFinancialPanel() {
        this.controller = new ReportFinancialController();
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
        generateReport(); // Automatically load the data when opening the panel
    }

    /**
     * Initializes components of the panel.
     */
    private void initializeComponents() {
        yearComboBox = new JComboBox<>(new String[]{"2003", "2004", "2005"});
        quarterComboBox = new JComboBox<>(new String[]{"Q1", "Q2", "Q3", "Q4", "Year"});

        generateReportButton = ButtonBuilder.createButton("Filter", () -> generateReport());
        saveReportButton = ButtonBuilder.createButton("Save as...", () -> saveReportToFile());

        csvRadioButton = ButtonBuilder.createStyledRadioButton("CSV", true);
        txtRadioButton = ButtonBuilder.createStyledRadioButton("TXT", false);
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
        try {
            List<Object[]> financialData = controller.getFinancialData(startDate, endDate);
            tableModel.setRowCount(0);
            for (Object[] row : financialData) {
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

