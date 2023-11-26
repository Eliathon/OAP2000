// Created by Sindre and Johnny

package com.oap200.app.views;

import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.controllers.ProductController;
import com.oap200.app.controllers.SQLController;
import com.oap200.app.models.ProductsDAO;
import com.oap200.app.tabbedPanels.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

// Import for TabbedPanels

public class MainFrame extends JFrame {

    private JTextArea textArea;
    private Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
    private JTextArea sqlQueryArea;
    private JTextArea sqlResultArea;
    private ScheduledExecutorService executor;
    private ProductController productController;
    private final String X_POS_KEY = "xPos";
    private final String Y_POS_KEY = "yPos";
    private final String WIDTH_KEY = "width";
    private final String HEIGHT_KEY = "height";
    private final String MAXIMIZED_KEY = "maximized";

    public MainFrame() {
        setTitle("Swing MainFrame");
        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        initPosition();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                storePosition();
            }
        });

        JButton productButton = ButtonBuilder.createStyledButton("Product Management",
                () -> openProductManagementPanel());
        JButton orderButton = ButtonBuilder.createStyledButton("Order Management", () -> openOrderManagementPanel());
        JButton employeeButton = ButtonBuilder.createStyledButton("Employee Management",
                () -> openEmployeeManagementPanel());
        JButton paymentButton = ButtonBuilder.createStyledButton("Payment Management",
                () -> openPaymentManagementPanel());
        JButton reportsButton = ButtonBuilder.createStyledButton("Customer Management",
                () -> openCustomerManagementPanel());
        JButton notificationsButton = ButtonBuilder.createStyledButton("Notifications & Alerts", null);

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        topPanel.add(productButton, gbc);
        gbc.gridx++;
        topPanel.add(orderButton, gbc);
        gbc.gridx++;
        topPanel.add(employeeButton, gbc);
        gbc.gridx++;
        topPanel.add(paymentButton, gbc);
        gbc.gridx++;
        topPanel.add(reportsButton, gbc);
        gbc.gridx++;
        topPanel.add(notificationsButton, gbc);
        add(topPanel, BorderLayout.NORTH);

        // SQL Query Area
        sqlQueryArea = new JTextArea(5, 30);
        JScrollPane sqlQueryScrollPane = new JScrollPane(sqlQueryArea);

        // SQL Result Area
        sqlResultArea = new JTextArea(10, 30);
        sqlResultArea.setEditable(false);
        JScrollPane sqlResultScrollPane = new JScrollPane(sqlResultArea);

        // Execute SQL Button
        JButton executeSqlButton = new JButton("Execute SQL");
        executeSqlButton.addActionListener(e -> {
            try {
                executeSqlQuery();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        JPanel sqlPanel = new JPanel();
        sqlPanel.setLayout(new BorderLayout());
        sqlPanel.add(sqlQueryScrollPane, BorderLayout.NORTH);
        sqlPanel.add(executeSqlButton, BorderLayout.CENTER);
        sqlPanel.add(sqlResultScrollPane, BorderLayout.SOUTH);

        // Add SQL Panel to MainFrame
        add(sqlPanel, BorderLayout.SOUTH);

        productController = new ProductController();

        // Perform initial low stock check
        checkAndNotifyLowStock();

        // Setup ScheduledExecutorService
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::checkAndNotifyLowStock, 1, 1, TimeUnit.HOURS); // Check every hour
    }

    @Override
    public void dispose() {
        super.dispose();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown(); // Properly shutdown the executor
        }
    }

    private SQLController sqlController = new SQLController();

    private void executeSqlQuery() throws Exception {
        String sqlQuery = sqlQueryArea.getText().trim();
        if (sqlQuery.isEmpty()) {
            sqlResultArea.setText("Please enter a SQL query.");
            return;
        }
        String result = sqlController.executeQuery(sqlQuery);
        sqlResultArea.setText(result);
    }

    private void checkAndNotifyLowStock() {
        List<String> lowStockItems = productController.checkLowStock();

        if (!lowStockItems.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Low stock for items: " + String.join(", ", lowStockItems));
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "All items have sufficient stock.");
            });
        }
    }

    private void initPosition() {
        int lastX = prefs.getInt(X_POS_KEY, -1);
        int lastY = prefs.getInt(Y_POS_KEY, -1);
        int lastWidth = prefs.getInt(WIDTH_KEY, -1);
        int lastHeight = prefs.getInt(HEIGHT_KEY, -1);
        boolean wasMaximized = prefs.getBoolean(MAXIMIZED_KEY, false);

        if (lastX != -1 && lastY != -1) {
            setLocation(lastX, lastY);
        } else {
            setLocationRelativeTo(null);
        }

        if (lastWidth != -1 && lastHeight != -1) {
            setSize(lastWidth, lastHeight);
        }

        if (wasMaximized) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    private void storePosition() {
        prefs.putInt(X_POS_KEY, getX());
        prefs.putInt(Y_POS_KEY, getY());
        prefs.putInt(WIDTH_KEY, getWidth());
        prefs.putInt(HEIGHT_KEY, getHeight());
        prefs.putBoolean(MAXIMIZED_KEY, (getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH);
    }

    private void openProductManagementPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame productFrame = new JFrame("Product Management");
            productFrame.setContentPane(new TabbedProductPanel());
            productFrame.setSize(600, 400);
            productFrame.setLocationRelativeTo(null);
            productFrame.setVisible(true);
        });
    }

    private void openOrderManagementPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame orderFrame = new JFrame("Order Management");
            orderFrame.setContentPane(new TabbedOrderPanel());
            orderFrame.setSize(600, 400);
            orderFrame.setLocationRelativeTo(null);
            orderFrame.setVisible(true);
        });
    }

    private void openEmployeeManagementPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame employeeFrame = new JFrame("Employee Management");
            employeeFrame.setContentPane(new TabbedEmployeePanel());
            employeeFrame.setSize(600, 400);
            employeeFrame.setLocationRelativeTo(null);
            employeeFrame.setVisible(true);
        });
    }

    private void openPaymentManagementPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame paymentFrame = new JFrame("Payment Management");
            paymentFrame.setContentPane(new TabbedPaymentPanel());
            paymentFrame.setSize(600, 400);
            paymentFrame.setLocationRelativeTo(null);
            paymentFrame.setVisible(true);
        });
    }

    private void openCustomerManagementPanel() {
        SwingUtilities.invokeLater(() -> {
            JFrame customerFrame = new JFrame("Customer Management");
            customerFrame.setContentPane(new TabbedCustomerPanel());
            customerFrame.setSize(600, 400);
            customerFrame.setLocationRelativeTo(null);
            customerFrame.setVisible(true);
        });
    }

    public void start() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}