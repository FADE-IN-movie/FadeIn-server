package utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
//  @Value("${jwt.security.key}")
  private static final String secretKey = "awdsawds";

  public Date createExp(Integer time) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + Duration.ofDays(time).toMillis());

    return expiration;
  }

  public Map<String, String> createAccessToken(Long userId) {
    Date now = new Date();
    Date expiration = createExp(2);

    String accessToken = Jwts.builder()
        .setSubject("Access Token")
        .claim("id",userId)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
        .compact();

    Map<String, String> map = new HashMap<>();
    map.put("accessToken", accessToken);
    map.put("accessTokenExp", expiration.toString());

    return map;
  }

  public Map<String, String> createRefreshToken(Long userId) {
    Date now = new Date();
    Date expiration = createExp(2 * 7);

    String refreshToken = Jwts.builder()
        .setSubject("Refresh Token")
        .claim("id",userId)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
        .compact();

    Map<String, String> map = new HashMap<>();
    map.put("refreshToken", refreshToken);
    map.put("refreshTokenExp", expiration.toString());

    return map;
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
    }catch (Exception e) {
      return false;
    }
  }

  @Bean
  public Claims parseJwtToken(String accessToken) {
    try{
      String jwt = accessToken.split(" ")[1];

      return Jwts.parser()
          .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
          .parseClaimsJws(jwt)
          .getBody();
    }catch(ExpiredJwtException e) {
      return null;
    }catch(JwtException e) {
      return null;
    }catch (Exception e) {
      return null;
    }
  }

  @Bean
  public int getUserIdInJwtToken(String accessToken) {
    try {
      String jwt = accessToken.split(" ")[1];

      return Integer.parseInt(
          Jwts.parser()
              .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
              .parseClaimsJws(jwt)
              .getBody()
              .get("id")
              .toString());
    } catch (ExpiredJwtException e) {
      return 0;
    } catch (JwtException e) {
      return 0;
    } catch (Exception e) {
      return 0;
    }
  }
}
