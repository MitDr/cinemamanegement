package com.project.cinemamanagement.Service.ServiceImpl;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Repository.UserRepository;
import com.project.cinemamanagement.Service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtImpl implements JwtService {
    @Value("${jwt.secret}")
    public String SECRET ;

    @Value("${jwt.secret2}")
    public String SECRETREF ;

    @Value("${jwt.accessexpiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refreshtokenexpiration}")
    private Long refreshTokenExpiration;

    private final UserRepository userRepository;
    @Override
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    @Override
    public String generateRefreshToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return creatRefreshToken(claims, userName);
    }


    @Override
    public String creatRefreshToken(Map<String, Object> claims, String userName) {
        long expirationTimeMillis = System.currentTimeMillis() + refreshTokenExpiration;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(getRefreshSignKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public String createToken(Map<String, Object> claims, String userName) {
        long expirationTimeMillis = System.currentTimeMillis() + accessTokenExpiration;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }


    @Override
    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Key getRefreshSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRETREF);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    @Override
    public String extractUsername(String token,int type) {
        return extractClaim(token, Claims::getSubject,type);
    }

    @Override
    public Date extractExpiration(String token,int type) {
        return extractClaim(token, Claims::getExpiration,type);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, int type) {
        final Claims claims = extractAllClaims(token,type);
        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token,int type) {
        Claims claims = switch (type) {
            case 0 -> Jwts
                    .parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            case 1 -> Jwts
                    .parser()
                    .setSigningKey(getRefreshSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            default -> null;
        };
        return claims;
    }

    @Override
    public Boolean isTokenExpired(String token,int type) {

        return extractExpiration(token,type).before(new Date());
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token,0);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token,0));
    }

    @Override
    public Boolean isCorrectTokenExpired(String token, UserDetails userDetails) {
        final String username = extractUsername(token,1);
        User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User aaa not found"));

        String token2 = user.getRefreshToken();
        if (token2 != null) {
            if (token.equals(token2)) {
                return true;
            }
        }
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token,1));
    }
}
