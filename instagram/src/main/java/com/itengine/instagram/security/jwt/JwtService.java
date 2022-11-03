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
import java.util.Date;


@Component
public class JwtService {

    private JwtConfiguration jwtConfiguration;
    private UserRepository userRepository;
    private String secretKey;

    public JwtService(JwtConfiguration jwtConfiguration, UserRepository userRepository) {
        this.jwtConfiguration = jwtConfiguration;
        this.userRepository = userRepository;
        secretKey = jwtConfiguration.getSecretKey();
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Date now = new Date();
        Date validity = new Date(now.getTime() + (1000 * 60 * 60 * jwtConfiguration.getValidityInHours()));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        User user = userRepository.findByUsernameIgnoreCase(getUsername(token));
        return new UsernamePasswordAuthenticationToken(user, "");
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
}
