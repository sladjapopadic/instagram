package com.itengine.instagram.security.jwt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {

    private String secretKey;
    private int validityInHours;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getValidityInHours() {
        return validityInHours;
    }

    public void setValidityInHours(int validityInHours) {
        this.validityInHours = validityInHours;
    }
}
