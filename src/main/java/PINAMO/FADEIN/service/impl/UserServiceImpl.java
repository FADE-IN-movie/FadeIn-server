package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.user.AccessTokenDTO;
import PINAMO.FADEIN.data.dto.user.LoginDTO;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.UserService;
import exception.CustomException;
import io.jsonwebtoken.Claims;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import PINAMO.FADEIN.utils.JwtUtil;
import PINAMO.FADEIN.utils.UserUtil;

import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

  UserUtil userUtil;
  UserDataHandler userDataHandler;

  @Autowired
  public UserServiceImpl(UserUtil userUtil, UserDataHandler userDataHandler) {
    this.userUtil = userUtil;
    this.userDataHandler = userDataHandler;
  }

  @Override
  public LoginDTO saveUser(Long id, String userEmail, String userName, String userPicture) {
    UserEntity userEntity = userDataHandler.saveUserEntity(userEmail, userName, userPicture);

    LoginDTO loginDTO = new LoginDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg());

    return loginDTO;
  }

  @Override
  public UserEntity getUser(Long userId) {
    try {
      return userDataHandler.getUserEntity(userId);
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public LoginDTO loginGoogleUser(String accessToken) {
    try {
      String[] check = accessToken.split("\\.");

      Base64.Decoder decoder = Base64.getDecoder();
      String payload = new String(decoder.decode(check[1]));

      JSONObject parser = new JSONObject(payload);

      String userEmail = parser.getString("email");
      String userName  = parser.getString("name");
      String userImage = parser.getString("picture");

      UserEntity is_existed = userDataHandler.getUserEntityByEmail(userEmail);

      if (is_existed == null) {
        UserEntity userEntity = userDataHandler.saveUserEntity(userEmail, userName, userImage);

        Long userId = userEntity.getId();

        LoginDTO loginDTO = userUtil.issueTokens(userEntity, userId);

        return loginDTO;
      }
      else {
        Long userId = is_existed.getId();

        LoginDTO loginDTO = userUtil.issueTokens(is_existed, userId);

        return loginDTO;
      }
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public LoginDTO loginNaverUser(String accessToken) {
    try {
      URI uri = UriComponentsBuilder
          .fromUriString("https://openapi.naver.com/v1/nid/me")
          .encode()
          .build()
          .toUri();

      HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer "+accessToken.split("=")[0]);

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

      RestTemplate restTemplate = new RestTemplate();

      ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

      JSONObject parser = new JSONObject(requestEntity.getBody());

      JSONObject response = parser.getJSONObject("response");

      String userEmail = response.getString("email");
      String userName  = response.getString("name");
      String userImage = response.getString("profile_image");

      UserEntity is_existed = userDataHandler.getUserEntityByEmail(userEmail);

      if (is_existed == null) {
        UserEntity userEntity = userDataHandler.saveUserEntity(userEmail, userName, userImage);

        Long userId = userEntity.getId();

        LoginDTO loginDTO = userUtil.issueTokens(userEntity, userId);

        return loginDTO;
      }
      else {
        Long userId = is_existed.getId();

        LoginDTO loginDTO = userUtil.issueTokens(is_existed, userId);

        return loginDTO;
      }
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public AccessTokenDTO reissueAccessToken(String refreshToken) throws CustomException {
    JwtUtil jwtUtil = new JwtUtil();

    String token = "Bearer " + refreshToken;
    if(jwtUtil.checkClaim(token)) {
      Claims claims = jwtUtil.parseJwtToken(token);
      int userId = (int) claims.get("id");
      Long longUserId = Long.valueOf(userId);

      if (getUser(longUserId) != null) {
        Map<String,String> accessTokenMap = jwtUtil.createAccessToken(longUserId);

        String accessToken = accessTokenMap.get("accessToken");
        String accessTokenExp = accessTokenMap.get("accessTokenExp");

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(accessToken, accessTokenExp);

        return accessTokenDTO;
      }
      else {
        return null;
      }
    }
    else {
      return null;
    }
  }

}
