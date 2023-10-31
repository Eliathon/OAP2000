//Created by Kristian

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class EmployeeManagement {
    private JFrame frame;
    private Connection connection;
    private JTable table;
    private JTextField nameField, ageField;
    private JButton addButton, updateButton, deleteButton, refreshButton;

    public EmployeeManagement() {
        connectDatabase();
        createUI();
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:employees.db");
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS employees(id INTEGER PRIMARY KEY, name TEXT, age INTEGER)");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUI() {
        frame = new JFrame("Employee Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        nameField = new JTextField(10);
        ageField = new JTextField(3);
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(refreshButton);

        table = new JTable();
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        refreshButton.addActionListener(e -> refreshTable());

        frame.setVisible(true);
    }

    private void refreshTable() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM employees");
            ResultSet rs = ps.executeQuery();
            Vector<String> columnNames = new Vector<>();
            columnNames.add("ID");
            columnNames.add("Name");
            columnNames.add("Age");
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getInt("age"));
                data.add(row);
            }
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEmployee() {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (name, age) VALUES (?, ?)");
            ps.setString(1, nameField.getText());
            ps.setInt(2, Integer.parseInt(ageField.getText()));
            ps.execute();
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        if (table.getSelectedRow() != -1) {
            int id = (int) table.getValueAt(table.getSelectedRow(), 0);
            try {
                PreparedStatement ps = connection.prepareStatement("UPDATE employees SET name = ?, age = ? WHERE id = ?");
                ps.setString(1, nameField.getText());
                ps.setInt(2, Integer.parseInt(ageField.getText()));
                ps.setInt(3, id);
                ps.execute();
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteEmployee() {
        if (table.getSelectedRow() != -1) {
            int id = (int) table.getValueAt(table.getSelectedRow(), 0);
            try {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
                ps.setInt(1, id);
                ps.execute();
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        SwingUtilities.invokeLater(() -> new EmployeeManagement());
    }

    public static void main(String[] args) {
        new EmployeeManagement().start();
    }
}

