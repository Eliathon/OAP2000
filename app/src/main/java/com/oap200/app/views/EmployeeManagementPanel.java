/**
 * Module for managing employees in the application.
 *
 * <p>This module includes functionalities for viewing, adding, updating, searching, and deleting
 * employee records. It interacts with the database using the {@link com.oap200.app.models.EmployeeDAO}
 * class for seamless data management.
 *
 * <p>The user interface for employee management may include features like viewing a list of employees,
 * adding new employees, updating existing records, searching for employees by name or number, and deleting
 * employee records. The module leverages the {@link com.oap200.app.utils.DbConnect} class for database
 * connectivity.
 *
 * <p>Usage example:
 * <pre>{@code
 * EmployeeManagementModule employeeModule = new EmployeeManagementModule();
 * List<String[]> allEmployees = employeeModule.fetchAllEmployees();
 * }</pre>
 *
 * @author Kristian
 * @version 1.0
 * @since 2023-11-30
 */

package com.oap200.app.views;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.controllers.EmployeeController;
import com.oap200.app.utils.ButtonBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * The main panel for managing employees in the application.
 */
public class EmployeeManagementPanel extends JPanel {
    // Declaration of class fields
    private JTable employeeTable;
    private JTable employeeTableAdd;
    private JTable employeeTableUpdate;
    private JTable employeeTableDelete;
    private JTextField searchByNumberField;
    private JTextField searchNumberField, searchNameField;
    private JTextField employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle;
    private JComboBox<String> employeeRolesComboBox;
    private JTextField updateEmployeeNumber, updateLastName, updateFirstName, updateExtension, updateEmail,
            updateOfficeCode, updateReportsTo;
    JComboBox<String> updateJobTitle;
    private EmployeeController employeeController;

    /**
     * Constructor for the EmployeeManagementPanel.
     *
     * @param parentFrame The parent JFrame in which this panel will be displayed.
     */
    public EmployeeManagementPanel(JFrame parentFrame) {
        employeeController = new EmployeeController(new EmployeeDAO(), this);
        initializeFields();
        loadEmployeeRoles();
        searchByNumberField = new JTextField(10);

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
        
        JButton viewButton = ButtonBuilder.createViewButton(this::viewEmployees);
        JButton addButton = ButtonBuilder.createAddButton(this::addEmployee);
        JButton deleteButton = ButtonBuilder.createDeleteButton(this::deleteEmployee);
        JButton updateButton = ButtonBuilder.createUpdateButton(this::updateEmployee);
        JButton searchButton = ButtonBuilder.createSearchButton(this::searchEmployeesName);
        JButton searchNumberButton = ButtonBuilder.createSearchNumberButton(this::searchEmployeesByNumber);

        JPanel viewSearchButtonPanel = createViewSearchButtonPanel(viewButton, searchButton, searchNumberButton);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Employees", null, createViewPanel(), "Click to view");
        tabbedPane.addTab("Add Employee", null, createAddPanel(), "Click to add");
        tabbedPane.addTab("Update Employee", null, createUpdatePanel(updateButton), "Click to Update");
        tabbedPane.addTab("Delete Employee", null, createDeletePanel(), "Click to Delete");

        add(createButtonPanel(backButton, logoutButton), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
private JPanel createViewSearchButtonPanel(JButton viewButton, JButton searchButton, JButton searchNumberButton) {
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(viewButton);
            panel.add(searchButton);
            panel.add(searchNumberButton);
            return panel;
        }
    /**
     * Initializes all the text fields and components in the panel.
     */
    private void initializeFields() {
        // Initialize text fields
        Dimension fieldDimension = new Dimension(150,20); //Sets a desired width and height of input fields

        searchNumberField = new JTextField(10);
        searchNumberField.setPreferredSize(fieldDimension);
        searchNameField = new JTextField(10);
        searchNameField.setPreferredSize(fieldDimension);
        employeeNumber = new JTextField(10);
        employeeNumber.setPreferredSize(fieldDimension);
        lastName = new JTextField(10);
        lastName.setPreferredSize(fieldDimension);
        firstName = new JTextField(10);
        firstName.setPreferredSize(fieldDimension);
        extension = new JTextField(10);
        extension.setPreferredSize(fieldDimension);
        email = new JTextField(10);
        email.setPreferredSize(fieldDimension);
        officeCode = new JTextField(10);
        officeCode.setPreferredSize(fieldDimension);
        reportsTo = new JTextField(10);
        reportsTo.setPreferredSize(fieldDimension);
        jobTitle = new JTextField(10);
        jobTitle.setPreferredSize(fieldDimension);

        updateEmployeeNumber = new JTextField(10);
        updateEmployeeNumber.setPreferredSize(fieldDimension);
        
        updateLastName = new JTextField(10);
        updateLastName.setPreferredSize(fieldDimension);
        updateFirstName = new JTextField(10);
        updateFirstName.setPreferredSize(fieldDimension);
        updateExtension = new JTextField(10);
        updateExtension.setPreferredSize(fieldDimension);
        updateEmail = new JTextField(10);
        updateEmail.setPreferredSize(fieldDimension);
        updateOfficeCode = new JTextField(10);
        updateOfficeCode.setPreferredSize(fieldDimension);
        updateReportsTo = new JTextField(10);
        updateReportsTo.setPreferredSize(fieldDimension);
        updateJobTitle = new JComboBox<String>();
        updateJobTitle.setPreferredSize(fieldDimension);
        employeeTable = new JTable(new DefaultTableModel());
        
        employeeRolesComboBox = new JComboBox<>();
    }

    /**
     * Creates the button panel with back and logout buttons.
     *
     * @param backButton   The back button.
     * @param logoutButton The logout button.
     * @return The button panel.
     */
    private JPanel createButtonPanel(JButton backButton, JButton logoutButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        return buttonPanel;
    }
    

    /**
     * Creates the view panel with search functionality and a table to display employees.
     *
     * @return The view panel.
     */
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create the search panel for employee number with FlowLayout
        JPanel searchByNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchByNumberPanel.add(new JLabel("Search by Number:"));
        searchByNumberPanel.add(searchByNumberField); // Add the new search field
        JButton searchNumberButton = ButtonBuilder.createSearchNumberButton(() -> searchEmployeesByNumber());
        searchByNumberPanel.add(searchNumberButton);

        // Create the search panel for employee name with FlowLayout
        JPanel searchByNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchByNamePanel.add(new JLabel("Search by Name:"));
        searchByNamePanel.add(searchNameField);
        JButton searchNameButton = ButtonBuilder.createSearchButton(() -> searchEmployeesName());
        searchByNamePanel.add(searchNameButton);

        // Combined both search panels into a single panel with BoxLayout
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
        searchPanel.add(searchByNumberPanel);
        searchPanel.add(searchByNamePanel);

        // Add the search panel to the top of the view panel
        panel.add(searchPanel, BorderLayout.NORTH);

        // The rest of your method to add the table view
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton viewAllButton = ButtonBuilder.createViewButton(() -> employeeController.handleViewAllEmployees());
        JPanel viewAllButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        viewAllButtonPanel.add(viewAllButton);
        panel.add(viewAllButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

   
/**
 * Creates the panel for adding a new employee.
 *
 * @return The panel for adding a new employee.
 */
private JPanel createAddPanel() {
    // Main panel for adding a new employee, using BorderLayout for overall arrangement
    JPanel addPanel = new JPanel(new BorderLayout());

    // Panel to hold all the label and input field pairs, using BoxLayout for vertical stacking
    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

    // Add the pairs of labels and text fields to the fieldsPanel
    fieldsPanel.add(createFieldPanel("Last Name:", lastName));
    fieldsPanel.add(createFieldPanel("First Names:", firstName));
    fieldsPanel.add(createFieldPanel("Extension:", extension));
    fieldsPanel.add(createFieldPanel("Email:", email));
    fieldsPanel.add(createFieldPanel("Office Code:", officeCode));
    fieldsPanel.add(createFieldPanel("Reports To:", reportsTo));
    fieldsPanel.add(createFieldPanel("Employee Roles:", employeeRolesComboBox));

    // Wrap fieldsPanel in a container to prevent it from stretching to the full width of the window
    JPanel fieldsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    fieldsContainer.add(fieldsPanel);

    // Add the fieldsContainer to the center of the addPanel
    addPanel.add(fieldsContainer, BorderLayout.CENTER);

    // Create and add the Add button to the south of addPanel
    JButton addButton = ButtonBuilder.createAddButton(() -> addEmployee());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(addButton);
    addPanel.add(buttonPanel, BorderLayout.SOUTH);

    return addPanel;
}

// Helper method to create a panel with a label and text field using FlowLayout for alignment
private JPanel createFieldPanel(String labelText, Component field) {
    JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    fieldPanel.add(new JLabel(labelText));
    fieldPanel.add(field);
    return fieldPanel;
}


/**
 * Creates the panel for updating employee information.
 *
 * @return The panel for updating employee information.
 */

/**
 * Creates the panel for deleting an employee.
 *
 * @return The panel for deleting an employee.
 */
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
private JPanel createUpdatePanel(JButton updateButton) {
    JPanel panel = new JPanel(new BorderLayout());
        // Add label and field panel as before
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        // Add labels and corresponding text fields
        inputPanel.add(new JLabel("employee Number:"));
        inputPanel.add(employeeNumber);

        // Update the size of the Product Name field to match the view panel
        Dimension preferredSize = employeeTable.getTableHeader().getDefaultRenderer()
                .getTableCellRendererComponent(employeeTable, "Employee Number", false, false, -1, 0)
                .getPreferredSize();
        employeeNumber.setPreferredSize(preferredSize);

        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastName);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstName);
        inputPanel.add(new JLabel("Extension:"));
        inputPanel.add(extension);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(email);
        inputPanel.add(new JLabel("Reports To:"));
        inputPanel.add(reportsTo);
        inputPanel.add(new JLabel("Job Title:"));
        inputPanel.add(employeeRolesComboBox);

        // Add label and field panel to the west and center positions
        panel.add(inputPanel, BorderLayout.WEST);
        

        // Add the button to a separate panel for positioning adjustment
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(updateButton);
        
       

        // Add the button's panel at the center
        panel.add(buttonPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.WEST);
        topPanel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(topPanel, BorderLayout.NORTH);

        employeeTableUpdate = new JTable();
        JScrollPane scrollPaneAdd = new JScrollPane(employeeTableUpdate);

        // Add scroll pane to the main panel
        panel.add(scrollPaneAdd, BorderLayout.CENTER);

        // Add the top panel to the main panel
        panel.add(topPanel, BorderLayout.NORTH);

        return panel;
    }
/**
 * Loads employee roles into the combo box.
 */
private void loadEmployeeRoles() {
    List<String> roles = employeeController.getEmployeeRoles();
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    for (String role : roles) {
        model.addElement(role);
    }
    employeeRolesComboBox.setModel(model);
}

/**
 * Refreshes the user interface.
 */
public void refreshUI() {
    revalidate();
    repaint();
}

/**
 * Opens the login panel in a new window.
 */
private void openLoginPanel() {
    JFrame loginFrame = new JFrame("Login");
    LoginPanel loginPanel = new LoginPanel();
    loginFrame.setContentPane(loginPanel);
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    loginFrame.pack();
    loginFrame.setVisible(true);
}

/**
 * Searches for employees by name and displays the results.
 */
private void searchEmployeesName() {
    String employeeName = searchNameField.getText().trim();

    if (!employeeName.isEmpty()) {
        List<String[]> searchResults = employeeController.handleSearchEmployees(employeeName);
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "This employee name doesn't exist in the database.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayEmployees(searchResults);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please enter an employee name to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
/**
 * Searches for employees by employee number and displays the results.
 */
private void searchEmployeesByNumber() {
    String employeeNumber = searchByNumberField.getText().trim();

    if (!employeeNumber.isEmpty()) {
        List<String[]> searchResults = employeeController.handleSearchEmployeesNumber(employeeNumber);
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "This employee number doesn't exist in the database.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayEmployees(searchResults);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please enter an employee number to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void viewEmployees() {
    employeeController.handleViewAllEmployees();
}
/**
 * Adds a new employee based on the input fields.
 */
private void addEmployee() {
    String lName = lastName.getText().trim();
    String fName = firstName.getText().trim();
    String ext = extension.getText().trim();
    String mail = email.getText().trim();
    String office = officeCode.getText().trim();
    String reports = reportsTo.getText().trim();
    String title = (String) employeeRolesComboBox.getSelectedItem();

    // Validate last name
    if (lName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Last Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Validate first name
    if (fName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "First Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Validate extension
    if (ext.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Extension cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Validate email
    if (mail.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Email cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Validate office code
    try {
        Integer.parseInt(office);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Office Code must be a number, and not empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    Integer reportsToInt = null;
    try {
        if (!reports.isEmpty()) {
            reportsToInt = Integer.parseInt(reports);
            // Check if employee exists
            if (!employeeController.getEmployeeDAO().doesEmployeeExist(reports)) {
                JOptionPane.showMessageDialog(this, "Employee for 'Reports To' does not exist.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid number format for 'Reports To'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Validate job title
    if (title.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Job Title cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    boolean success = employeeController.handleAddEmployee(lName, fName, ext, mail, office, reportsToInt, title);

    if (success) {
        String generatedEmployeeNumber = employeeController.getEmployeeDAO().getGeneratedEmployeeNumber();
        String selectedEmployeeRole = (String) employeeRolesComboBox.getSelectedItem();

        String employeeInfoMessage = "Employee added successfully with inputs:\n\n" +
                "Employee Number: " + generatedEmployeeNumber + "\n" +
                "Last Name: " + lName + "\n" +
                "First Name: " + fName + "\n" +
                "Extension: " + ext + "\n" +
                "Email: " + mail + "\n" +
                "Office Code: " + office + "\n" +
                "Reports To: " + (reportsToInt != null ? reportsToInt.toString() : "None") + "\n" +
                "Employee Role: " + selectedEmployeeRole;

        JOptionPane.showMessageDialog(this, employeeInfoMessage, "Addition completed", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Failed to add employee", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
/**
 * Updates an existing employee based on the input fields.
 */
private void updateEmployee() {
    String empNum = updateEmployeeNumber.getText().trim();
    if (empNum.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Employee number cannot be empty for an update.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String lName = updateLastName.getText().trim();
    String fName = updateFirstName.getText().trim();
    String ext = updateExtension.getText().trim();
    String mail = updateEmail.getText().trim();
    String office = updateOfficeCode.getText().trim();
    String reports = updateReportsTo.getText().trim();
    String jobTitle = (String) updateJobTitle.getSelectedItem();

    Integer reportsToInt = null;
    try {
        if (!reports.isEmpty()) {
            reportsToInt = Integer.parseInt(reports);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid format for 'Reports To'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    boolean success = employeeController.handleUpdateEmployee(empNum, lName, fName, ext, mail, office, reportsToInt, jobTitle);
    if (success) {
        StringBuilder updateMessage = new StringBuilder("Employee updated successfully with the following changes:\n\n");
        if (!lName.isEmpty()) updateMessage.append("Last Name: ").append(lName).append("\n");
        if (!fName.isEmpty()) updateMessage.append("First Name: ").append(fName).append("\n");
        if (!ext.isEmpty()) updateMessage.append("Extension: ").append(ext).append("\n");
        if (!mail.isEmpty()) updateMessage.append("Email: ").append(mail).append("\n");
        if (!office.isEmpty()) updateMessage.append("Office Code: ").append(office).append("\n");
        if (!reports.isEmpty()) updateMessage.append("Reports To: ").append(reports).append("\n");
        if (jobTitle != null && !jobTitle.isEmpty()) updateMessage.append("Job Title: ").append(jobTitle).append("\n");

        JOptionPane.showMessageDialog(this, updateMessage.toString(), "Update Completed", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Failed to update employee", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
/**
 * Deletes an employee based on the employee number.
 */
private void deleteEmployee() {
    String employeeNum = searchNumberField.getText();

    try {
        String[] employeeDetails = employeeController.getEmployeeDAO().getEmployeeDetails(employeeNum);
        if (employeeDetails != null && employeeController.handleDeleteEmployee(employeeNum)) {
            String message = String.format("Employee with these details has been deleted successfully from the system: \n Employee Number: %s \n Last Name: %s \n First Name: %s \n Extension: %s \n Email: %s \n Office Code: %s \n Reported To: %s \n Job Title: %s", 
                                           employeeDetails[0],// Employee number
                                           employeeDetails[1], // Last Name
                                           employeeDetails[2],  // First Name
                                           employeeDetails[3],  // Extension
                                           employeeDetails[4],  // Email
                                           employeeDetails[5],  // Office Code
                                           employeeDetails[6], // Reports To
                                           employeeDetails[7]  // Job Title
            );
            JOptionPane.showMessageDialog(this, message, "Employee Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to delete employee. This might be because you typed a letter, or the employee does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException | ClassNotFoundException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error occurred while deleting the employee.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Displays a list of employees in the table.
 *
 * @param employeeList List of employee details to be displayed.
 */
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

/**
 * Main method to start the application.
 */
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
