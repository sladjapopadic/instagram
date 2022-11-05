package com.itengine.instagram.security.jwt;

import com.itengine.instagram.security.jwt.configuration.JwtConfiguration;
import com.itengine.instagram.user.model.User;
import com.itengine.instagram.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtService {

    private final JwtConfiguration jwtConfiguration;
    private final UserRepository userRepository;
    private final String secretKey;
    private static final String USER_ID_KEY = "userId";

    public JwtService(JwtConfiguration jwtConfiguration, UserRepository userRepository) {
        this.jwtConfiguration = jwtConfiguration;
        this.userRepository = userRepository;
        secretKey = jwtConfiguration.getSecretKey();
    }

    public String createToken(String username) {
       return createToken(username, null);
    }

    public String createToken(String username, Long userId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(USER_ID_KEY, userId);
        Date now = new Date();
        int validityInHours = jwtConfiguration.getValidityInHours();
        Date validity = new Date(now.getTime() + convertToMilliseconds(validityInHours));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private long convertToMilliseconds(int hours) {
        return 1000L * 60 * 60 * hours;
    }

    public Authentication getAuthentication(String token) {
        Long userId = getTokenUserId(token);
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return new UsernamePasswordAuthenticationToken(null, "", new ArrayList<>());
        }

        return new UsernamePasswordAuthenticationToken(user.get(), "", new ArrayList<>());
    }

    public String getTokenSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getTokenUserId(String token) {
        Integer userId = (Integer) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(USER_ID_KEY);
        return Long.valueOf(userId);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean isValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
