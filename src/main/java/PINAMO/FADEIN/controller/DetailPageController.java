package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.LikeDTO;
import PINAMO.FADEIN.data.dto.movie.DetailPageDTO;
import PINAMO.FADEIN.data.dto.movie.LikePageDTO;
import PINAMO.FADEIN.data.object.CastObject;
import PINAMO.FADEIN.data.object.DetailObject;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.service.DetailPageService;
import exception.Constants;
import exception.CustomException;
import io.jsonwebtoken.Claims;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DetailPageController {

  private DetailPageService detailPageService;
  private final Logger LOGGER = LoggerFactory.getLogger(DetailPageController.class);

  JwtUtil jwtUtil = new JwtUtil();

  @Autowired
  public DetailPageController(DetailPageService detailPageService) {
    this.detailPageService = detailPageService;
  }

  @GetMapping(value = "/{tmdbId}")
  public DetailPageDTO getDetailPage(@PathVariable int tmdbId,
                                     @RequestParam(defaultValue = "movie", required = false) String type,
                                     @RequestHeader(value = "authorization", required=false) String accessToken) throws CustomException{

    System.out.println(accessToken);

    LOGGER.info("GET CONTENT DETAIL.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);

    String path = type + "/" + tmdbId;

    DetailObject detail = detailPageService.getDetail(path);
    if (detail == null) {
      LOGGER.error("ERROR OCCUR IN GETTING DETAILS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    List<CastObject> cast = detailPageService.getCast(path);
    if (cast == null) {
      LOGGER.error("ERROR OCCUR IN GETTING CASTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    List<ContentObject> similarContents = detailPageService.getSimilarContents(path);
    if (similarContents == null) {
      LOGGER.error("ERROR OCCUR IN GETTING SIMILAR CONTENTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return detailPageService.getDetailPage((long) userId, detail, cast, similarContents);
  }

  @PostMapping(value = "/like")
  public LikeDTO changeLikeStatus(@RequestBody LikeDTO changeLikeDTO, @RequestHeader(value = "authorization", required=false) String accessToken) throws CustomException{

    LOGGER.info("CHANGE LIKE STATUS.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);

    boolean currentStatus = changeLikeDTO.isCurrentLike();

    LikeDTO likeDTO = detailPageService.changeLikeState(changeLikeDTO, (long) userId);
    if (likeDTO == null) {
      LOGGER.error("ERROR OCCUR IN CHANGING LIKE STATUS.");
      throw new CustomException(Constants.ExceptionClass.LIKE, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return likeDTO;
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
