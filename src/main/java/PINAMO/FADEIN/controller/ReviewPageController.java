package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.ReviewPageDTO;
import PINAMO.FADEIN.data.dto.movie.SearchLengthDTO;
import PINAMO.FADEIN.data.dto.movie.WritePageDTO;
import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;
import PINAMO.FADEIN.service.ReviewPageService;
import PINAMO.FADEIN.service.WritePageService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewPageController {

  private ReviewPageService reviewPageService;
  private final Logger LOGGER = LoggerFactory.getLogger(ReviewPageController.class);

  JwtUtil jwtUtil = new JwtUtil();


  public int getYear() {
    Date now = new Date();
    return Integer.parseInt(now.toString().split("-")[0]);
  }

  @Autowired
  public ReviewPageController(ReviewPageService reviewPageService) {
    this.reviewPageService = reviewPageService;
  }

  @GetMapping(value = "/list")
  public ReviewPageDTO getReviewPage(@RequestParam Integer year,
                                     @RequestParam Integer month,
                                     @RequestHeader(value = "authorization") String accessToken) throws CustomException{

    LOGGER.info("GET REVIEW PAGE.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    ReviewPageDTO reviewPageDTO = reviewPageService.getReviewPage((long) userId, year, month);
    if (reviewPageDTO == null) {
      LOGGER.error("ERROR OCCUR IN GETTING REVIEW LIST.");
      throw new CustomException(Constants.ExceptionClass.REVIEW, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return reviewPageDTO;
  }

  @DeleteMapping(value = "/{reviewId}")
  public ResponseEntity writeReview(@PathVariable String reviewId,
                                    @RequestHeader(value = "authorization") String accessToken) throws CustomException {

    LOGGER.info("DELETE REVIEW.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    int returnCode = reviewPageService.deleteReview(reviewId);

    if (returnCode == 0) {
      LOGGER.error("ERROR OCCUR IN WRITING REVIEW.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    } else if (returnCode == 2) {
      LOGGER.error("NOT EXIST REVIEW.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.BAD_REQUEST, "NOT EXIST REVIEW");
    }

    return new ResponseEntity(HttpStatus.OK);
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
