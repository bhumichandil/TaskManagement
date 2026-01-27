package com.task.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.task.ENUM.Permission;
import com.task.Entity.UserAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private final Key key;
    private static final long EXPIRE_TOKEN = 1000L * 60 * 60 * 24; // 24 hours

    public JWTUtil() {
        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isEmpty()) {
            
            secret = "ReplaceThisWithVeryStrongSecretKey12345";
        }

        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserAuth user) {

        Set<Permission> permissions =
                RolePermissionConfig.getRolePermissions().get(user.getRole());

        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .claim("role", user.getRole().name())
                .claim("permissions",
                        permissions.stream()
                                .map(Enum::name)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TOKEN))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }
   public  String extractToken(String header) {
	   if(header != null && header.startsWith("Bearer")){
	   		return header.substring(7);
   }
	   return null;
}
}