// Created by Sindre

package com.oap200.app.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    public String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
