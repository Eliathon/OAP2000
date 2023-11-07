package com.oap200.app.views;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class PaymentManagement {
     
     private JFrame frame;
    private JPanel panel;
    private JButton viewButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField customerNumberField;
    private JTextField checkNumberField;
    private JTextField paymentDateField;
    private JTextField amountField;
    private JTextField paymentsField;
    private JTable resultTable;




    
    private JTextArea textArea = new JTextArea();
    
    private JScrollPane textAreaScrollPane;


    private JTextArea resultMessageArea;
    private JScrollPane messageScrollPane;


        


    private Connection connection;

        
    
    
        // ... Rest of your code
    
    

public void start() {

    frame = new JFrame("Payment Management");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
    frame.setLayout(new BorderLayout());

    textAreaScrollPane = new JScrollPane(textArea);
    textAreaScrollPane.setPreferredSize(new Dimension(800, 100));


        resultTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(resultTable);
        tableScrollPane.setPreferredSize(new Dimension(1900, 600));

        resultMessageArea = new JTextArea();
        resultMessageArea.setEditable(false);
        messageScrollPane = new JScrollPane(resultMessageArea);
        messageScrollPane.setPreferredSize(new Dimension(800, 100));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setLayout(new FlowLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setLayout(new FlowLayout());

       JLabel customerNumberLabel = new JLabel("Customer Number Field:");
        customerNumberField = new JTextField();

        JLabel checkNumberLabel = new JLabel("Check Number Field:");
        checkNumberField = new JTextField();

        JLabel paymentDateLabel = new JLabel("Payment Date Field:");
        paymentDateField = new JTextField();

        JLabel amountLabel = new JLabel("Amount Field:");
        amountField = new JTextField();

        JLabel paymentsLabel = new JLabel("Payments Field:");
        paymentsField = new JTextField();




        viewButton = new JButton("View Payments");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPayments();
            }
        });

        addButton = new JButton("Add Payments");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPayments();
            }
        });

        deleteButton = new JButton("Delete Payments");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePayments();
            }
        });

                updateButton = new JButton("Update Payments");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePayments();
            }
        });
        buttonPanel.add(updateButton);



        topPanel.add(viewButton, BorderLayout.NORTH);
topPanel.add(addButton, BorderLayout.NORTH);
topPanel.add(deleteButton, BorderLayout.NORTH);
topPanel.add(updateButton, BorderLayout.NORTH);



centerPanel.add(resultMessageArea, BorderLayout.CENTER);
bottomPanel.add(resultTable, BorderLayout.SOUTH);

centerPanel.add(textAreaScrollPane, BorderLayout.CENTER);

    frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
       
        frame.setVisible(true);








}


private void viewPayments() {
    String searchQuery = paymentsField.getText();

    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM payments WHERE checkNumber LIKE '%" + searchQuery + "%'");

        // Opprett et dataobjekt for tabellen
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Customer Number");
        tableModel.addColumn("Check Number");
        tableModel.addColumn("Payment Date");
        tableModel.addColumn("Amount");
      

        // Fyll tabellen med data fra resultatsettet
        while (resultSet.next()) {
            String customerNumber = resultSet.getString("customerNumber");
            String checkNumber = resultSet.getString("checkNumber");
            String paymentDate = resultSet.getString("paymentDate");
            String amount = resultSet.getString("amount");
           
      

            tableModel.addRow(new Object[]{customerNumber, checkNumber, paymentDate, amount});
        }

        // Oppdater JTable med det nye datamodellen
        resultTable.setModel(tableModel);

        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


//Insert code here

private void addPayments() {
    String customerNumberInput = customerNumberField.getText();
    String checkNumberInput = checkNumberField.getText();
    String paymentDateInput = paymentDateField.getText();
    String amountInput = amountField.getText();

    try {
        int customerNumber = Integer.parseInt(customerNumberInput);
        String checkNumber = checkNumberInput;
        // Parse the date string to a Date object if it's in the correct format
        java.sql.Date paymentDate = java.sql.Date.valueOf(paymentDateInput);
        BigDecimal amount = new BigDecimal(amountInput);

        if (customerNumber > 0 && !checkNumber.isEmpty() && paymentDate != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO payments (customerNumber, checkNumber, paymentDate, amount) VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, customerNumber);
            preparedStatement.setString(2, checkNumber);
            preparedStatement.setDate(3, paymentDate);
            preparedStatement.setBigDecimal(4, amount);
            preparedStatement.executeUpdate();

            resultMessageArea.setText("Payment added successfully.");
            connection.close();
        } else {
            resultMessageArea.setText("Invalid input data. Please check the values.");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        resultMessageArea.setText("Failed to add payment.");
    } catch ( Exception ex) {
        resultMessageArea.setText("Invalid input data. Please check the values.");
    }
}


private void deletePayments() {
    String customerNumberInput = customerNumberField.getText();
    String checkNumberInput = checkNumberField.getText();
    String paymentDateInput = paymentDateField.getText();
    String amountInput = amountField.getText();

    try {
        int customerNumber = Integer.parseInt(customerNumberInput);
        String checkNumber = checkNumberInput;
        java.sql.Date paymentDate = java.sql.Date.valueOf(paymentDateInput);
        BigDecimal amount = new BigDecimal(amountInput);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM payments WHERE customerNumber = ? AND checkNumber = ? AND paymentDate = ? AND amount = ?"
        );
        preparedStatement.setInt(1, customerNumber);
        preparedStatement.setString(2, checkNumber);
        preparedStatement.setDate(3, paymentDate);
        preparedStatement.setBigDecimal(4, amount);
        int rowsDeleted = preparedStatement.executeUpdate();

        if (rowsDeleted > 0) {
            resultMessageArea.setText("Payment deleted successfully.");
        } else {
            resultMessageArea.setText("No matching payment found to delete.");
        }

        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        resultMessageArea.setText("Failed to delete payment.");
    } catch (Exception ex) {
        resultMessageArea.setText("Invalid input data. Please check the values.");
    }
}

private void updatePayments() {
    String customerNumberInput = customerNumberField.getText();
    String checkNumberInput = checkNumberField.getText();
    String paymentDateInput = paymentDateField.getText();
    String amountInput = amountField.getText();

    try {
        int customerNumber = Integer.parseInt(customerNumberInput);
        String checkNumber = checkNumberInput;
        java.sql.Date paymentDate = java.sql.Date.valueOf(paymentDateInput);
        BigDecimal amount = new BigDecimal(amountInput);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
        PreparedStatement preparedStatement = null;
        int rowsUpdated = 0;

        if (!customerNumberInput.isEmpty()) {
            preparedStatement = connection.prepareStatement(
                "UPDATE payments SET customerNumber = ? WHERE customerNumber = ?"
            );
            preparedStatement.setInt(1, customerNumber);
            preparedStatement.setInt(2, Integer.parseInt(customerNumberInput));
            rowsUpdated += preparedStatement.executeUpdate();
        }

        if (!checkNumberInput.isEmpty()) {
            preparedStatement = connection.prepareStatement(
                "UPDATE payments SET checkNumber = ? WHERE customerNumber = ?"
            );
            preparedStatement.setString(1, checkNumber);
            preparedStatement.setInt(2, customerNumber);
            rowsUpdated += preparedStatement.executeUpdate();
        }

        if (!paymentDateInput.isEmpty()) {
            preparedStatement = connection.prepareStatement(
                "UPDATE payments SET paymentDate = ? WHERE customerNumber = ?"
            );
            preparedStatement.setDate(1, paymentDate);
            preparedStatement.setInt(2, customerNumber);
            rowsUpdated += preparedStatement.executeUpdate();
        }

        if (!amountInput.isEmpty()) {
            preparedStatement = connection.prepareStatement(
                "UPDATE payments SET amount = ? WHERE customerNumber = ?"
            );
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, customerNumber);
            rowsUpdated += preparedStatement.executeUpdate();
        }

        if (rowsUpdated > 0) {
            resultMessageArea.setText("Payment(s) updated successfully.");
        } else {
            resultMessageArea.setText("No matching payment found to update.");
        }

        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        resultMessageArea.setText("Failed to update payment.");
    } catch (NumberFormatException ex) {
        resultMessageArea.setText("Invalid input data. Please check the values.");
    }
}






public static void main(String[] args) {
    PaymentManagement paymentManagement = new PaymentManagement();
    paymentManagement.start();
}
}


