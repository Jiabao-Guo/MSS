package com.jasper.munselfservice.util;

import jakarta.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtil {
    /**
     * Hash a string using SHA-256
     * @param base The string to hash
     * @return The hashed string. Null if an error occurred.
     */
    public static @Nullable String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
