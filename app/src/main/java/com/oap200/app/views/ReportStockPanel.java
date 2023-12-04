package com.oap200.app.views;

import com.oap200.app.utils.PrintManager;
import com.oap200.app.Interfaces.ReportGenerator;
import com.oap200.app.controllers.ReportStockController;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

/**
 * Panel class responsible for displaying stock reports.
 * It interacts with the StockController to retrieve and display stock data.
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
     * Initializes the components of the panel such as buttons, table, and search field.
     */
    private void initializeComponents() {
       // generateReportButton = ButtonBuilder.createButton("Generate Stock Report", this::generateReport);
       // printButton = ButtonBuilder.createButton("Print Report", this::handlePrintAction);
        generateReportButton = ButtonBuilder.createButton("Search", () -> generateReport());
        printButton = ButtonBuilder.createButton("Print", () -> handlePrintAction());


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

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Search:"));
        inputPanel.add(searchField);
        inputPanel.add(generateReportButton);
        inputPanel.add(printButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);
    }

    /**
     * Handles the action of printing the stock report.
     */
    private void handlePrintAction() {
        if (PrintManager.isPrinting()) {
            return;
        }
        PrintManager.printTable(reportTable);
    }

    /**
     * Generates and displays the stock report based on the provided search criteria.
     */
   @Override
public void generateReport() {
    String searchText = (searchField != null) ? searchField.getText().toLowerCase() : "";
    ReportStockController controller = new ReportStockController();

    DefaultTableModel tableModel = (DefaultTableModel) reportTable.getModel();
    tableModel.setRowCount(0);

    try {
        List<Object[]> stockData = controller.getStockData(searchText);
        for (Object[] row : stockData) {
            tableModel.addRow(row);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}