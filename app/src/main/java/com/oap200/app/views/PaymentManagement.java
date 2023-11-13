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
    private JPanel addPanel;
    private JPanel updatePanel;
    private JPanel deletePanel;
    private JPanel mainPanel;

    private JButton viewButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;

    private JComboBox<String> checkNumberComboBox;

    private JTextField customerNumberField;
    private JTextField checkNumberField;
    private JTextField paymentDateField;
    private JTextField amountField;
    private JTextField paymentsField;
    private JTable resultTable;
   
    JTextArea resultMessageArea;
    

public void start() {

    JButton addButton = new JButton("Add Payments");
    updateButton = new JButton("Update Payments");
 deleteButton = new JButton("Delete Payments");
    JTextArea textArea = new JTextArea();
    
    JScrollPane textAreaScrollPane;


    
    JScrollPane messageScrollPane;


        


     Connection connection;

        
    
    
        // ... Rest of your code
    
    



    frame = new JFrame("Payment Management");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
    frame.setLayout(new BorderLayout());

    mainPanel = new JPanel(new BorderLayout());

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
        bottomPanel.setLayout(new BorderLayout());

       JLabel customerNumberLabel = new JLabel("Customer Number Field:");
        customerNumberField = new JTextField();
    customerNumberField.setPreferredSize(new Dimension(65,35));


        JLabel checkNumberLabel = new JLabel("Check Number Field:");
        checkNumberField = new JTextField();
checkNumberField.setPreferredSize(new Dimension(140,35));


        JLabel paymentDateLabel = new JLabel("Payment Date Field:");
        paymentDateField = new JTextField();
paymentDateField.setPreferredSize(new Dimension(130,35));


        JLabel amountLabel = new JLabel("Amount Field:");
        amountField = new JTextField();
amountField.setPreferredSize(new Dimension(130,35));


        JLabel paymentsLabel = new JLabel("Payments Field:");
        paymentsField = new JTextField();

bottomPanel.add(tableScrollPane, BorderLayout.SOUTH);


        viewButton = new JButton("View Payments");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPayments();
            }
        });

        addButton = new JButton("Add Payments"); // Add this line to instantiate addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPanel();
                
               
            }
        });
        
        

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdatePanel();
                
                
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeletePanel();
                deletePayments();
                
            }
        });
        buttonPanel.add(updateButton);


topPanel.add(viewButton, BorderLayout.NORTH);
topPanel.add(addButton, BorderLayout.NORTH);
topPanel.add(deleteButton, BorderLayout.NORTH);
topPanel.add(updateButton, BorderLayout.NORTH);





centerPanel.add(messageScrollPane, BorderLayout.CENTER);
bottomPanel.add(tableScrollPane, BorderLayout.CENTER);
centerPanel.add(textAreaScrollPane, BorderLayout.CENTER);

mainPanel.add(customerNumberLabel);
    mainPanel.add(customerNumberField);
    mainPanel.add(checkNumberLabel);
    mainPanel.add(checkNumberField);
    mainPanel.add(paymentDateLabel);
    mainPanel.add(paymentDateField);
    mainPanel.add(amountLabel);
    mainPanel.add(amountField);

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
    } catch (Exception ex) {
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
    // Retrieve selected checkNumber from JComboBox
    String selectedCheckNumber = (String) checkNumberComboBox.getSelectedItem();

    String customerNumberInput = customerNumberField.getText();
    String paymentDateInput = paymentDateField.getText();
    String amountInput = amountField.getText();

    try {
        int customerNumber = Integer.parseInt(customerNumberInput);
        java.sql.Date paymentDate = java.sql.Date.valueOf(paymentDateInput);
        BigDecimal amount = new BigDecimal(amountInput);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
        PreparedStatement preparedStatement = null;
        int rowsUpdated = 0;

        if (!customerNumberInput.isEmpty() && selectedCheckNumber != null) {
            preparedStatement = connection.prepareStatement(
                    "UPDATE payments SET customerNumber = ?, paymentDate = ?, amount = ? WHERE checkNumber = ?"
            );
            preparedStatement.setInt(1, customerNumber);
            preparedStatement.setDate(2, paymentDate);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setString(4, selectedCheckNumber);
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



private void showAddPanel() {
    clearInputFields();
    addPanel = createInputPanel("Add Payments");
    int result = JOptionPane.showConfirmDialog(frame, addPanel, "Add Payments", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        addPayments();
    }
}

private void showUpdatePanel() {
    clearInputFields();
    updatePanel = createInputPanel("Update Payments");
    int result = JOptionPane.showConfirmDialog(frame, updatePanel, "Update Payments", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        updatePayments();
    }
}

private void showDeletePanel() {
    clearInputFields();
    deletePanel = createInputPanel("Delete Payments");
    int result = JOptionPane.showConfirmDialog(frame, deletePanel, "Delete Payments", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        deletePayments();
    }
}

private JPanel createInputPanel(String title) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

    // Use the original fields defined at the class level
    panel.add(new JLabel("Customer Number:"));
    panel.add(customerNumberField);

    // Add a JComboBox for Check Numbers
    checkNumberComboBox = new JComboBox<>();

    populateCheckNumbers(checkNumberComboBox); // Populate the JComboBox with existing check numbers
    panel.add(new JLabel("Check Number:"));
    panel.add(checkNumberComboBox);

    panel.add(new JLabel("Payment Date:"));
    panel.add(paymentDateField);
    panel.add(new JLabel("Amount:"));
    panel.add(amountField);

    return panel;
}

private void populateCheckNumbers(JComboBox<String> checkNumberComboBox) {
    try {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT checkNumber FROM payments");

        while (resultSet.next()) {
            String checkNumber = resultSet.getString("checkNumber");
            checkNumberComboBox.addItem(checkNumber);
        }

        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}


private void clearInputFields() {
    // Clear the input fields before showing the input panel
    // You may need to adapt this method based on your actual fields
    customerNumberField.setText("");
    checkNumberField.setText("");
    paymentDateField.setText("");
    amountField.setText("");
}



public static void main(String[] args) {
    PaymentManagement paymentManagement = new PaymentManagement();
    paymentManagement.start();
}
}
