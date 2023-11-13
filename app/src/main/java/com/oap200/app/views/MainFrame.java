// Created by Sindre and Johnny

package com.oap200.app.views;

import javax.swing.*;
import com.oap200.app.utils.ButtonBuilder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private JTextArea textArea;
    private Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
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
    
        JButton productButton = ButtonBuilder.createStyledButton("Product Management", () -> {
            new ProductManagementPanel().start();
        });
        JButton orderButton = ButtonBuilder.createStyledButton("Order Management", () -> {
            new OrderManagementPanel().start();
        });
        JButton employeeButton = ButtonBuilder.createStyledButton("Employee Management", () -> {
            new EmployeeManagement().start();
        });
        JButton paymentButton = ButtonBuilder.createStyledButton("Payment Management", () -> {
            new PaymentManagement().start();
        });
        JButton reportsButton = ButtonBuilder.createStyledButton("Reports", null); // No action defined yet
        JButton notificationsButton = ButtonBuilder.createStyledButton("Notifications & Alerts", null); // No action defined yet

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        topPanel.add(productButton, gbc);
        topPanel.add(orderButton, gbc);
        topPanel.add(employeeButton, gbc);
        topPanel.add(paymentButton, gbc);
        topPanel.add(reportsButton, gbc);
        topPanel.add(notificationsButton, gbc);
        add(topPanel, BorderLayout.NORTH);
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void displayEmails(List<String> emails) {
        for (String email : emails) {
            textArea.append(email + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    public void start() {
    }
}