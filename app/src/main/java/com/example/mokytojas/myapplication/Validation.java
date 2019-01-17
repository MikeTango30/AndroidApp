package com.example.mokytojas.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String VALID_USERNAME_PATTERN = "^[A-Za-z]{5,30}$";
    private static final String VALID_PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*]{5,30}$";

    public static boolean isValidUsername(String username) {
        Pattern credentialPattern = Pattern.compile(VALID_USERNAME_PATTERN);
        Matcher credentialMatcher = credentialPattern.matcher(username);
        return credentialMatcher.find();
    }

    public static boolean isValidPassword(String password) {
        Pattern credentialPattern = Pattern.compile(VALID_PASSWORD_PATTERN);
        Matcher credentialMatcher = credentialPattern.matcher(password);
        return credentialMatcher.find();
    }
}