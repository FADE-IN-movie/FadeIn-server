package PINAMO.FADEIN.utils;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.user.LoginDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

  @Value("${jwt.security.key}")
  private String secretKey;

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

  public LoginDTO issueTokens(UserEntity userEntity, Long userId) {
    Map<String, String> accessTokenMap = createAccessToken(userId);
    Map<String, String> refreshTokenMap = createRefreshToken(userId);

    String returnAccessToken = accessTokenMap.get("accessToken");
    String returnAccessTokenExp = accessTokenMap.get("accessTokenExp");

    String returnRefreshToken = refreshTokenMap.get("refreshToken");
    String returnRefreshTokenExp = refreshTokenMap.get("refreshTokenExp");

    LoginDTO loginDTO = new LoginDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg(), returnAccessToken, returnAccessTokenExp, returnRefreshToken, returnRefreshTokenExp);

    System.out.println(returnAccessToken);

    return loginDTO;
  }

}
