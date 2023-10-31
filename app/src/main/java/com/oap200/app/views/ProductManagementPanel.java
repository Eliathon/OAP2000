// Created by Sindre

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductManagementPanel {

    private JFrame frame;
    private JPanel panel;
    private JButton addButton;
    private JTextField productNameField;

    public void start() {
  frame = new JFrame("Product Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField();

        addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Legg til koden her for å håndtere når knappen blir trykket
                String productName = productNameField.getText();
                // Gjør noe med produktet, for eksempel lagre det i en database
                System.out.println("Lagt til produkt: " + productName);
            }
        });

        panel.add(productNameLabel);
        panel.add(productNameField);
        panel.add(addButton);

        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        ProductManagementPanel productManagementPanel = new ProductManagementPanel();
        productManagementPanel.start();
    }
    }


