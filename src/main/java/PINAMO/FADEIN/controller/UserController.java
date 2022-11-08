package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.user.loginDTO;
import PINAMO.FADEIN.data.dto.user.accessTokenDTO;
import PINAMO.FADEIN.data.dto.user.userDTO;
import PINAMO.FADEIN.service.UserService;
import exception.Constants;
import exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class UserController {

  private UserService userService;

  private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/{userId}")
  public userDTO getUser(@PathVariable Long userId) {
    LOGGER.info("GET USER INFORMATION.");
    return userService.getUser(userId);
  }

  @PostMapping(value = "/google")
  public loginDTO loginGoogleUser(@RequestBody String accessToken) {
    LOGGER.info("LOGIN GOOGLE USER.");
    return userService.loginGoogleUser(accessToken);
  }

  @PostMapping(value = "/naver")
  public loginDTO loginNaverUser(@RequestBody String accessToken) {
    LOGGER.info("LOGIN NAVER USER.");
    return userService.loginNaverUser(accessToken);
  }

  @PostMapping(value = "/refresh")
  public accessTokenDTO reissueAccessToken(@RequestBody String refreshToken) throws UserException {
    accessTokenDTO returnDTO = userService.reissueAccessToken(refreshToken);

    LOGGER.info("REFRESH ACCESS TOKEN.");

    if (returnDTO==null) {
      throw new UserException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED REFRESH TOKEN.");
    }
    else return returnDTO;
  }

  @ExceptionHandler(value = UserException.class)
  public ResponseEntity<Map<String, String>> ExceptionHandler(UserException e) {
    HttpHeaders responseHeaders = new HttpHeaders();

    Map<String, String> map = new HashMap<>();
    map.put("error type", e.getHttpStatusType());
    map.put("code", Integer.toString(e.getHttpStatusCode()));
    map.put("message", e.getMessage());

    return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
  }
}
