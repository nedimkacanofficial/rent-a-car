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

    /**
     * Generates a JSON Web Token (JWT) for the given authentication.
     * <p>
     * This method takes an Authentication object, extracts the user details from it,
     * and generates a JWT containing the user's ID as the subject. The token is signed
     * using the HS512 algorithm with the configured signing key.
     *
     * @param authentication The Authentication object representing the authenticated user.
     * @return String A JWT representing the user's authentication details.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject(String.valueOf(userDetails.getId())).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
    }

    /**
     * Extracts the user ID from a JSON Web Token (JWT).
     * <p>
     * This method takes a JWT as input, parses it using the configured signing key,
     * and extracts the subject claim, which represents the user's ID. The ID is then
     * converted to a Long and returned.
     *
     * @param token The JWT from which to extract the user ID.
     * @return Long The user's ID extracted from the JWT.
     * @throws ExpiredJwtException      If the JWT has expired.
     * @throws UnsupportedJwtException  If the JWT is not supported.
     * @throws MalformedJwtException    If the JWT is malformed.
     * @throws SignatureException       If the JWT signature is invalid.
     * @throws IllegalArgumentException If the provided token is null or empty.
     */
    public Long getIdFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        String strId = claims.getSubject();
        return Long.parseLong(strId);
    }

    /**
     * Validates a JSON Web Token (JWT) for integrity and expiration.
     * <p>
     * This method takes a JWT as input, parses it using the configured signing key,
     * and checks for integrity and expiration. If the token is valid, it returns true;
     * otherwise, it logs the specific error and returns false.
     *
     * @param token The JWT to validate.
     * @return boolean True if the JWT is valid; false otherwise.
     * @throws ExpiredJwtException      If the JWT has expired.
     * @throws UnsupportedJwtException  If the JWT is not supported.
     * @throws MalformedJwtException    If the JWT is malformed.
     * @throws SignatureException       If the JWT signature is invalid.
     * @throws IllegalArgumentException If the provided token is null or empty.
     */
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

    /**
     * Retrieves the signing key used for JSON Web Token (JWT) generation and validation.
     * <p>
     * This method decodes the JWT secret key from Base64 and returns it as a Key object
     * suitable for use with HMAC-based signature algorithms.
     *
     * @return Key The signing key used for JWT operations.
     * @throws IllegalArgumentException If the JWT secret key is not properly encoded or is empty.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
