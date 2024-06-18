package com.inspire.com.checkpoint2.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomPasswordEncoder {
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String encode(String password) {
        return encoder.encode(password);
    }
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
