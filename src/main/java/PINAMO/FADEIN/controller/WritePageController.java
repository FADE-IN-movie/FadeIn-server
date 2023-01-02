package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.data.dto.movie.WritePageDTO;
import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;
import PINAMO.FADEIN.service.WritePageService;
import exception.Constants;
import exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import PINAMO.FADEIN.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WritePageController {

  JwtUtil jwtUtil;
  private WritePageService writePageService;
  private final Logger LOGGER = LoggerFactory.getLogger(WritePageController.class);

  @Autowired
  public WritePageController(JwtUtil jwtUtil, WritePageService writePageService) {
    this.jwtUtil = jwtUtil;
    this.writePageService = writePageService;
  }

  @GetMapping(value = "")
  public WritePageDTO getWritePage(@RequestParam(required = false) String reviewId,
                                   @RequestParam int tmdbId,
                                   @RequestParam(defaultValue = "movie", required = false) String type,
                                   @RequestHeader(value = "authorization") String accessToken) throws CustomException{

    LOGGER.info("GET WRITE PAGE.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    String path = type + "/" + tmdbId;

    WriteContentObject writeContentObject = writePageService.getWriteContent(path);
    if (writeContentObject == null) {
      LOGGER.error("ERROR OCCUR IN GETTING CONTENT DATA.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    if (reviewId==null) reviewId = "";
    WriteReviewObject writeReviewObject = writePageService.getWriteReview(reviewId);
    if (writeReviewObject == null) {
      LOGGER.error("ERROR OCCUR IN GETTING REVIEW DATA.");
      throw new CustomException(Constants.ExceptionClass.REVIEW, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    WritePageDTO writePageDTO = new WritePageDTO(writeContentObject, writeReviewObject);

    return writePageDTO;
  }

  @PostMapping(value = "/{reviewId}")
  public ResponseEntity writeReview(@PathVariable String reviewId,
                                    @RequestBody WriteReviewDTO writeReviewDTO,
                                    @RequestHeader(value = "authorization") String accessToken) throws CustomException {

    LOGGER.info("WRITE REVIEW.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    if (!writePageService.writeReview(reviewId, (long) userId, writeReviewDTO)) {
      LOGGER.error("ERROR OCCUR IN WRITING REVIEW.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return new ResponseEntity(HttpStatus.CREATED);
  }

  @PatchMapping(value = "/{reviewId}")
  public ResponseEntity modifyReview(@PathVariable String reviewId,
                                    @RequestBody WriteReviewDTO writeReviewDTO,
                                    @RequestHeader(value = "authorization") String accessToken) throws CustomException {

    LOGGER.info("MODIFY REVIEW.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    if (!writePageService.writeReview(reviewId, (long) userId, writeReviewDTO)) {
      LOGGER.error("ERROR OCCUR IN MODIFYING REVIEW.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return new ResponseEntity(HttpStatus.ACCEPTED);
  }

  @GetMapping(value = "/search")
  public SearchPageDTO getWriteSearch(@RequestParam String keyword,
                                      @RequestParam(defaultValue = "1", required = false) int page) throws CustomException {

    LOGGER.info("GET SEARCH RESULTS IN WRITE PAGE.");

    SearchPageDTO writeSearchDTO = writePageService.getWriteSearch(keyword, page);
    if (writeSearchDTO == null) {
      LOGGER.error("ERROR OCCUR IN GETTING SEARCH RESULTS.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return writeSearchDTO;
  }

  @GetMapping(value = "/search/length")
  public int getWriteSearchLength(@RequestParam String keyword) throws CustomException {

    LOGGER.info("GET SEARCH RESULTS LEGNTH IN WRITE PAGE.");

    int writeSearchLength = writePageService.getWriteSearchLength(keyword);

    return writeSearchLength;
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
