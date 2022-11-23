package utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {
//  @Value("${jwt.security.key}")
  private static final String secretKey = "awdsawds";

  public Date createExp(Integer time) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + Duration.ofHours(time).toMillis());

    return expiration;
  }

  public String createAccessToken(Long userId) {
    Date now = new Date();
    Date expiration = createExp(1);

    return Jwts.builder()
        .setSubject("Access Token")
        .claim("id",userId)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
        .compact();
  }

  public String createRefreshToken(Long userId) {
    Date now = new Date();
    Date expiration = createExp(24*14);

    return Jwts.builder()
        .setSubject("Access Token")
        .claim("id",userId)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
        .compact();
  }

  public boolean checkClaim(String accessToken) {
    try {
      String jwt = accessToken.split(" ")[1];

      Claims claims = Jwts.parser()
          .setSigningKey(secretKey.getBytes())
          .parseClaimsJws(jwt).getBody();
      return true;

    }catch(ExpiredJwtException e) {
      return false;

    }catch(JwtException e) {
      return false;
    }
  }

  @Bean
  public Claims parseJwtToken(String accessToken) {
    String jwt = accessToken.split(" ")[1];

    return Jwts.parser()
        .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
        .parseClaimsJws(jwt)
        .getBody();
  }

  @Bean
  public int getUserIdInJwtToken(String accessToken) {
    String jwt = accessToken.split(" ")[1];

    return Integer.parseInt(
        Jwts.parser()
        .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
        .parseClaimsJws(jwt)
        .getBody()
        .get("id")
        .toString());
  }
}
