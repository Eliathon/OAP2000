package com.oap200.app.views;

import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.controllers.ProductController;
import com.oap200.app.controllers.SQLController;
import com.oap200.app.models.ProductsDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

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
    private SQLController sqlController;

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

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addTopPanelButtons(topPanel, gbc);

        add(topPanel, BorderLayout.NORTH);

        sqlQueryArea = new JTextArea(5, 30);
        JScrollPane sqlQueryScrollPane = new JScrollPane(sqlQueryArea);

        sqlResultArea = new JTextArea(10, 30);
        sqlResultArea.setEditable(false);
        JScrollPane sqlResultScrollPane = new JScrollPane(sqlResultArea);

        JButton executeSqlButton = new JButton("Execute SQL");
        executeSqlButton.addActionListener(e -> {
            try {
                executeSqlQuery();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        JPanel sqlPanel = new JPanel(new BorderLayout());
        sqlPanel.add(sqlQueryScrollPane, BorderLayout.NORTH);
        sqlPanel.add(executeSqlButton, BorderLayout.CENTER);
        sqlPanel.add(sqlResultScrollPane, BorderLayout.SOUTH);
        add(sqlPanel, BorderLayout.SOUTH);

        this.sqlController = new SQLController();

        productController = new ProductController(new ProductsDAO());

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::checkAndNotifyLowStock, 0, 1, TimeUnit.HOURS);
    }

    private void addTopPanelButtons(JPanel panel, GridBagConstraints gbc) {
        addButton(panel, gbc, 0, "Product Management", () -> openProductManagementPanel());
        addButton(panel, gbc, 1, "Order Management", () -> openOrderManagementPanel());
        addButton(panel, gbc, 2, "Employee Management", () -> openEmployeeManagementPanel());
        addButton(panel, gbc, 3, "Payment Management", () -> openPaymentManagementPanel());
        addButton(panel, gbc, 4, "Customer Management", () -> openCustomerManagementPanel());
        addButton(panel, gbc, 5, "Reports", () -> openReportManagementPanel());
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, int gridx, String text, Runnable action) {
        gbc.gridx = gridx;
        gbc.gridy = 0;
        JButton button = ButtonBuilder.createStyledButton(text, action);
        panel.add(button, gbc);
    }

    private void executeSqlQuery() throws Exception {
        // Your existing code...
        String sqlQuery = sqlQueryArea.getText().trim();
        if (sqlQuery.isEmpty()) {
            sqlResultArea.setText("Please enter a SQL query.");
            return;
        }

        // Use sqlController here
        String result = sqlController.executeQuery(sqlQuery);
        sqlResultArea.setText(result);
    }

    private void checkAndNotifyLowStock() {
        try {
            List<String> lowStockItems = productController.checkLowStock();
            if (!lowStockItems.isEmpty()) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                        "Low stock for items: " + String.join(", ", lowStockItems)));
            } else {
                SwingUtilities
                        .invokeLater(() -> JOptionPane.showMessageDialog(this, "All items have sufficient stock."));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(
                    () -> JOptionPane.showMessageDialog(this, "Failed to check for low stock due to a database error.",
                            "Database Error", JOptionPane.ERROR_MESSAGE));
        }
    }

    private void initPosition() {
        int lastX = prefs.getInt(X_POS_KEY, -1);
        int lastY = prefs.getInt(Y_POS_KEY, -1);
        int lastWidth = prefs.getInt(WIDTH_KEY, -1);
        int lastHeight = prefs.getInt(HEIGHT_KEY, -1);
        boolean wasMaximized = prefs.getBoolean(MAXIMIZED_KEY, false);

        setInitialPosition(lastX, lastY, lastWidth, lastHeight, wasMaximized);
    }

    private void setInitialPosition(int x, int y, int width, int height, boolean maximized) {
        if (x != -1 && y != -1) {
            setLocation(x, y);
        } else {
            setLocationRelativeTo(null);
        }

        if (width != -1 && height != -1) {
            setSize(width, height);
        }

        if (maximized) {
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

    // Open different management panels
    private void openProductManagementPanel() {
        openManagementPanel("Product Management", new ProductManagementPanel(this));
    }

    private void openOrderManagementPanel() {
        openManagementPanel("Order Management", new OrderManagementPanel(this));
    }

    private void openEmployeeManagementPanel() {
        openManagementPanel("Employee Management", new EmployeeManagementPanel(this));
    }

    private void openPaymentManagementPanel() {
        openManagementPanel("Payment Management", new PaymentManagementPanel(this));
    }

    private void openCustomerManagementPanel() {
        openManagementPanel("Customer Management", new CustomerManagementPanel(this));
    }

    private void openReportManagementPanel() {
        openManagementPanel("Reports", new ReportManagementPanel());
    }

    private void openManagementPanel(String title, JPanel panel) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setContentPane(panel);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
