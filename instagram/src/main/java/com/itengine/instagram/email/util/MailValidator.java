package com.itengine.instagram.email.util;

import org.apache.commons.validator.routines.EmailValidator;

public class MailValidator {

    public static boolean isValid(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }
}
