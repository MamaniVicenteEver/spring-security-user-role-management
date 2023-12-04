package com.roles.usermanagement.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Clase de utilidad para la generación y validación de tokens JWT (JSON Web Tokens).
 */
@Component
public class JwtUtil {

    // Emisor del token JWT
    private static final String ISSUER = "User_R0les_M4n4gement";
    // Tiempo de expiración del token JWT (15 días)
    private static final long TOKEN_EXPIRATION_TIME_MILLIS = TimeUnit.DAYS.toMillis(15);

    // Clave secreta para firmar y verificar los tokens JWT
    private static final String SECRET_KEY = "User_M4n4gement";

    // Algoritmo de firma para los tokens JWT (HMAC256)
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Crea un token JWT con el nombre de usuario proporcionado como sujeto.
     *
     * @param username El nombre de usuario para el cual se crea el token.
     * @return El token JWT generado.
     */
    public String create(String username) {
        return JWT.create()
            .withSubject(username)
            .withIssuer(ISSUER)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME_MILLIS))
            .sign(ALGORITHM);
    }

    /**
     * Verifica si un token JWT es válido.
     *
     * @param jwt El token JWT que se verifica.
     * @return true si el token es válido, false si no lo es.
     */
    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM)
                .build()
                .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * Obtiene el nombre de usuario del token JWT.
     *
     * @param jwt El token JWT del cual se obtiene el nombre de usuario.
     * @return El nombre de usuario extraído del token.
     */
    public String getUsername(String jwt) {
        return JWT.require(ALGORITHM)
            .build()
            .verify(jwt)
            .getSubject();
    }
}