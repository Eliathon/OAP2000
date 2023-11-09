package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class PaymentManagement extends JPanel {
    private JFrame frame;
    private JPanel panel;
    private JButton viewButton;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField customerNumberField;
    private JTextField checkNumberField;
    private JTextField paymentDateField;
    private JTextField amountField;
    private JTextArea textArea = new JTextArea();

    private Connection connection;

    public PaymentManagement() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        frame = new JFrame("Payment Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        customerNumberField = new JTextField(10);
        checkNumberField = new JTextField(10);
        paymentDateField = new JTextField(10);
        amountField = new JTextField(10);

        viewButton = new JButton("View Payments");
        addButton = new JButton("Add Payment");
        deleteButton = new JButton("Delete Payment");

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPayments();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPayment();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePayment();
            }
        });

        panel.add(new JLabel("Customer Number:"));
        panel.add(customerNumberField);
        panel.add(new JLabel("Check Number:"));
        panel.add(checkNumberField);
        panel.add(new JLabel("Payment Date (yyyy-MM-dd):"));
        panel.add(paymentDateField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(viewButton);
        panel.add(addButton);
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/classicmodels";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    }

    private void viewPayments() {
        String query = "SELECT * FROM payments";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            textArea.setText(""); // Clear the text area

            while (resultSet.next()) {
                int customerNumber = resultSet.getInt("customerNumber");
                String checkNumber = resultSet.getString("checkNumber");
                Date paymentDate = resultSet.getDate("paymentDate");
                double amount = resultSet.getDouble("amount");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String paymentDateStr = dateFormat.format(paymentDate);

                String paymentInfo = String.format(
                        "Customer Number: %d, Check Number: %s, Payment Date: %s, Amount: %.2f\n",
                        customerNumber, checkNumber, paymentDateStr, amount);

                textArea.append(paymentInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve payments.");
        }
    }

    private void addPayment() {
        String insertQuery = "INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            int customerNumber = Integer.parseInt(customerNumberField.getText());
            String checkNumber = checkNumberField.getText();
            Date paymentDate = Date.valueOf(paymentDateField.getText());
            double amount = Double.parseDouble(amountField.getText());

            preparedStatement.setInt(1, customerNumber);
            preparedStatement.setString(2, checkNumber);
            preparedStatement.setDate(3, paymentDate);
            preparedStatement.setDouble(4, amount);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Payment added successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add payment.");
            }

            // Clear the input fields
            customerNumberField.setText("");
            checkNumberField.setText("");
            paymentDateField.setText("");
            amountField.setText("");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add payment.");
        }
    }

    private void deletePayment() {
        String deleteQuery = "DELETE FROM payments WHERE customerNumber = ? AND checkNumber = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            int customerNumber = Integer.parseInt(customerNumberField.getText());
            String checkNumber = checkNumberField.getText();

            preparedStatement.setInt(1, customerNumber);
            preparedStatement.setString(2, checkNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Payment deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No matching payment found to delete.");
            }

            // Clear the input fields
            customerNumberField.setText("");
            checkNumberField.setText("");
            paymentDateField.setText("");
            amountField.setText("");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to delete payment.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PaymentManagement();
            }
        });
    }
}
