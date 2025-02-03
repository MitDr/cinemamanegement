package com.project.cinemamanagement.Service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String generateToken(String userName);

    String generateRefreshToken(String userName);

    String creatRefreshToken(Map<String, Object> claims, String userName);

    String createToken(Map<String, Object> claims, String userName);

    Key getSignKey();
    String extractUsername(String token,int type);

    Date extractExpiration(String token,int type);

    <T> T extractClaim(String token, Function<Claims,T> claimsResolver,int type);

    Claims extractAllClaims(String token,int type);

    Boolean isTokenExpired(String token,int type);

    Boolean validateToken(String token, UserDetails userDetails);
    Boolean isCorrectTokenExpired(String token, UserDetails userDetails);
}
