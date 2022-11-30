package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.user.AccessTokenDTO;
import PINAMO.FADEIN.data.dto.user.LoginDTO;
import exception.CustomException;

public interface UserService {

  LoginDTO saveUser(Long id, String userEmail, String userName, String userPicture);

  UserEntity getUser(Long userId) throws CustomException;

  LoginDTO loginGoogleUser(String accessToken);

  LoginDTO loginNaverUser(String accessToken);

  AccessTokenDTO reissueAccessToken(String refreshToken) throws CustomException;
}
