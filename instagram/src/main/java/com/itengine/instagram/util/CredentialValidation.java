package com.itengine.instagram.util;

import com.itengine.instagram.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidation {

    public static void validateUsernameFormat(String username) {
        if (!isPatternMatched(CredentialRegex.USERNAME_REGEX, username)) {
            throw new ValidationException("Username " + username + " format not valid.");
        }
    }

    public static void validatePasswordFormat(String password) {
        if (!isPatternMatched(CredentialRegex.PASSWORD_REGEX, password)) {
            throw new ValidationException("Password " + password + " format not valid.");
        }
    }

    private static boolean isPatternMatched(String regex, String valueToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valueToMatch);
        return matcher.matches();
    }
}
