package com.ebra.PFMS.config;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash the password (using BCrypt)
    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Check the password against a hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}