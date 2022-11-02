package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.user.accessTokenDTO;
import PINAMO.FADEIN.data.dto.user.loginDTO;
import PINAMO.FADEIN.data.dto.user.userDTO;
import exception.UserException;

public interface UserService {

  loginDTO saveUser(Long id, String userEmail, String userName, String userPicture);

  userDTO getUser(Long userId);

  loginDTO loginGoogleUser(String accessToken);

  loginDTO loginNaverUser(String accessToken);

  accessTokenDTO reissueAccessToken(String refreshToken) throws UserException;
}
