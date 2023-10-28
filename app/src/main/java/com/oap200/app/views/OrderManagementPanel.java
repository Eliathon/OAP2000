package com.oap200.app.views;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.List;

public class OrderManagementPanel extends JPanel {

    private JTextArea textArea = new JTextArea();

    public OrderManagementPanel() {
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void displayOrders(List<String> orders) {
        textArea.setText(""); // Clear existing content
        for (String order : orders) {
            textArea.append(order + "\n");
        }
        revalidate();
        repaint();
    }
}
