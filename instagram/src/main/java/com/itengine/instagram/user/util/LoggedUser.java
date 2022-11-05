package com.itengine.instagram.user.util;

import com.itengine.instagram.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUser {

    private static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUsername() {
        return getUser().getUsername();
    }

    public static Long getId() {
        return getUser().getId();
    }
}
