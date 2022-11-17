package PINAMO.FADEIN.data.dao.impl;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dao.UserDAO;
import PINAMO.FADEIN.data.repository.UserRepository;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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
        return userRepository.getReferenceById(userId);
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        return userRepository.getByUserEmail(userEmail);
    }

    @Override
    public boolean isUser(Long userId) {
        return userRepository.existsById(userId);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(CustomException e) {
        HttpHeaders responseHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("error type", e.getHttpStatusType());
        map.put("code", Integer.toString(e.getHttpStatusCode()));
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
    }
}
