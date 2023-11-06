package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class PaymentManagement {
    private JFrame frame;
    private JPanel panel;
    private JButton viewButton;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField customerNumberField;
    private JTextField checkNumberField;
    private JTextField paymentDateField;
    private JTextField amountField;
    String customerNumberInput = customerNumberField.getText();
    String checkNumberInput = checkNumberField.getText();
    private JTextArea textArea = new JTextArea();

    private Connection connection;


public void start() {

    frame = new JFrame("Payment Management");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1100, 600); // Økt høyden for å plassere flere komponenter
    frame.setLayout(new BorderLayout());



}
}



