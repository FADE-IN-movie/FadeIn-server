package PINAMO.FADEIN.utils;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.user.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserUtil {

  JwtUtil jwtUtil;

  public LoginDTO issueTokens(UserEntity userEntity, Long userId) {
    Map<String, String> accessTokenMap = jwtUtil.createAccessToken(userId);
    Map<String, String> refreshTokenMap = jwtUtil.createRefreshToken(userId);

    String returnAccessToken = accessTokenMap.get("accessToken");
    String returnAccessTokenExp = accessTokenMap.get("accessTokenExp");

    String returnRefreshToken = refreshTokenMap.get("refreshToken");
    String returnRefreshTokenExp = refreshTokenMap.get("refreshTokenExp");

    LoginDTO loginDTO = new LoginDTO(userEntity.getId(), userEntity.getUserEmail(), userEntity.getUserName(), userEntity.getUserImg(), returnAccessToken, returnAccessTokenExp, returnRefreshToken, returnRefreshTokenExp);

    System.out.println(returnAccessToken);

    return loginDTO;
  }

}
