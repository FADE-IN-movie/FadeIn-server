package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.UserDAO;
import PINAMO.FADEIN.handler.UserDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDataHandlerImpl implements UserDataHandler {

  UserDAO userDAO;

  @Autowired
  public UserDataHandlerImpl(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public UserEntity saveUserEntity(String userEmail, String userName, String userPicture) {

    UserEntity userEntity = new UserEntity(userEmail, userName, userPicture);

    return userDAO.saveUser(userEntity);
  }

  @Override
  public UserEntity getUserEntity(Long userId) {
    return userDAO.getUser(userId);
  }

  @Override
  public UserEntity getUserEntityByEmail(String userEmail) {
    return userDAO.getUserByEmail(userEmail);
  }

}