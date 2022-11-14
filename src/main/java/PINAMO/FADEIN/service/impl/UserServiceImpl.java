package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.user.accessTokenDTO;
import PINAMO.FADEIN.data.dto.user.loginDTO;
import PINAMO.FADEIN.data.dto.user.userDTO;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.UserService;
import exception.CustomException;
import io.jsonwebtoken.Claims;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import utils.JwtUtil;

import java.net.URI;
import java.util.Base64;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

  UserDataHandler userDataHandler;

  @Autowired
  public UserServiceImpl(UserDataHandler userDataHandler) {
    this.userDataHandler = userDataHandler;
  }

  @Override
  public loginDTO saveUser(Long id, String userEmail, String userName, String userPicture) {
    UserEntity userEntity = userDataHandler.saveUserEntity(userEmail, userName, userPicture);

    loginDTO loginDTO = new loginDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg());

    return loginDTO;
  }

  @Override
  public userDTO getUser(Long userId) {
    try {
      UserEntity userEntity = userDataHandler.getUserEntity(userId);

      userDTO userDTO = new userDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg());

      return userDTO;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public loginDTO loginGoogleUser(String accessToken) {
    try {
      String[] check = accessToken.split("\\.");

      Base64.Decoder decoder = Base64.getDecoder();
      String payload = new String(decoder.decode(check[1]));

      JSONObject parser = new JSONObject(payload);

      String userEmail = parser.getString("email");
      String userName  = parser.getString("name");
      String userImage = parser.getString("picture");

      JwtUtil jwtUtil = new JwtUtil();

      UserEntity is_existed = userDataHandler.getUserEntityByEmail(userEmail);

      if (is_existed == null) {
        UserEntity userEntity = userDataHandler.saveUserEntity(userEmail, userName, userImage);

        Date accessExp = jwtUtil.createExp(1);
        String returnAccessToken = jwtUtil.createAccessToken(userEntity.getId());
//    Claims claims = jwtUtil.parseJwtToken(token);

        Date refreshExp = jwtUtil.createExp(24);
        String returnRefreshToken = jwtUtil.createRefreshToken(userEntity.getId());

        System.out.println(returnRefreshToken);

        loginDTO loginDTO = new loginDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg(), returnAccessToken, accessExp.toString(), returnRefreshToken, refreshExp.toString());

        return loginDTO;
      }
      else {
        Date accessExp = jwtUtil.createExp(1);
        String returnAccessToken = jwtUtil.createAccessToken(is_existed.getId());
//    Claims claims = jwtUtil.parseJwtToken(token);

        Date refreshExp = jwtUtil.createExp(24);
        String returnRefreshToken = jwtUtil.createRefreshToken(is_existed.getId());

        System.out.println(returnRefreshToken);

        loginDTO loginDTO = new loginDTO(is_existed.getId(), is_existed.getUserEmail(), is_existed.getUserName(), is_existed.getUserImg(), returnAccessToken, accessExp.toString(), returnRefreshToken, refreshExp.toString());

        return loginDTO;
      }
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public loginDTO loginNaverUser(String accessToken) {
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

      JwtUtil jwtUtil = new JwtUtil();

      UserEntity is_existed = userDataHandler.getUserEntityByEmail(userEmail);

      if (is_existed == null) {
        UserEntity userEntity = userDataHandler.saveUserEntity(userEmail, userName, userImage);

        Date accessExp = jwtUtil.createExp(1);
        String returnAccessToken = jwtUtil.createAccessToken(userEntity.getId());

        Date refreshExp = jwtUtil.createExp(24);
        String returnRefreshToken = jwtUtil.createRefreshToken(userEntity.getId());

        System.out.println(returnRefreshToken);

        loginDTO loginDTO = new loginDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg(), returnAccessToken, accessExp.toString(), returnRefreshToken, refreshExp.toString());

        return loginDTO;
      }
      else {
        Date accessExp = jwtUtil.createExp(1);
        String returnAccessToken = jwtUtil.createAccessToken(is_existed.getId());

        Date refreshExp = jwtUtil.createExp(24*14);
        String returnRefreshToken = jwtUtil.createRefreshToken(is_existed.getId());

        loginDTO loginDTO = new loginDTO(is_existed.getId(), is_existed.getUserEmail(), is_existed.getUserName(), is_existed.getUserImg(), returnAccessToken, accessExp.toString(), returnRefreshToken, refreshExp.toString());

        return loginDTO;
      }
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public accessTokenDTO reissueAccessToken(String refreshToken) throws CustomException {
    JwtUtil jwtUtil = new JwtUtil();

    if(jwtUtil.checkClaim(refreshToken)) {
      Claims claims = jwtUtil.parseJwtToken(refreshToken);
      int userId = (int) claims.get("id");
      Long longUserId = Long.valueOf(userId);

      if (getUser(longUserId) != null) {
        Date accessExp = jwtUtil.createExp(1);
        String accessToken = jwtUtil.createAccessToken(longUserId);

        accessTokenDTO accessTokenDTO = new accessTokenDTO(accessToken, accessExp.toString());

        return accessTokenDTO;
      }
      else return null;
    }
    else return null;
  }

}
