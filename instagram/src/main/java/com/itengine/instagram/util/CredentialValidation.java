package com.itengine.instagram.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialValidation {

    public static boolean isPatternMatched(String regex, String valueToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valueToMatch);
        return matcher.matches();
    }
}
