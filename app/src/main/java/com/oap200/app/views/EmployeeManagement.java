// Created by Kristian


//Fordele tilgang blant employees

package com.oap200.app.views;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javax.swing.table.DefaultTableModel;



import java.util.List;

public class EmployeeManagement {
    private JComboBox<String> reportsToComboBox;

    private JFrame frame;
    private Connection connection;
    private JTable table;
    private JTextField employeeIdField, employeeNumberSearchField, searchField, firstNameField, lastNameField, extensionField, emailField, officeCodeField, jobTitleField, accessLevelField;
    private JButton addButton, updateButton, deleteButton, refreshButton, searchButton;
  
    public EmployeeManagement() {
        connectDatabase();
        createUI();
    }
    private Vector<String> getAllEmployees() {
        Vector<String> employees = new Vector<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName FROM employees");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(rs.getInt("employeeNumber") + " - " + rs.getString("firstName") + " " + rs.getString("lastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int windowWidth = (int) (width * 1);
        int windowHeight = (int) (height * 1);

        frame = new JFrame("Employee Management");
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        frame.getContentPane().setBackground(new Color(153, 179, 255));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(true);
        inputPanel.setBackground(Color.BLACK); 
    
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setOpaque(true);
        searchPanel.setBackground(new Color(153, 179, 255));

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

        
        JPanel employeeInputPanel = new JPanel();
        employeeInputPanel.setLayout(new BoxLayout(employeeInputPanel, BoxLayout.Y_AXIS));
        employeeInputPanel.setOpaque(true);
        employeeInputPanel.setBackground(Color.LIGHT_GRAY);

    
        // Creates separate panels for label and input fields

        employeeIdField = new JTextField(10);
        employeeInputPanel.add(createLabeledField("Employee ID:", employeeIdField));
        firstNameField = new JTextField(10);
        employeeInputPanel.add(createLabeledField("First Name:", firstNameField));
        lastNameField = new JTextField(10);
        employeeInputPanel.add(createLabeledField("Last Name:", lastNameField));
        extensionField = new JTextField(10);
        employeeInputPanel.add(createLabeledField("Extension:", extensionField));
        emailField = new JTextField(10);
        employeeInputPanel.add(createLabeledField("Email:", emailField));
        officeCodeField = new JTextField(10);
        employeeInputPanel.add(createLabeledField("Office Code:", officeCodeField));
        
        Vector<String> employeesList = getAllEmployees();
reportsToComboBox = new JComboBox<>(employeesList);

employeeInputPanel.add(createLabeledField("Reports To:", reportsToComboBox));

        jobTitleField = new JTextField(10); 
        employeeInputPanel.add(createLabeledField("Job Title:", jobTitleField));

        accessLevelField = new JTextField(1);
        employeeInputPanel.add(createLabeledField("Access Level:", accessLevelField));
    
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        employeeInputPanel.add(buttonPanel);
    
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
    
    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(field);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));  // top, left, bottom, right padding
        return panel;
    }
    

    private void refreshTable() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle, accessLevel FROM employees");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        employeeNumberSearchField.setText("");
        searchField.setText("");
        employeeIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        extensionField.setText("");
        emailField.setText("");
        officeCodeField.setText("");
        accessLevelField.setText("");
       // Resets the input panels, to not show any data
        reportsToComboBox.setSelectedIndex(-1); // Resets the JComboBox to have no selection
        jobTitleField.setText("");
        // Similarly, clear or reset other fields
        
    }
    

    private void searchEmployeeByNumber(String empNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle, accessLevel FROM employees WHERE employeeNumber LIKE ?");
            ps.setString(1, "%" + empNumber + "%");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            clearInputFields();
        }
    }

    private void searchEmployeeByLastName(String lastName) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle, accessLevel FROM employees WHERE lastName LIKE ?");
            ps.setString(1, "%" + lastName + "%");
            ResultSet rs = ps.executeQuery();
            populateTableFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            clearInputFields();
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
        columnNames.add("accessLevel");

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
            row.add(rs.getInt("accessLevel"));
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
        String reportsTo = ((String) reportsToComboBox.getSelectedItem()).split(" - ")[0];
        String jobTitle = jobTitleField.getText().trim();
        String accessLevel = accessLevelField.getText().trim();
        
        if (employeeId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || extension.isEmpty() || email.isEmpty() || officeCode.isEmpty() || reportsTo.isEmpty() || jobTitle.isEmpty() || accessLevel.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.");
            return;
        }

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO employees (employeeNumber, firstName, lastName, extension, email, officeCode, reportsTo, jobTitle, accessLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(employeeId));
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, extension);
            ps.setString(5, email);
            ps.setString(6, officeCode);
            ps.setString(7, reportsTo);
            ps.setString(8, jobTitle);
            ps.setString(9, accessLevel);

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(frame, "Employee added successfully!");
                refreshTable();
                populateReportsToDropdown(); //Shows the newly added employees to the dropdown table
                clearInputFields();
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
String reportsTo = ((String) reportsToComboBox.getSelectedItem()).split(" - ")[0];
String jobTitle = jobTitleField.getText().trim();
String accessLevel = accessLevelField.getText().trim();


if (employeeId.isEmpty()) {
    JOptionPane.showMessageDialog(frame, "Employee ID is required");
return;
}   
    StringBuilder sql = new StringBuilder("UPDATE employees SET ");
    List<Object> parameters = new ArrayList<>();



    if (!firstName.isEmpty()) {
        sql.append("firstName=?, ");
        parameters.add(firstName);
    }
    if (!lastName.isEmpty()) {
        sql.append("lastName=?, ");
        parameters.add(lastName);
    }
    if (!extension.isEmpty()) {
        sql.append("extension=?, ");
        parameters.add(extension);
    }
    if (!email.isEmpty()) {
        sql.append("email=?, ");
        parameters.add(email);
    }
    if (!officeCode.isEmpty()) {
        sql.append("officeCode=?, ");
        parameters.add(officeCode);
    }
    if (!reportsTo.isEmpty()) {
        sql.append("reportsTo=?, ");
        parameters.add(reportsTo);
    }
    if (!jobTitle.isEmpty()) {
        sql.append("jobTitle=?, ");
        parameters.add(jobTitle);
    }
    if (!accessLevel.isEmpty()){
        sql.append("accessLevel=?, ");
        parameters.add(accessLevel);
    }
    
    if (parameters.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "No fields to update.");
        return;
    }

    // Remove trailing comma and space
    if (sql.toString().endsWith(", ")){
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));
    }
      sql.append("WHERE employeeNumber=?");
      parameters.add(Integer.parseInt(employeeId));
    
    try {
        PreparedStatement ps = connection.prepareStatement(sql.toString());
        for (int i = 0; i < parameters.size(); i++) {
            ps.setObject(i + 1, parameters.get(i)); // Set the parameters for PreparedStatement
        }
    
        int result = ps.executeUpdate();
    
        
        if (result > 0) {
            JOptionPane.showMessageDialog(frame, "Employee was updated successfully");
            refreshTable(); 
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(frame, "No employee was updated, please check the employee ID and try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } 
    
    }
    
    

    
    private boolean hasReports(int employeeNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM employees WHERE reportsTo = ?");
            ps.setInt(1, employeeNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
private void deleteEmployee() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(frame, "Please select an employee to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int employeeNumber = (int) table.getValueAt(selectedRow, 0);

    if (hasReports(employeeNumber)) {
        JOptionPane.showMessageDialog(frame, "This employee has other employees reporting to them. Please change the reporting structure before deleting.", "Cannot Delete", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int dialogResult = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

    if (dialogResult == JOptionPane.YES_OPTION) {
        new SwingWorker<Void, Void>() {
            protected Void doInBackground() throws SQLException {
                // Perform the long-running deletion in the background.
                PreparedStatement ps = connection.prepareStatement("UPDATE customers SET salesRepEmployeeNumber = NULL WHERE salesRepEmployeeNumber = ?");
                ps.setInt(1, employeeNumber);
                ps.executeUpdate();
                
                ps = connection.prepareStatement("DELETE FROM employees WHERE employeeNumber = ?");
                ps.setInt(1, employeeNumber);
                ps.executeUpdate();

                return null;
            }

            protected void done() {
                try {
                    get(); // Call get to ensure any exceptions are caught
                    JOptionPane.showMessageDialog(frame, "Employee deleted successfully!");
                    refreshTable();
                    clearInputFields();
                } catch (InterruptedException | ExecutionException e) {
                    Throwable cause = e.getCause();
                    JOptionPane.showMessageDialog(frame, "Error: " + cause.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
                    cause.printStackTrace();
                }
            }
        }.execute();
    }
}

    private void populateReportsToDropdown() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT employeeNumber, firstName, lastName FROM employees");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int employeeNumber = rs.getInt("employeeNumber");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Employee employee = new Employee(employeeNumber, firstName, lastName);
                reportsToComboBox.addItem(employee.toString());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    class Employee {
        private int employeeNumber;
        private String firstName;
        private String lastName;
    
        public Employee(int employeeNumber, String firstName, String lastName) {
            this.employeeNumber = employeeNumber;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    
        public int getEmployeeNumber() {
            return employeeNumber;
        }
    
        @Override
        public String toString() {
            return firstName + " " + lastName + " (" + employeeNumber + ")";
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeManagement());
    }
    public void start() {
    }
}

