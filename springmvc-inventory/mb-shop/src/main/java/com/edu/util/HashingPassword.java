package com.edu.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class HashingPassword {
    static final String SALT = "inventory";

    public static String encrypt(String originPassword) {
        String result = null;
        byte[] salt = SALT.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashPass = md.digest(originPassword.getBytes(StandardCharsets.US_ASCII));
            result = Base64.getEncoder().encodeToString(hashPass).substring(0, 32);
        } catch (Exception e) {
        }
        return result;
    }
}
