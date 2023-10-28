// Created by Sindre

package com.oap200.app.views;

import javax.swing.*;

import com.oap200.app.models.OrdersDAO;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private JTextArea textArea;
    private OrderManagementPanel orderPanel; // Declare the OrderManagementPanel
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
        orderPanel = new OrderManagementPanel(); // Initialize the OrderManagementPanel
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(orderPanel, BorderLayout.SOUTH); // Add the panel to the MainFrame

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
            OrdersDAO dao = new OrdersDAO();
            List<String> ordersList = dao.fetchOrders();
            orderPanel.displayOrders(ordersList); // Display orders in the orderPanel
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(productButton);
        topPanel.add(orderButton); // Added both buttons to the same top panel

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
}
