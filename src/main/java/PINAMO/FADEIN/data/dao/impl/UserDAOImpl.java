package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.UserDAO;
import PINAMO.FADEIN.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAOImpl implements UserDAO {

    UserRepository userRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity getUser(Long userId) {
        UserEntity userEntity = userRepository.getReferenceById(userId);
        return userEntity;
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        UserEntity userEntity = userRepository.getByUserEmail(userEmail);
        return userEntity;
    }
}
