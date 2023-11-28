// Created by Kristian
package com.oap200.app.views;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.controllers.EmployeeController;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementPanel extends JPanel {

    private JTable employeeTable;
    private JTextField searchNumberField, searchNameField;
    private JTextField employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle;
    private JComboBox<String> employeeRolesComboBox;
    private JTextField updateEmployeeNumber, updateLastName, updateFirstName, updateExtension, updateEmail,
            updateOfficeCode, updateReportsTo;
    JComboBox<String> updateJobTitle;
    private EmployeeController employeeController;

    public EmployeeManagementPanel(JFrame parentFrame) {
        employeeController = new EmployeeController(new EmployeeDAO(), this);
        initializeFields();
        loadEmployeeRoles();

        setLayout(new BorderLayout());

        JButton backButton = ButtonBuilder.createBlueBackButton(() -> {
            // Get the top-level frame that contains this panel
            Window window = SwingUtilities.getWindowAncestor(EmployeeManagementPanel.this);
            if (window != null) {
                window.dispose(); // This will close the window
            }
        });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {
            // Close the current window
            Window currentWindow = SwingUtilities.getWindowAncestor(EmployeeManagementPanel.this);
            if (currentWindow != null) {
                currentWindow.dispose();
            }

            // Close the MainFrame
            if (parentFrame != null) {
                parentFrame.dispose();
            }

            // Open the LoginPanel in a new window
            openLoginPanel();
        });

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Employees", null, createViewPanel(), "Click to view");
        tabbedPane.addTab("Add Employee", null, createAddPanel(), "Click to add");
        tabbedPane.addTab("Update Employee", null, createUpdatePanel(), "Click to Update");
        tabbedPane.addTab("Delete Employee", null, createDeletePanel(), "Click to Delete");

        add(createButtonPanel(backButton, logoutButton), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void initializeFields() {
        // Initialize text fields
        searchNumberField = new JTextField(10);
        searchNameField = new JTextField(10);
        employeeNumber = new JTextField(10);
        lastName = new JTextField(10);
        firstName = new JTextField(10);
        extension = new JTextField(10);
        email = new JTextField(10);
        officeCode = new JTextField(10);
        reportsTo = new JTextField(10);
        jobTitle = new JTextField(10);

        updateEmployeeNumber = new JTextField(10);
        updateLastName = new JTextField(10);
        updateFirstName = new JTextField(10);
        updateExtension = new JTextField(10);
        updateEmail = new JTextField(10);
        updateOfficeCode = new JTextField(10);
        updateReportsTo = new JTextField(10);
        updateJobTitle = new JComboBox<String>();

        employeeTable = new JTable(new DefaultTableModel());

        employeeRolesComboBox = new JComboBox<>();
    }

    private JPanel createButtonPanel(JButton backButton, JButton logoutButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        return buttonPanel;
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton viewButton = ButtonBuilder.createViewButton(() -> employeeController.handleViewAllEmployees());
        JButton searchButton = ButtonBuilder.createSearchButton(() -> searchEmployees());
        JPanel viewSearchButtonPanel = new JPanel(new FlowLayout());
        viewSearchButtonPanel.add(viewButton);
        viewSearchButtonPanel.add(searchButton);
        panel.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton addButton = ButtonBuilder.createAddButton(() -> addEmployee());
        JPanel addPanel = new JPanel(new GridLayout(9, 2));
        addPanel.add(new JLabel("Employee Number:"));
        addPanel.add(employeeNumber);
        addPanel.add(new JLabel("Last Name:"));
        addPanel.add(lastName);
        addPanel.add(new JLabel("First Names:"));
        addPanel.add(firstName);
        addPanel.add(new JLabel("Extension:"));
        addPanel.add(extension);
        addPanel.add(new JLabel("Email:"));
        addPanel.add(email);
        addPanel.add(new JLabel("Office Code:"));
        addPanel.add(officeCode);
        addPanel.add(new JLabel("Reports To:"));
        addPanel.add(reportsTo);
        addPanel.add(new JLabel("Employee Roles:"));
        addPanel.add(employeeRolesComboBox);
        panel.add(addPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> updateEmployee());
        JPanel updatePanel = new JPanel(new GridLayout(9, 2));
        JComboBox<String> updateJobTitle = new JComboBox<>(employeeRolesComboBox.getModel());
        updatePanel.add(new JLabel("Update Employee Number:"));
        updatePanel.add(updateEmployeeNumber);
        updatePanel.add(new JLabel("Update Last Name:"));
        updatePanel.add(updateLastName);
        updatePanel.add(new JLabel("Update First Name:"));
        updatePanel.add(updateFirstName);
        updatePanel.add(new JLabel("Update Extension:"));
        updatePanel.add(updateExtension);
        updatePanel.add(new JLabel("Update Email:"));
        updatePanel.add(updateEmail);
        updatePanel.add(new JLabel("Update Office Code:"));
        updatePanel.add(updateOfficeCode);
        updatePanel.add(new JLabel("Update Reports To:"));
        updatePanel.add(updateReportsTo);
        updatePanel.add(new JLabel("Update Job Title:"));
        updatePanel.add(updateJobTitle); // Add the combo box instead of a text field
        panel.add(updatePanel, BorderLayout.CENTER);
        panel.add(updateButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> deleteEmployee());
        JPanel deletePanel = new JPanel(new GridLayout(1, 2));
        deletePanel.add(new JLabel("Employee Number:"));
        deletePanel.add(searchNumberField);
        panel.add(deletePanel, BorderLayout.NORTH);
        panel.add(deleteButton, BorderLayout.SOUTH);
        return panel;
    }

    private void loadEmployeeRoles() {
        List<String> roles = employeeController.getEmployeeRoles();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String role : roles) {
            model.addElement(role);
        }
        employeeRolesComboBox.setModel(model);
    }

    public void refreshUI() {
        revalidate();
        repaint();
    }

    private void openLoginPanel() {
        JFrame loginFrame = new JFrame("Login");
        LoginPanel loginPanel = new LoginPanel();
        loginFrame.setContentPane(loginPanel);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }

    private void searchEmployees() {
        String employeeNumber = searchNumberField.getText();
        String employeeName = searchNameField.getText();

        // Check if a search number is provided
        if (!employeeNumber.isEmpty()) {
            employeeController.handleSearchEmployeesNumber(employeeNumber);
        } else if (!employeeName.isEmpty()) {
            // If no number provided, search by name
            employeeController.handleSearchEmployees(employeeName);
        } else {
            // If both fields are empty, you might want to handle this case (e.g., show a
            // message or do nothing)
        }
    }

    private void addEmployee() {
        String empNum = employeeNumber.getText();
        String lName = lastName.getText();
        String fName = firstName.getText();
        String ext = extension.getText();
        String mail = email.getText();
        String office = officeCode.getText();
        String reports = reportsTo.getText();
        String title = jobTitle.getText();

        Integer reportsToInt = null;
        try {
            reportsToInt = reports.isEmpty() ? null : Integer.parseInt(reports);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Reports To number format.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = employeeController.handleAddEmployee(empNum, lName, fName, ext, mail, office, reportsToInt,
                title);

        if (success) {
            JOptionPane.showMessageDialog(this, "Employee added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add employee", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        String empNum = updateEmployeeNumber.getText().trim();
        String lName = updateLastName.getText().trim();
        String fName = updateFirstName.getText().trim();
        String ext = updateExtension.getText().trim();
        String mail = updateEmail.getText().trim();
        String office = updateOfficeCode.getText().trim();
        String reports = updateReportsTo.getText().trim();

        // Retrieve the selected job title from the combo box
        String jobTitle = (String) employeeRolesComboBox.getSelectedItem();

        // Basic validation for employee number
        if (empNum.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee number cannot be empty for an update.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (jobTitle == null || jobTitle.isEmpty()) {
            // Handle the case where jobTitle is null or empty
            // For example, you might set it to a default value or display an error message
        }

        Integer reportsToInt = null;
        try {
            if (!reports.isEmpty()) {
                reportsToInt = Integer.parseInt(reports);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid format for 'Reports To'.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = employeeController.handleUpdateEmployee(empNum, lName, fName, ext, mail, office, reportsToInt,
                jobTitle);

        if (success) {
            JOptionPane.showMessageDialog(this, "Employee updated successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update employee", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        String employeeNum = searchNumberField.getText();
        employeeController.handleDeleteEmployee(employeeNum);
    }

    public void displayEmployees(List<String[]> employeeList) {
        // Define the column names
        String[] columnNames = { "Employee Number", "Last Name", "First Name", "Extension", "Email", "Office Code",
                "Reports To", "Job Title" };

        // Create a new table model with column names and no rows initially
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        // Populate the model with data
        for (String[] row : employeeList) {
            model.addRow(row);
        }

        // Set the model to the employeeTable
        employeeTable.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame employeeframe = new JFrame("Employee Management");
            employeeframe.setContentPane(new EmployeeManagementPanel(employeeframe));
            employeeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            employeeframe.pack();
            employeeframe.setVisible(true);
        });
    }
}
