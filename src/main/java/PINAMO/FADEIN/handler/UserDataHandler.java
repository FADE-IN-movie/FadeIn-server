package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.UserEntity;

public interface UserDataHandler {

  UserEntity saveUserEntity(String userEmail, String userName, String userPicture);

  UserEntity getUserEntity(Long userId);
  UserEntity getUserEntityByEmail(String userEmail);

  boolean isUserEntity(Long userId);

}
