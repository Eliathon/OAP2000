package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
{
try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE ordernumber LIKE '%" + searchQuery + "%'");

            StringBuilder resultText = new StringBuilder();
            while (resultSet.next()) {
                String ordernumber = resultSet.getString("ordernumber");
                resultText.append(ordernumber).append("\n");
            }

            resultTextArea.setText(resultText.toString());

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }