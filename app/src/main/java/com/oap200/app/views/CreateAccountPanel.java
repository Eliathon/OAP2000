package com.oap200.app.views;

import com.oap200.app.controllers.UserController;
import javax.swing.*;
import java.awt.*;

public class CreateAccountPanel extends JPanel {
    private LoginPanel loginPanel;
    private UserController userController;

    public CreateAccountPanel(LoginPanel loginPanel, UserController userController) {
        this.loginPanel = loginPanel;
        this.userController = userController;
        setLayout(new GridLayout(5, 2));
        initializeUI();
    }

    private void initializeUI() {
        JTextField newUserField = new JTextField(15);
        JPasswordField newPasswordField = new JPasswordField(15);
        JButton createButton = new JButton("Create Account");
        JButton cancelButton = new JButton("Cancel");

        add(new JLabel("Username:"));
        add(newUserField);
        add(new JLabel("Password:"));
        add(newPasswordField);
        add(createButton);
        add(cancelButton);

        createButton.addActionListener(e -> {
            if (userController.createAccount(newUserField.getText(), newPasswordField.getPassword())) {
                JOptionPane.showMessageDialog(this,
                        "Account created successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                loginPanel.showLoginPanel();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to create an account",
                        "Account Creation Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> loginPanel.showLoginPanel());
    }
}
