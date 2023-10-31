// Created by Kristian

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class EmployeeManagement {
    private JFrame frame;
    private Connection connection;
    private JTable table;
    private JTextField employeeNumberSearchField, searchField, firstNameField, lastNameField, emailField, jobTitleField;
    private JButton addButton, updateButton, deleteButton, refreshButton, searchButton;

    public EmployeeManagement() {
        connectDatabase();
        createUI();
    }

    private void connectDatabase() {
        String url = "jdbc:mysql://localhost:3306/classicmodels";
        String user = "root";
        String password = "";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createUI() {
        frame = new JFrame("Employee Management");
        frame.setSize(1100, 600); // Adjusted width for better layout
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new BorderLayout());
        
        // Separating the search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField employeeNumberSearchField = new JTextField(10);
        JButton employeeNumberSearchButton = new JButton("Search by Employee Number");
        searchField = new JTextField(15);
        searchButton = new JButton("Search by Last Name");
        
        searchPanel.add(new JLabel("Employee Number:"));
        searchPanel.add(employeeNumberSearchField);
        searchPanel.add(employeeNumberSearchButton);
        searchPanel.add(new JLabel("Last Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
    
        // Creating the add/update employee panel
        JPanel employeeInputPanel = new JPanel(new GridLayout(2, 7)); 
    
        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);
        emailField = new JTextField(20);
        jobTitleField = new JTextField(15);
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
    
        employeeInputPanel.add(new JLabel("First Name:"));
        employeeInputPanel.add(firstNameField);
        employeeInputPanel.add(new JLabel("Last Name:"));
        employeeInputPanel.add(lastNameField);
        employeeInputPanel.add(new JLabel("Email:"));
        employeeInputPanel.add(emailField);
        employeeInputPanel.add(new JLabel("Job Title:"));
        employeeInputPanel.add(jobTitleField);
        employeeInputPanel.add(addButton);
        employeeInputPanel.add(updateButton);
        employeeInputPanel.add(deleteButton);
        employeeInputPanel.add(refreshButton);
    
        // Add panels to the main input panel
        inputPanel.add(searchPanel, BorderLayout.NORTH);
        inputPanel.add(employeeInputPanel, BorderLayout.CENTER);
    
        table = new JTable();
        refreshTable();
    
        JScrollPane scrollPane = new JScrollPane(table);
    
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
    
        // Adjusted the search button's action to differentiate between employee number and last name search.
        employeeNumberSearchButton.addActionListener(e -> searchEmployeeByNumber(employeeNumberSearchField.getText()));
        searchButton.addActionListener(e -> searchEmployeeByLastName(searchField.getText()));
        addButton.addActionListener(e -> addEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        refreshButton.addActionListener(e -> refreshTable());
    
        frame.setVisible(true);
    }

    private void refreshTable() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, email, jobTitle FROM employees");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchEmployeeByNumber(String empNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, email, jobTitle FROM employees WHERE employeeNumber LIKE ?");
            ps.setString(1, "%" + empNumber + "%");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void searchEmployeeByLastName(String lastName) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, email, jobTitle FROM employees WHERE lastName LIKE ?");
            ps.setString(1, "%" + lastName + "%");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTableFromResultSet(ResultSet rs) throws SQLException {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Employee Number");
        columnNames.add("First Name");
        columnNames.add("Last Name");
        columnNames.add("Email");
        columnNames.add("Job Title");

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("employeeNumber"));
            row.add(rs.getString("firstName"));
            row.add(rs.getString("lastName"));
            row.add(rs.getString("email"));
            row.add(rs.getString("jobTitle"));
            data.add(row);
        }

        table.setModel(new DefaultTableModel(data, columnNames));
    }

    private void addEmployee() {
        JOptionPane.showMessageDialog(frame, "Adding employees functionality is not implemented yet.");
    }

    private void updateEmployee() {
        JOptionPane.showMessageDialog(frame, "Updating employees functionality is not implemented yet.");
    }

    private void deleteEmployee() {
        if (table.getSelectedRow() != -1) {
            int employeeNumber = (int) table.getValueAt(table.getSelectedRow(), 0);
            try {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM employees WHERE employeeNumber = ?");
                ps.setInt(1, employeeNumber);
                ps.execute();
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagement());
    }
}
