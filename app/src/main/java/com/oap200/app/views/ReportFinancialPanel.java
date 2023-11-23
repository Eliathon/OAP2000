package com.oap200.app.views;

import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.utils.DateFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ReportFinancialPanel is a panel that enables users to generate financial reports
 * based on year and quarter. It offers a user interface with dropdown menus for
 * selecting the year and quarter, and a button to generate the report.
 * The generated report is displayed in a JTable.
 *
 * @author Dirkje J. van der Poel
 * @version 1.0
 * @since 2023
 */

public class ReportFinancialPanel extends JPanel implements ReportGenerator {
    private JButton generateReportButton, saveReportButton;
    private JRadioButton csvRadioButton;
    private JRadioButton txtRadioButton;
    private JRadioButton yearlyReportRadioButton;
    private JRadioButton quarterlyReportRadioButton;
    private JComboBox<String> yearComboBox, quarterComboBox;
    private JTable financialTable;
    private DefaultTableModel tableModel;

    public ReportFinancialPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    private void initializeComponents() {
        // ComboBoxes for the selection off year and quater reports
        yearComboBox = new JComboBox<>(new String[]{"2003", "2004", "2005"});
        quarterComboBox = new JComboBox<>(new String[]{"Q1", "Q2", "Q3", "Q4", "Q All"}); //TODO mss handig met afvink box
        
        // Knop om het rapport te genereren
        generateReportButton = ButtonBuilder.createStyledButton("Generate Financial Report", this::generateReport);
        saveReportButton = ButtonBuilder.createStyledButton("Save Financial Report", this::saveReportToFile);

        csvRadioButton = ButtonBuilder.createStyledRadioButton("Save as CSV", true); // standaard geselecteerd
        txtRadioButton = ButtonBuilder.createStyledRadioButton("Save as TXT", false);

        yearlyReportRadioButton = new JRadioButton("Yearly Report");
        quarterlyReportRadioButton = new JRadioButton("Quarterly Report", true);


            // Groepeer de radio buttons
        ButtonGroup formatButtonGroup = new ButtonGroup();
        formatButtonGroup.add(csvRadioButton);
        formatButtonGroup.add(txtRadioButton);
        
        ButtonGroup reportTypeButtonGroup = new ButtonGroup();
        reportTypeButtonGroup.add(yearlyReportRadioButton);
        reportTypeButtonGroup.add(quarterlyReportRadioButton);

        // Tabel om financiële gegevens te tonen
        tableModel = new DefaultTableModel();
        financialTable = new JTable(tableModel);
        financialTable.setAutoCreateRowSorter(true);
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Total Amount");
        
        // Paneel voor het instellen van de interface
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearComboBox);
        inputPanel.add(new JLabel("Quarter:"));
        inputPanel.add(quarterComboBox);
        inputPanel.add(generateReportButton);
        inputPanel.add(saveReportButton);  // Toevoegen van de opslaanknop aan het inputPanel
        inputPanel.add(csvRadioButton);// Voeg de radio buttons toe aan het inputPanel
        inputPanel.add(txtRadioButton);// Voeg de radio buttons toe aan het inputPanel
        inputPanel.add(txtRadioButton);    // Voeg de radio buttons toe aan het inputPanel
        


        // Voeg radio buttons toe voor het kiezen van rapporttype (jaarlijks of per kwartaal)
        JPanel reportTypePanel = new JPanel();
        reportTypePanel.add(yearlyReportRadioButton);
        reportTypePanel.add(quarterlyReportRadioButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(financialTable), BorderLayout.CENTER);
    }

    private void addActionsToButtons() {
        generateReportButton.addActionListener(e -> {
            if (quarterlyReportRadioButton.isSelected()) {
                generateQuarterlyReport();
            } else if (yearlyReportRadioButton.isSelected()) {
                generateYearlyReport();
            }
        });
        generateReportButton.addActionListener(e -> generateReport());
        saveReportButton.addActionListener(e -> saveReportToFile()); // Actie toevoegen voor opslaanknop
    }
    
    // Methode om het rapport op te slaan
private void saveReportToFile() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Report As...");
    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        try {
            FileWriter fw = new FileWriter(fileToSave);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    bw.write(tableModel.getValueAt(i, j).toString());
                    if (csvRadioButton.isSelected()) { // CSV
                        if (j < tableModel.getColumnCount() - 1) {
                            bw.write(","); // CSV-scheidingsteken
                        }
                    } else if (txtRadioButton.isSelected()) { // TXT
                        if (j < tableModel.getColumnCount() - 1) {
                            bw.write("\t"); // Tab voor TXT-bestanden
                        }
                    }
                }
                bw.newLine();
            }

            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(this, "Report saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
     
   // Methode om het rapport te genereren
   @Override
   public void generateReport() {
    String selectedYear = (String) yearComboBox.getSelectedItem();
    String selectedQuarter = (String) quarterComboBox.getSelectedItem();

    String startDate = DateFactory.calculateStartDate(selectedYear, selectedQuarter);
    String endDate = DateFactory.calculateEndDate(selectedYear, selectedQuarter);

    generateReportWithDates(startDate, endDate);
}

   // Deze methode doet het eigenlijke werk, maar is niet onderdeel van de interface
   public void generateReportWithDates(String startDate, String endDate) {
    // Maak een nieuw DbConnect object
    try {
        DbConnect dbConnect = new DbConnect();
        Connection conn = dbConnect.getConnection(); // Haal de verbinding op

        String sql = "SELECT c.customerName, SUM(od.quantityOrdered * od.priceEach) AS totalSales "
                   + "FROM customers c "
                   + "JOIN orders o ON c.customerNumber = o.customerNumber "
                   + "JOIN orderdetails od ON o.orderNumber = od.orderNumber "
                   + "WHERE o.orderDate BETWEEN ? AND ? "
                   + "GROUP BY c.customerName "
                   + "ORDER BY totalSales DESC;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, startDate);
        pstmt.setString(2, endDate);
        ResultSet rs = pstmt.executeQuery();

        tableModel.setRowCount(0); // Clear the table for new data
        while (rs.next()) {
            tableModel.addRow(new Object[]{rs.getString("customerName"), rs.getBigDecimal("totalSales")});
        }

        // Sluit ResultSet, PreparedStatement, en Connection
        rs.close();
        pstmt.close();
        conn.close();

    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }}

        private void generateQuarterlyReport() {
            String selectedYear = (String) yearComboBox.getSelectedItem();
            String selectedQuarter = (String) quarterComboBox.getSelectedItem();
        
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
        
private void generateYearlyReport() {
    String selectedYear = (String) yearComboBox.getSelectedItem();

    String startDate = selectedYear + "-01-01"; // Begin van het jaar
    String endDate = selectedYear + "-12-31";   // Einde van het jaar

    generateReportWithDates(startDate, endDate);
}
   }