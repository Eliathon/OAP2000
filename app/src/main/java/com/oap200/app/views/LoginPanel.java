// Created by Sindre

package com.oap200.app.views;

import javax.swing.*;

import com.oap200.app.utils.ConfigLoader;
import com.oap200.app.utils.LoginCredentials;
import com.oap200.app.utils.PasswordHasher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JFrame {

    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private PasswordHasher passwordHasher = new PasswordHasher();

    String configFilePath = "app\\src\\main\\resources\\config.json";

    public LoginPanel() {
        super("User Login");

        userField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        createAccountButton = new JButton("Create Account");
        panel.add(createAccountButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                char[] password = passwordField.getPassword();
                if (authenticate(username, password)) {
                    // Authentication succeeded, close login window
                    dispose();
                    // Open main application window
                    new MainFrame().setVisible(true);
                } else {
                    // Authentication failed
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Invalid username or password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Handle create account button action
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                char[] password = passwordField.getPassword();

                // Instantiate ConfigLoader with correct file path
                ConfigLoader configLoader = new ConfigLoader(configFilePath);

                // Call createAccount method
                if (configLoader.createAccount(username, password)) {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Account created successfully",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Failed to create an account",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null); // Center on screen
    }

    public boolean authenticate(String username, char[] password) {
        try {
            ConfigLoader configLoader = new ConfigLoader(configFilePath);
            // Load the encrypted credentials for the given username
            LoginCredentials credentials = configLoader.loadCredentials(username);

            // Use the password hasher to check the entered password against the stored
            // hashed password
            if (credentials.getUsername().equals(username)
                    && passwordHasher.checkPassword(new String(password), credentials.getPassword())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPanel().setVisible(true);
            }
        });
    }
}