package com.rentacar.security.jwt;

import com.rentacar.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtilsService {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtilsService.class);

    @Value("${rentacar.app.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${rentacar.app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(String.valueOf(userDetails.getId())).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
    }

    public Long getIdFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        String strId = claims.getSubject();
        return Long.parseLong(strId);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT Token is unsupported {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("JWT Token is malformed {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Token illegal args {}", e.getMessage());
        }
        return false;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
