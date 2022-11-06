package com.itengine.instagram.email.util;

import com.itengine.instagram.exception.ValidationException;
import org.apache.commons.validator.routines.EmailValidator;

public class MailValidator {

    public static void validateEmail(String email) {
        if (!isValid(email)) {
            throw new ValidationException("Email " + email + " format not valid.");
        }
    }

    private static boolean isValid(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }
}
