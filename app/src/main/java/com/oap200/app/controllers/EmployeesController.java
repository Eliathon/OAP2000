package com.oap200.app.controllers;

import com.oap200.app.models.EmployeeDAO;
import com.oap200.app.views.EmployeeManagementPanel;

import java.util.List;

import javax.swing.JOptionPane;

public class EmployeesController {
    
    private EmployeeDAO employeeDAO;
    private EmployeeManagementPanel employeeManagementPanel;

    public EmployeesController(EmployeeDAO employeeDAO, EmployeeManagementPanel employeeManagementPanel) {
        this.employeeDAO = employeeDAO;
        this.employeeManagementPanel = employeeManagementPanel;
        initView();
    }

    private void initView() {
        employeeManagementPanel.addBackButtonListener(e -> handleBackButton());
        employeeManagementPanel.addLogoutButtonListener(e -> handleLogoutButton());
        employeeManagementPanel.addViewButtonListener(e -> handleViewButton());
        employeeManagementPanel.addAddButtonListener(e -> handleAddButton());
        employeeManagementPanel.addUpdateButtonListener(e -> handleUpdateButton());
        employeeManagementPanel.addDeleteButtonListener(e -> handleDeleteButton());
    }

    private void handleBackButton() {
        // Handle back button action
    }

    private void handleLogoutButton() {
        // Handle logout button action
    }

    private void handleViewButton() {
        List<String[]> employeeList = employeeDAO.fetchEmployees();
        employeeManagementPanel.displayEmployees(employeeList);
    }

    private void handleAddButton() {
        // Handle add button action
    }

    private void handleUpdateButton() {
        // Handle update button action
    }

    private void handleDeleteButton() {
        int selectedRow = employeeManagementPanel.getSelectedRow();
        if (selectedRow != -1) {
            int employeeNumber = employeeManagementPanel.getSelectedEmployeeNumber(selectedRow);
            boolean success = employeeDAO.deleteEmployee(employeeNumber);
            if (success) {
                employeeManagementPanel.showDeleteSuccessMessage();
                handleViewButton(); // Refresh the table to reflect the deletion
            } else {
                employeeManagementPanel.showDeleteErrorMessage();
            }
        } else {
            employeeManagementPanel.showNoSelectionErrorMessage();
        }
    }
}
