package com.oap200.app.views;

import com.oap200.app.utils.DbConnect;
import com.oap200.app.utils.PrintManager;
import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.sql.*;

/**
 * ReportStockPanel is responsible for generating and displaying stock reports.
 * It provides functionality to search and print stock data.
 *
 * @author Dirkje Jansje van der Poel
 */
public class ReportStockPanel extends JPanel implements ReportGenerator {
    private JButton generateReportButton, printButton;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    /**
     * Constructs the ReportStockPanel with layout and component initialization.
     */
    public ReportStockPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
        generateReport();
    }

    /**
     * Initializes the components of the panel.
     */
    private void initializeComponents() {
       // generateReportButton = ButtonBuilder.createButton("Generate Stock Report", this::generateReport);
       // printButton = ButtonBuilder.createButton("Print Report", this::handlePrintAction);
        generateReportButton = ButtonBuilder.createButton("Generate Report", () -> generateReport());
        printButton = ButtonBuilder.createButton("Print Report", () -> handlePrintAction());


        searchField = new JTextField(20);
        searchField.addActionListener(e -> generateReport());

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
     * Generates the report based on the search criteria.
     */
    @Override
    public void generateReport() {
        String searchText = (searchField != null) ? searchField.getText().toLowerCase() : "";        DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
        tableModel.setRowCount(0);

        try (DbConnect dbConnect = new DbConnect();
             Connection conn = dbConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT productCode, productName, productLine, quantityInStock, buyPrice " +
                     "FROM products " +
                     "WHERE LOWER(productName) LIKE ? OR LOWER(productLine) LIKE ? " +
                     "ORDER BY quantityInStock DESC;")) {
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("productCode"),
                        rs.getString("productName"),
                        rs.getString("productLine"),
                        rs.getInt("quantityInStock"),
                        rs.getDouble("buyPrice")
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
