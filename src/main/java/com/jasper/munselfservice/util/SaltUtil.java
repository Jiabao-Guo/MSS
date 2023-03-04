package com.jasper.munselfservice.util;

public class SaltUtil {
    public static String timestampToSalt() {
        return String.valueOf(System.currentTimeMillis() / 1000 / 10);
    }

    public static String calculateSaltForLogin(
        String plaintextSha256Sha256,
        Integer number
    ) {
        return HashUtil.sha256(
            plaintextSha256Sha256
                + timestampToSalt()
                + number.toString()
        );
    }
}
