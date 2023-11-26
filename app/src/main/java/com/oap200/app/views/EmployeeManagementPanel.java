//Created by Kristian
package com.oap200.app.views;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.controllers.EmployeeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import com.oap200.app.utils.ButtonBuilder;

import java.util.Arrays;
import java.util.List;

public class EmployeeManagementPanel extends JFrame {

    // Preferences keys for storing window position
    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";

    // Components for the view
    private JTable employeeTable;
    private JTextField searchNumberField;
    private JTextField searchNameField;

    // Components for adding a new employee
    private JTextField employeeNumber;
    private JTextField lastName;
    private JComboBox<String> employeeRolesComboBox = new JComboBox<>();
    private JTextField firstName;
    private JTextField extension;
    private JTextField email;
    private JTextField officeCode;
    private JTextField reportsTo;
    private JTextField jobTitle;

    // Components for updating an employee
    private JTextField updateEmployeeNumber;
    private JTextField updateLastName;
    private JTextField updateFirstName;
    private JTextField updateExtension;
    private JTextField updateEmail;
    private JTextField updateOfficeCode;
    private JTextField updateReportsTo;
    private JTextField updateJobTitle;

    private EmployeeController employeeController;

private void initializeFields() {
        System.out.println("Initializing fields...");
        loadEmployeeRoles();

        searchNumberField = new JTextField(10);
        searchNameField = new JTextField(10);

        this.employeeNumber = new JTextField(10);
        this.lastName = new JTextField(10);
        this.firstName = new JTextField(10);
        this.extension = new JTextField(10);
        this.email = new JTextField(10);
        this.officeCode = new JTextField(10);
        this.reportsTo = new JTextField(10);
        this.jobTitle = new JTextField(10);

        updateEmployeeNumber = new JTextField(10);
        updateLastName = new JTextField(10);
        updateFirstName = new JTextField(10);
        updateExtension = new JTextField(10);
        updateEmail = new JTextField(10);
        updateOfficeCode = new JTextField(10);
        updateReportsTo = new JTextField(10);
        updateJobTitle = new JTextField(10);

    }

private void refreshTable(){
    employeeController.handleViewAllEmployees();
}
    public EmployeeManagementPanel() {
        initializeFields();
        employeeController = new EmployeeController(new EmployeeDAO(), this);

        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(EmployeeManagementPanel.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        // Save window position on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

        // Set up the layout for the frame
        setLayout(new BorderLayout());

        // Initialize ButtonBuilder buttons
        JButton backButton = ButtonBuilder.createBlueBackButton(() -> { /* Action for Back Button */ });
        JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> { /* Action for Logout Button */ });
        JButton viewButton = ButtonBuilder.createViewButton(() -> { /* Action for View Button */ });
        JButton addButton = ButtonBuilder.createAddButton(() -> {
            System.out.println("Add Button Clicked!");
        });
        JButton deleteButton = ButtonBuilder.createDeleteButton(() -> { /* Action for Delete Button */ });
        JButton updateButton = ButtonBuilder.createUpdateButton(() -> { /* Action for Update Button */ });
        JButton searchNumberButton = ButtonBuilder.createSearchButton(() -> searchEmployeesNumber());
        JButton searchButton = ButtonBuilder.createSearchButton(() -> {
            searchEmployees();
        });

        JPanel viewSearchButtonPanel = new JPanel(new FlowLayout());
        viewSearchButtonPanel.add(viewButton);
        viewSearchButtonPanel.add(searchNumberButton);
        viewSearchButtonPanel.add(searchButton);

        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Employees
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(viewSearchButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("View Employees", null, panel1, "Click to view");

        // Tab 2: Add Employees
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanelAdd(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Employee", null, panel2, "Click to add");

        // Tab 3: Update Employees
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanelUpdate(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Employee", null, panel3, "Click to Update");

        // Tab 4: Delete Employees
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanelDelete(panel4);
        panel4.add(deleteButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Delete Employee", null, panel4, "Click to Delete");

        // Initialize Panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel topPanel = new JPanel(new BorderLayout());

        buttonPanel.setOpaque(true);
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Main panel for the frame
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.EAST);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Initialize the table
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel1.add(scrollPane, BorderLayout.CENTER);

        viewButton.addActionListener(e -> {employeeController.handleViewAllEmployees();});
        searchButton.addActionListener(e -> {searchEmployeesNumber();});
        searchButton.addActionListener(e -> {searchEmployees(); refreshTable();});
        deleteButton.addActionListener(e -> {deleteEmployee(); refreshTable();});
        addButton.addActionListener(e -> {addEmployee(); refreshTable();});
        updateButton.addActionListener(e -> {
            String employeeNumberToUpdate = updateEmployeeNumber.getText();
            String newLastName = updateLastName.getText();
            String newFirstName = updateFirstName.getText();
            String newExtension = updateExtension.getText();
            String newEmail = updateEmail.getText();
            String newOfficeCode = updateOfficeCode.getText();
            String newReportsToText = updateReportsTo.getText();
            String newJobTitle = updateJobTitle.getText();
            
            try {
                Integer newReportsTo = Integer.parseInt(newReportsToText);
                boolean updateSuccess = employeeController.handleUpdateEmployee(employeeNumberToUpdate, newLastName, 
                    newFirstName, newExtension, newEmail, newOfficeCode, newReportsTo, newJobTitle);
                
                if (updateSuccess) {
                    JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed. Please check the input data.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
        
                System.out.println("Updated Employee: " + employeeNumberToUpdate + ", " + newLastName + ", " + newFirstName + ", " + newExtension + ", " + newEmail + ", " + newOfficeCode + ", " + newReportsTo + ", " + newJobTitle);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Reports To number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        refreshTable();});
        
        loadEmployeeRoles();
    }

    private void loadEmployeeRoles() {
        if (employeeController != null) {
            List<String> employeeRoles = employeeController.getEmployeeRoles();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(employeeRoles.toArray(new String[0]));
            employeeRolesComboBox.setModel(model);
            System.out.println("Employee roles loaded: " + employeeRoles);
        } else {
            System.err.println("EmployeeController is null!!");
        }
    }

    private void searchEmployees() {
        String employeeName = searchNameField.getText();
        employeeController.handleSearchEmployees(employeeName);
    }

private void searchEmployeesNumber(){
    String employeeNumber = searchNumberField.getText();
    employeeController.handleSearchEmployeesNumber(employeeNumber);
}

    private void deleteEmployee() {
        String employeeNumber = searchNumberField.getText();
        boolean deletionSuccessful = employeeController.handleDeleteEmployee(employeeNumber);
        if (deletionSuccessful) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Deletion completed", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error when deleting employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        System.out.println("addEmployee() called!");
    
        String employeeNumberText = employeeNumber.getText();
        String lastNameText = lastName.getText();
        String firstNameText = firstName.getText();
        String extensionText = extension.getText();
        String emailText = email.getText();
        String officeCodeText = officeCode.getText();
        String reportsToText = reportsTo.getText();
        String jobTitleText = jobTitle.getText();
    
        try {
            Integer reportsToInt = reportsToText.isEmpty() ? null : Integer.parseInt(reportsToText);
            boolean additionSuccessful = employeeController.handleAddEmployee(
                    employeeNumberText, lastNameText, firstNameText, 
                    extensionText, emailText, officeCodeText, reportsToInt, jobTitleText);
    
            if (additionSuccessful) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Addition completed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error when adding employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Reports To number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addComponentsToPanelAdd(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(9, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(9, 1));

        labelPanel.add(new JLabel("Employee Number:"));
        fieldPanel.add(employeeNumber);
        labelPanel.add(new JLabel("Last Name:"));
        fieldPanel.add(lastName);
        labelPanel.add(new JLabel("First Names:"));
        fieldPanel.add(firstName);
        labelPanel.add(new JLabel("Extension:"));
        fieldPanel.add(extension);
        labelPanel.add(new JLabel("Email:"));
        fieldPanel.add(email);
        labelPanel.add(new JLabel("Office Code:"));
        fieldPanel.add(officeCode);
        labelPanel.add(new JLabel("Reports To:"));
        fieldPanel.add(reportsTo);
        labelPanel.add(new JLabel("Employee Roles:"));
        fieldPanel.add(employeeRolesComboBox);
        
        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelUpdate(JPanel panelUpdate) {
        panelUpdate.setLayout(new BorderLayout());

        JPanel inputPanelUpdate = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanelUpdate.add(new JLabel("Update Employee Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        updateEmployeeNumber = new JTextField(10);
        inputPanelUpdate.add(updateEmployeeNumber, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Last Name:"), gbc);
        gbc.gridx = 1;
        updateLastName = new JTextField(10);
        inputPanelUpdate.add(updateLastName, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        updateFirstName = new JTextField(10);
        inputPanelUpdate.add(updateFirstName, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Extension:"), gbc);
        gbc.gridx = 1;
        updateExtension = new JTextField(10);
        inputPanelUpdate.add(updateExtension, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Email:"), gbc);
        gbc.gridx = 1;
        updateEmail = new JTextField(10);
        inputPanelUpdate.add(updateEmail, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Office Code:"), gbc);
        gbc.gridx = 1;
        updateOfficeCode = new JTextField(10);
        inputPanelUpdate.add(updateOfficeCode, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        inputPanelUpdate.add(new JLabel("Update Reports To:"), gbc);
        gbc.gridx = 1;
        updateReportsTo = new JTextField(10);
        inputPanelUpdate.add(updateReportsTo, gbc);

        // Add this to fill the space in the panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        inputPanelUpdate.add(new JPanel(), gbc);

        panelUpdate.add(inputPanelUpdate, BorderLayout.NORTH);

        // Create the scrollPane with the employeeTable
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelUpdate.add(tableContainer, BorderLayout.CENTER);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
       
      // Employee Number Field
    inputPanel.add(new JLabel("Employee Number:"), gbc);
    gbc.gridx = 1;
    searchNumberField = new JTextField(10);
    inputPanel.add(searchNumberField, gbc);

       // Increment the gridy for the next row
    gbc.gridy = 1; // Move to the next row
    gbc.gridx = 0; // Reset the x position

  // Employee Name Field
    inputPanel.add(new JLabel("Employee Name:"), gbc);
    gbc.gridx = 1;
    searchNameField = new JTextField(10);
    inputPanel.add(searchNameField, gbc);
        panelView.add(inputPanel, BorderLayout.NORTH);

        // Create the scrollPane with the employeeTable
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
    }

    private void addComponentsToPanelDelete(JPanel panelDelete) {
        panelDelete.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        inputPanel.add(new JLabel("Employee Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        searchNumberField = new JTextField(10);
        inputPanel.add(searchNumberField, gbc);

        panelDelete.add(inputPanel, BorderLayout.NORTH);

        // Create the scrollPane with the employeeTable
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelDelete.add(tableContainer, BorderLayout.CENTER);
    }

    public void displayEmployees(List<String[]> employeeList) {
        String[] columnNames = { "Employee Number", "Last Name", "First Name ", "Extension", "Email ", "Office Code", "Reports To", "Job Title"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : employeeList) {
            model.addRow(row);
        }
        employeeTable.setModel(model);

        System.out.println("Displayed Employees: " + Arrays.deepToString(employeeList.toArray()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementPanel frame = new EmployeeManagementPanel();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Employee Management");
            frame.pack();
            frame.setVisible(true);
        });
    }
}
