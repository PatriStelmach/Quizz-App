package com.pjatk.QuizzApp.Security;

import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import com.auth0.jwt.JWT;

@Service
public class JwtService
{

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;



    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return new DefaultClaims(
                JWT
                .require(getSignAlgorithm())
                .build()
                .verify(token)
                .getClaims());
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails)
    {

        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration)
    {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return JWT
                .create()
                .withPayload(extraClaims)
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(jwtExpiration))
                .withClaim("authorities", authorities)
                .sign(getSignAlgorithm());
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }


    private Algorithm getSignAlgorithm()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Algorithm.HMAC256(keyBytes);
    }
}
