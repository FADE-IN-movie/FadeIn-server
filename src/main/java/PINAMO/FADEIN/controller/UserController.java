package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.user.loginDTO;
import PINAMO.FADEIN.data.dto.user.accessTokenDTO;
import PINAMO.FADEIN.data.dto.user.userDTO;
import PINAMO.FADEIN.service.UserService;
import exception.Constants;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class UserController {

  private UserService userService;

  private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/{userId}")
  public UserEntity getUser(@PathVariable Long userId) throws CustomException {

    LOGGER.info("GET USER INFORMATION.");
    UserEntity result = userService.getUser(userId);

    if (result == null) {
      LOGGER.error("NOT EXIST USER.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.NOT_FOUND, "NOT EXIST USER.");
    }
    else return result;
  }

  @PostMapping(value = "/google")
  public loginDTO loginGoogleUser(@RequestBody String accessToken) throws CustomException {

    LOGGER.info("LOGIN GOOGLE USER.");
    loginDTO result = userService.loginGoogleUser(accessToken);

    if (result == null) {
      LOGGER.error("INTERNAL SERVER ERROR.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR.");
    }
    else return result;
  }

  @PostMapping(value = "/naver")
  public loginDTO loginNaverUser(@RequestBody String accessToken) throws CustomException {

    LOGGER.info("LOGIN NAVER USER.");
    loginDTO result = userService.loginNaverUser(accessToken);

    if (result == null) {
      LOGGER.error("INTERNAL SERVER ERROR.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }
    else return result;
  }

  @PostMapping(value = "/refresh")
  public accessTokenDTO reissueAccessToken(@RequestBody String refreshToken) throws CustomException {

    LOGGER.info("REFRESH ACCESS TOKEN.");
    accessTokenDTO result = userService.reissueAccessToken(refreshToken);

    if (result == null) {
      LOGGER.error("UNAUTHORIZED REFRESH TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED REFRESH TOKEN");
    }
    else return result;
  }

  @ExceptionHandler(value = CustomException.class)
  public ResponseEntity<Map<String, String>> ExceptionHandler(CustomException e) {
    HttpHeaders responseHeaders = new HttpHeaders();

    Map<String, String> map = new HashMap<>();
    map.put("type", e.getHttpStatusType());
    map.put("code", Integer.toString(e.getHttpStatusCode()));
    map.put("message", e.getMessage());

    return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
  }
}
