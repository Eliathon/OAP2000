package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderManagementPanel extends JPanel {

    private JTextArea textArea = new JTextArea(10, 40); // Just an example size.

    public OrderManagementPanel() {
        setLayout(new BorderLayout()); // Example layout manager
        add(new JScrollPane(textArea), BorderLayout.CENTER); // Add textArea inside a scroll pane for better UX
    }

    public void displayOrders(List<String> orders) {
        SwingUtilities.invokeLater(() -> {
            textArea.setText(""); // Clear existing content
            for (String order : orders) {
                textArea.append(order + "\n");
            }
        });
    }
}
