package com.itengine.instagram.util;

public class CredentialRegex {
    public static final String USERNAME_REGEX = "^[a-z\\d_.-]+$";
    public static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d)(?=.*?[#?!@$%^&*-.,]).{8,}$";
}
