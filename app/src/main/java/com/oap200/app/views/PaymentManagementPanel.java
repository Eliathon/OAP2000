package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.oap200.app.models.PaymentsDAO;
import com.oap200.app.utils.ButtonBuilder;

public class PaymentManagementPanel extends JPanel {

    private JTable paymentsTable;

    public PaymentManagementPanel(JFrame parentFrame) {
        initializeFields();

        // Set up the layout for the frame
        setLayout(new BorderLayout());

        // Initialize ButtonBuilder buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            // Get the top-level frame that contains this panel
            Window window = SwingUtilities.getWindowAncestor(PaymentManagementPanel.this);
            if (window != null) {
                window.dispose(); // This will close the window
            }
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            System.exit(0); // Exit the application
            openLoginPanel();
        });
        JButton viewButton = ButtonBuilder.createViewButton(() -> {
            /* Action for View Button */});
        JButton sortCustomerButton = new JButton("Sort by Customer Number");
        JButton sortCheckButton = new JButton("Sort by Check Number");
        JButton sortDateButton = new JButton("Sort by Payment Date");
        JButton sortAmountButton = new JButton("Sort by Amount");

        sortCustomerButton.addActionListener(e -> viewSortedData("customerNumber"));
        sortCheckButton.addActionListener(e -> viewSortedData("checkNumber"));
        sortDateButton.addActionListener(e -> viewSortedData("paymentDate"));
        sortAmountButton.addActionListener(e -> viewSortedData("amount"));

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Employee
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Payments", null, panel1, "Click to view");

        // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Main panel for the frame
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        // Adding panels to this panel instead of the frame's content pane
        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        // Initialize the table
        paymentsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        // Correct this line to add the scrollPane to the CENTER instead of EAST
        panel1.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> viewPayments());
        viewButton.addActionListener(e -> viewCheck());
        viewButton.addActionListener(e -> viewDate());
        viewButton.addActionListener(e -> viewAmount());
    }

    private void viewPayments() {
        PaymentsDAO PaymentsDAO = new PaymentsDAO();
        List<String[]> paymentsList = PaymentsDAO.fetchPayments();
        String[] columnNames = { "Customer Number", "Checknumber", "Payment Date", "Amount" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : paymentsList) {
            model.addRow(row);
        }
        paymentsTable.setModel(model);
    }

    private void viewSortedData(String sortByColumn) {
        PaymentsDAO paymentsDAO = new PaymentsDAO();
        List<String[]> paymentsList = paymentsDAO.fetchPaymentsOrderedBy(sortByColumn);

        String[] columnNames = { "Customer Number", "Checknumber", "Payment Date", "Amount" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String[] row : paymentsList) {
            model.addRow(row);
        }

        paymentsTable.setModel(model);
    }

    private void viewCheck() {
        PaymentsDAO paymentsDAO = new PaymentsDAO();
        List<String[]> paymentsList = paymentsDAO.fetchPayments();

        // Sort paymentsList based on Checknumber (assuming it's a numeric value)
        paymentsList.sort(Comparator.comparingDouble(row -> Double.parseDouble(row[1])));

        String[] columnNames = { "Customer Number", "Checknumber", "Payment Date", "Amount" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String[] row : paymentsList) {
            model.addRow(row);
        }

        paymentsTable.setModel(model);
    }

    private void viewDate() {
        PaymentsDAO paymentsDAO = new PaymentsDAO();
        List<String[]> paymentsList = paymentsDAO.fetchPayments();

        // Sort paymentsList based on Payment Date (assuming it's a date string in a
        // sortable format)
        paymentsList.sort(Comparator.comparing(row -> LocalDate.parse(row[2])));

        String[] columnNames = { "Customer Number", "Checknumber", "Payment Date", "Amount" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String[] row : paymentsList) {
            model.addRow(row);
        }

        paymentsTable.setModel(model);
    }

    private void viewAmount() {
        PaymentsDAO paymentsDAO = new PaymentsDAO();
        List<String[]> paymentsList = paymentsDAO.fetchPayments();

        // Sort paymentsList based on Amount (assuming it's a numeric value)
        paymentsList.sort(Comparator.comparingDouble(row -> Double.parseDouble(row[3])));

        String[] columnNames = { "Customer Number", "Checknumber", "Payment Date", "Amount" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String[] row : paymentsList) {
            model.addRow(row);
        }

        paymentsTable.setModel(model);
    }

    private void initializeFields() {
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);
        new JTextField(10);

    }

    private void openLoginPanel() {
        JFrame loginFrame = new JFrame("Login");
        LoginPanel loginPanel = new LoginPanel();
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Button weight

        // Adding the "Customer Number" sorting button
        JButton sortCustomerButton = new JButton("Sort by Customer Number");
        sortCustomerButton.addActionListener(e -> viewSortedData("customerNumber"));
        inputPanel.add(sortCustomerButton, gbc);

        // Adding the "Check Number" sorting button
        JButton sortCheckButton = new JButton("Sort by Check Number");
        sortCheckButton.addActionListener(e -> viewSortedData("checkNumber"));
        gbc.gridx = 1;
        inputPanel.add(sortCheckButton, gbc);

        // Adding the "Payment Date" sorting button
        JButton sortDateButton = new JButton("Sort by Payment Date");
        sortDateButton.addActionListener(e -> viewSortedData("paymentDate"));
        gbc.gridx = 2;
        inputPanel.add(sortDateButton, gbc);

        // Adding the "Amount" sorting button
        JButton sortAmountButton = new JButton("Sort by Amount");
        sortAmountButton.addActionListener(e -> viewSortedData("amount"));
        gbc.gridx = 3;
        inputPanel.add(sortAmountButton, gbc);

        // Adjusting button size based on header size
        Dimension buttonSize = sortCustomerButton.getPreferredSize();
        sortCheckButton.setPreferredSize(buttonSize);
        sortDateButton.setPreferredSize(buttonSize);
        sortAmountButton.setPreferredSize(buttonSize);

        panelView.add(inputPanel, BorderLayout.NORTH);

        // Now, create the scrollPane with the paymentsTable right here:
        JScrollPane scrollPane = new JScrollPane(paymentsTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame paymentFrame = new JFrame("Payment View");
            PaymentManagementPanel frame = new PaymentManagementPanel(paymentFrame);
            paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            paymentFrame.setTitle("Payment Management");
            paymentFrame.pack();
            paymentFrame.setVisible(true);
        });
    }
}