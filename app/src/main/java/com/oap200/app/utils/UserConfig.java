// Created by Sindre

package com.oap200.app.utils;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserConfig {

    private String username;
    private String passwordHash;

    // Constructor
    public UserConfig(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    // Factory method to create a UserConfig from a JSON object
    public static UserConfig fromJson(JSONObject userObject) {
        String base64Username = userObject.getString("username");
        String username = new String(Base64.getDecoder().decode(base64Username), StandardCharsets.UTF_8);
        String passwordHash = userObject.getString("password");

        return new UserConfig(username, passwordHash);
    }

    // Method to load all user configs from a JSON array string
    public static UserConfig[] loadAll(String jsonContent) {
        JSONArray usersArray = new JSONArray(jsonContent);
        UserConfig[] configs = new UserConfig[usersArray.length()];

        for (int i = 0; i < usersArray.length(); i++) {
            configs[i] = fromJson(usersArray.getJSONObject(i));
        }

        return configs;
    }
}
