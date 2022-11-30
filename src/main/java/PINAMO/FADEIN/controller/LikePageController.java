package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.LikePageDTO;
import PINAMO.FADEIN.data.dto.movie.RankingPageDTO;
import PINAMO.FADEIN.service.LikePageService;
import PINAMO.FADEIN.service.RankingPageService;
import exception.Constants;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

@RestController
@RequestMapping("/like")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LikePageController {

  private LikePageService likePageService;
  private final Logger LOGGER = LoggerFactory.getLogger(LikePageController.class);

  JwtUtil jwtUtil = new JwtUtil();

  @Autowired
  public LikePageController(LikePageService likePageService) {
    this.likePageService = likePageService;
  }

  @GetMapping(value = "")
  public LikePageDTO getLikePage(@RequestHeader(value = "authorization") String accessToken) throws CustomException{

    LOGGER.info("GET LIKE LIST.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    LikePageDTO likePageDTO = likePageService.getLikePage((long) userId);
    if (likePageDTO == null) {
      LOGGER.error("ERROR OCCUR IN GETTING LIKE LIST.");
      throw new CustomException(Constants.ExceptionClass.LIKE, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return likePageDTO;
  }
}
