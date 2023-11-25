package com.oap200.app.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.prefs.Preferences;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.utils.ButtonBuilder;
import com.oap200.app.controllers.EmployeesController;
public class EmployeeManagementPanel extends JFrame {

    private static final String PREF_X = "window_x";
    private static final String PREF_Y = "window_y";
    private JComboBox<String> reportsToComboBox;
    private JTextField employeeNumberField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField extensionField;
    private JTextField emailField;
    private JTextField officeCodeField;
    private JTextField reportsToField;
    private JTextField jobTitleField;
    private JTable employeeTable;

    private JTextField searchNumField;
    private JTextField searchNameField;

// Define methods to retrieve input values from GUI components
public String getEmployeeNumber() {
    return employeeNumberField.getText().trim();
}

public String getLastName() {
    return lastNameField.getText().trim();
}

public String getFirstName() {
    return firstNameField.getText().trim();
}

public String getExtension() {
    return extensionField.getText().trim();
}

public String getEmail() {
    return emailField.getText().trim();
}

public String getOfficeCode() {
    return officeCodeField.getText().trim();
}

public String getReportsTo() {
    return reportsToField.getText().trim();
}

public String getJobTitle() {
    return jobTitleField.getText().trim();
}

// ... Other methods ...




    public EmployeeManagementPanel() {    
        initializeFields();
        reportsToComboBox = new JComboBox<>();
      initView();

        // Load the last window position
        Preferences prefs = Preferences.userNodeForPackage(EmployeeManagementPanel.class);
        int x = prefs.getInt(PREF_X, 50); // Default x position
        int y = prefs.getInt(PREF_Y, 50); // Default y position
        setLocation(x, y);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prefs.putInt(PREF_X, getLocation().x);
                prefs.putInt(PREF_Y, getLocation().y);
            }
        });

        // Set up the layout for the frame
        setLayout(new BorderLayout());

    JButton backButton = ButtonBuilder.createBlueBackButton(() -> {/* Action for Back Button */});
    JButton logoutButton = ButtonBuilder.createRedLogoutButton(() -> {/* Action for Logout Button */});
    JButton viewButton = ButtonBuilder.createViewButton(this::viewEmployees);
    JButton addButton = ButtonBuilder.createAddButton(this::addEmployee);
    JButton deleteButton = ButtonBuilder.createDeleteButton(this::deleteEmployee);
    JButton updateButton = ButtonBuilder.createUpdateButton(() -> {/* Action for Update Button */});


        // Initialize JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: View Employee
        JPanel panel1 = new JPanel(new BorderLayout());
        addComponentsToPanelView(panel1);
        panel1.add(viewButton, BorderLayout.SOUTH);
        tabbedPane.addTab("View Employee", null, panel1, "Click to view");

        // Tab 2: Add Employee
        JPanel panel2 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel2);
        panel2.add(addButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Add Employee", null, panel2, "Click to add");

        // Tab 3: Update Employee
        JPanel panel3 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel3);
        panel3.add(updateButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Update Employee", null, panel3, "Click to Update");

        // Tab 4: Delete Employee
        JPanel panel4 = new JPanel(new BorderLayout());
        addComponentsToPanel(panel4);
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

        viewButton.addActionListener(e -> viewEmployees());
    }

    

    

    private void viewEmployees() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<String[]> employeeList = employeeDAO.fetchEmployees();
        String[] columnNames = { "Employee Number", "Last Name", "First Name", "Extension", "E-mail", "Office Code", "Reports To", "Job Title" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (String[] row : employeeList) {
            model.addRow(row);
        }
        employeeTable.setModel(model);
    }

    private void searchNum() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<String[]> searchResults = employeeDAO.searchNum(PREF_X);
    }

    public void showAddSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    public void showAddErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Addition Error", JOptionPane.ERROR_MESSAGE);
    }


private void addEmployee() {
    String employeeNumber = employeeNumberField.getText().trim();
   String lastName = lastNameField.getText().trim();
   String firstName = firstNameField.getText().trim();
    String extension = extensionField.getText().trim();
    String email = emailField.getText().trim();
    String officeCode = officeCodeField.getText().trim();
    String reportsTo = reportsToField.getText().trim();
    String jobTitle = jobTitleField.getText().trim();


    if (employeeNumber.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || extension.isEmpty() || email.isEmpty() || officeCode.isEmpty() || reportsTo.isEmpty() || jobTitle.isEmpty()) {
        JOptionPane.showMessageDialog(this, "TESTEREN");
        return;
    }

EmployeeDAO employeeDAO = new EmployeeDAO();
boolean success = employeeDAO.addEmployee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);

if (success){
    JOptionPane.showMessageDialog(this, "Employee added successfully!");
    viewEmployees();
} else {
        JOptionPane.showMessageDialog(this, "Error: Could not create new employee.", "Addition Error", JOptionPane.ERROR_MESSAGE);
}

}

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int employeeNumber = Integer.parseInt((String) employeeTable.getValueAt(selectedRow, 0));
        EmployeeDAO employeeDAO = new EmployeeDAO();
    
        if (employeeDAO.deleteEmployee(employeeNumber)) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            viewEmployees(); // Refresh the table to reflect the deletion
        } else {
            JOptionPane.showMessageDialog(this, "Error: Could not delete the employee.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    


    private void initializeFields() {
        employeeNumberField = new JTextField(10);
        lastNameField = new JTextField(10);
        firstNameField = new JTextField(10);
        extensionField = new JTextField(10);
        emailField = new JTextField(10);
        officeCodeField = new JTextField(10);
        reportsToField = new JTextField(10);
        jobTitleField = new JTextField(10);
    }

    private void addComponentsToPanel(JPanel panel) {
        JPanel labelPanel = new JPanel(new GridLayout(8, 1)); // 8 labels
        JPanel fieldPanel = new JPanel(new GridLayout(8, 1)); // 8 fields

        // Cloning fields for each tab
        
        labelPanel.add(new JLabel("Employee Number:"));
    fieldPanel.add(employeeNumberField);
    labelPanel.add(new JLabel("Last Name:"));
    fieldPanel.add(lastNameField);
    labelPanel.add(new JLabel("First Name:"));
    fieldPanel.add(firstNameField);
    labelPanel.add(new JLabel("Extension:"));
    fieldPanel.add(extensionField);
    labelPanel.add(new JLabel("Email:"));
    fieldPanel.add(emailField);
    labelPanel.add(new JLabel("Office Code:"));
    fieldPanel.add(officeCodeField);
    labelPanel.add(new JLabel("Reports To:"));
    fieldPanel.add(reportsToField);
    labelPanel.add(new JLabel("Job Title:"));
    fieldPanel.add(jobTitleField);


        panel.add(labelPanel, BorderLayout.WEST);
        panel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void addComponentsToPanelView(JPanel panelView) {
        panelView.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Label weight
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Adding the "Employee Number:" label
        inputPanel.add(new JLabel("Employee Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        employeeNumberField = new JTextField(10);  // Use the class field
    inputPanel.add(employeeNumberField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3; // Reset to label weight
        // Adding the "Last Name:" label
        inputPanel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7; // Field weight
        JTextField lastName = new JTextField(10);
        inputPanel.add(lastName, gbc);

        panelView.add(inputPanel, BorderLayout.NORTH);

        // Now, create the scrollPane with the employeeTable right here:
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Create a container panel for the table to align it to the left
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panelView.add(tableContainer, BorderLayout.CENTER);
    }

public void initView(){

    viewButton.addActionListener(e -> viewEmployees());
    addButton.addActionListener(e -> addEmployee());
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
