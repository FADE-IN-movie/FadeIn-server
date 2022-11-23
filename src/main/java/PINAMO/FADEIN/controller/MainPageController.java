package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.service.MainPageService;
import exception.Constants;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contents")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class MainPageController {

  private MainPageService mainPageService;
  private final Logger LOGGER = LoggerFactory.getLogger(MainPageController.class);

  JwtUtil jwtUtil = new JwtUtil();

  @Autowired
  public MainPageController(MainPageService mainPageService) {
    this.mainPageService = mainPageService;
  }

  @PostMapping(value = "/save")
  public void saveRecommend(@RequestBody String type) {
    mainPageService.saveRecommend(type);
  }

  @GetMapping(value = "")
  public MainPageDTO getMainPage(@RequestParam String type, @RequestHeader(value = "authorization", required=false) String accessToken) throws CustomException{

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);

    System.out.println(userId);

    LOGGER.info("GET "+ type.toUpperCase() + " CONTENTS.");

    List<ContentObject> popular = mainPageService.getPopular(type);
    if (popular == null) {
      LOGGER.error("ERROR OCCUR IN GETTING POPULAR CONTENTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    List<ContentObject> topRate = mainPageService.getTopRate(type);
    if (topRate == null) {
      LOGGER.error("ERROR OCCUR IN GETTING TOP RATED CONTENTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    List<ContentObject> nowPlaying = mainPageService.getNowPlaying(type);
    if (nowPlaying == null) {
      LOGGER.error("ERROR OCCUR IN GETTING NOW PLAYING CONTENTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    List<ContentObject> preference = mainPageService.getPopular(type);
    if (preference == null) {
      LOGGER.error("ERROR OCCUR IN GETTING PREFERENCE CONTENTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    List<ContentObject> recommend = mainPageService.getRecommend(type);
//    if (recommend == null) {
//      LOGGER.error("ERROR OCCUR IN GETTING RECOMMEND CONTENTS.");
//      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
//    }

    return mainPageService.getMainPage(popular, topRate, nowPlaying, preference, recommend);
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
