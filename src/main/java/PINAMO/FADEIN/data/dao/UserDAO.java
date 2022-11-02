package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.UserEntity;

public interface UserDAO {

    UserEntity saveUser(UserEntity userEntity);

    UserEntity getUser(Long userId);

    UserEntity getUserByEmail(String Email);
}
