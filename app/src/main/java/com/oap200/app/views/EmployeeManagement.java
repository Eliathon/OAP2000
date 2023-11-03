// Created by Kristian

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class EmployeeManagement {
    private JFrame frame;
    private Connection connection;
    private JTable table;
    private JTextField employeeIdField, employeeNumberSearchField, searchField, firstNameField, lastNameField, extensionField, emailField, officeCodeField, reportsToField, jobTitleField;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createUI() {
        frame = new JFrame("Employee Management");
        frame.setSize(1100, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        JPanel inputPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        employeeNumberSearchField = new JTextField(10);
        JButton employeeNumberSearchButton = new JButton("Search by Employee Number");
        searchField = new JTextField(15);
        searchButton = new JButton("Search by Last Name");

        searchPanel.add(new JLabel("Employee Number:"));
        searchPanel.add(employeeNumberSearchField);
        searchPanel.add(employeeNumberSearchButton);
        searchPanel.add(new JLabel("Last Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel employeeInputPanel = new JPanel(new GridLayout(10, 10)); 

        employeeIdField = new JTextField(10);
        lastNameField = new JTextField(10);
        firstNameField = new JTextField(10);
        extensionField = new JTextField(10);
        emailField = new JTextField(20);
        officeCodeField = new JTextField(10);
        reportsToField = new JTextField(10);
        jobTitleField = new JTextField(15);
        
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        refreshButton.addActionListener(e -> refreshTable());

        
        

        employeeInputPanel.add(new JLabel("Employee ID:"));
        employeeInputPanel.add(employeeIdField);
        employeeInputPanel.add(new JLabel("First Name:"));
        employeeInputPanel.add(firstNameField);
        employeeInputPanel.add(new JLabel("Last Name:"));
        employeeInputPanel.add(lastNameField);
        employeeInputPanel.add(new JLabel("Extension"));
        employeeInputPanel.add(extensionField);
        employeeInputPanel.add(new JLabel("Email:"));
        employeeInputPanel.add(emailField);
        employeeInputPanel.add(new JLabel("Office Code"));
        employeeInputPanel.add(officeCodeField);
        employeeInputPanel.add(new JLabel("Reports To"));
        employeeInputPanel.add(reportsToField);
        employeeInputPanel.add(new JLabel("Job Title:"));
        employeeInputPanel.add(jobTitleField);
        employeeInputPanel.add(addButton);
        employeeInputPanel.add(updateButton);
        employeeInputPanel.add(deleteButton);
        employeeInputPanel.add(refreshButton);

        inputPanel.add(searchPanel, BorderLayout.NORTH);
        inputPanel.add(employeeInputPanel, BorderLayout.CENTER);

        table = new JTable();
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

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
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, extenstion, email, officeCode, reportsTo, jobTitle FROM employees");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchEmployeeByNumber(String empNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle FROM employees WHERE employeeNumber LIKE ?");
            ps.setString(1, "%" + empNumber + "%");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchEmployeeByLastName(String lastName) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle FROM employees WHERE lastName LIKE ?");
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
        columnNames.add("Extension");
        columnNames.add("Email");
        columnNames.add("Office Code");
        columnNames.add("Reports To");
        columnNames.add("Job Title");

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("employeeNumber"));
            row.add(rs.getString("firstName"));
            row.add(rs.getString("lastName"));
            row.add(rs.getString("extension"));
            row.add(rs.getString("email"));
            row.add(rs.getInt("officeCode"));
            row.add(rs.getString("reportsTo"));
            row.add(rs.getString("jobTitle"));
            data.add(row);
        }

        table.setModel(new DefaultTableModel(data, columnNames));
    }

    private void addEmployee() {
        String employeeId = employeeIdField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String extension = extensionField.getText().trim();
        String email = emailField.getText().trim();
        String officeCode = officeCodeField.getText().trim();
        String reportsTo = reportsToField.getText().trim();
        String jobTitle = jobTitleField.getText().trim();
        
        if (employeeId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || extension.isEmpty() || email.isEmpty() || officeCode.isEmpty() || reportsTo.isEmpty() || jobTitle.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.");
            return;
        }

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(employeeId));
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, extension);
            ps.setString(5, email);
            ps.setString(6, officeCode);
            ps.setString(7, reportsTo);
            ps.setString(8, jobTitle);

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(frame, "Employee added successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add the employee. Please try again.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        //Retrieves values from the input field
String employeeId = employeeIdField.getText().trim();
String firstName = firstNameField.getText().trim();
String lastName = lastNameField.getText().trim();
String extension = extensionField.getText().trim();
String email = emailField.getText().trim();
String officeCode = officeCodeField.getText().trim();
String reportsTo = reportsToField.getText().trim();
String jobTitle = jobTitleField.getText().trim();

if (employeeId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || extension.isEmpty() || email.isEmpty() || officeCode.isEmpty() || reportsTo.isEmpty() || jobTitle.isEmpty()) {
    JOptionPane.showMessageDialog(frame, "All fields are required.");
return;
}
//Checks if a row in the table is selected
int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(frame, "Please select an employee to update.", "No Selection", JOptionPane.ERROR_MESSAGE);
        return;}


        //Uses employeeNumber of the selected row to update the correspong record in the database
    try{
        PreparedStatement ps = connection.prepareStatement("UPDATE employees SET firstName=?, lastName=?, extension=?, email=?, officeCode=?, reportsTo=?, jobTitle=? WHERE employeeNumber=?");
    ps.setString(1, firstName);
    ps.setString(2, lastName);
    ps.setString(3, extension);
    ps.setString(4, email);
    ps.setString(5, officeCode);
    ps.setString(6, reportsTo);
    ps.setString(7, jobTitle);
    ps.setInt(8, Integer.parseInt(employeeId));

    int result = ps.executeUpdate();

    if (result > 0) {
        JOptionPane.showMessageDialog(frame, "Employee was updated successfully");
        refreshTable();
    } else {
        JOptionPane.showMessageDialog(frame, "Update of employee failed. Please try again.");
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(frame, "Error" + e.getMessage());
    e.printStackTrace();
}
    }
    

    

    private void deleteEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int employeeNumber = (int) table.getValueAt(selectedRow, 0);
        int dialogResult = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM employees WHERE employeeNumber = ?");
                ps.setInt(1, employeeNumber);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Employee deleted successfully!");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete the employee. Please try again.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagement());
    }
}
