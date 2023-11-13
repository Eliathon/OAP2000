package com.oap200.app.controllers;

import com.oap200.app.utils.ConfigLoader;
import com.oap200.app.utils.PasswordHasher;

public class UserController {

    private String configFilePath = "app\\src\\main\\resources\\config.json";
    private ConfigLoader configLoader = new ConfigLoader(configFilePath);
    private PasswordHasher passwordHasher = new PasswordHasher();

    public boolean authenticate(String username, char[] password) {
        try {
            return passwordHasher.checkPassword(new String(password),
                    configLoader.loadCredentials(username).getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createAccount(String username, char[] password) {
        if (!isPasswordComplex(password)) {
            return false;
        }
        return configLoader.createAccount(username, password);
    }

    private boolean isPasswordComplex(char[] password) {
        String passwordStr = new String(password);
        return passwordStr.matches("(?=.*[0-9])" + // At least one digit
                "(?=.*[a-z])" + // At least one lower case letter
                "(?=.*[A-Z])" + // At least one upper case letter
                "(?=.*[!@#$%^&+=])" + // At least one special character
                "(?=\\S+$).{8,}"); // At least 8 characters
    }
}
