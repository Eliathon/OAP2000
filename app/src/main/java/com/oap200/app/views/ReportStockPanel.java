// Created by Dirkje J. van der Poel
package com.oap200.app.views;

import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.ButtonBuilder; // Import the ButtonBuilder class
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.awt.*;


public class ReportStockPanel extends JPanel implements ReportGenerator {
    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    public ReportStockPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        addActionsToButtons();
    }

    private void initializeComponents() {
        generateReportButton = ButtonBuilder.createStyledButton("Generate Stock Report", this::generateReport);
        printButton = ButtonBuilder.createStyledButton("Print Report", this::handlePrintAction);

        searchField = new JTextField(20); 
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
    }

    private void addActionsToButtons() {
        generateReportButton.addActionListener(e -> generateReport());
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
        DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
        tableModel.setRowCount(0);  // Clear existing rows
    
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            DbConnect dbConnect = new DbConnect(); // Create a new instance
             conn = dbConnect.getConnection(); // Get the connection
       
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
  }

