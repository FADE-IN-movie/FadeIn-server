package PINAMO.FADEIN.controller;

import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.dto.movie.WritePageDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WritePageController {

  private WritePageService writePageService;
  private final Logger LOGGER = LoggerFactory.getLogger(WritePageController.class);

  JwtUtil jwtUtil = new JwtUtil();

  @Autowired
  public WritePageController(WritePageService writePageService) {
    this.writePageService = writePageService;
  }

  @GetMapping(value = "/{contentId}")
  public WriteContentObject getWritePage(@PathVariable int contentId, @RequestParam String type, @RequestHeader(value = "authorization", required=false) String accessToken) throws CustomException{

    LOGGER.info("GET WRITE PAGE.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }

    String path = type + "/" + contentId;

    WriteContentObject writeContentObject = writePageService.getWritePage(path);

    if (writeContentObject == null) {
      LOGGER.error("ERROR OCCUR IN GETTING WRITE PAGE.");
      throw new CustomException(Constants.ExceptionClass.CONTENT, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }

    return writeContentObject;
  }

  @PostMapping(value = "")
  public ResponseEntity writeReview(@RequestBody WritePageDTO writePageDTO, @RequestHeader(value = "authorization", required=false) String accessToken) throws CustomException {

    LOGGER.info("WRITE REVIEW.");

    int userId = 0;
    if (accessToken!=null && jwtUtil.checkClaim(accessToken)) userId = jwtUtil.getUserIdInJwtToken(accessToken);
    else {
      LOGGER.error("UNAUTHORIZED ACCESS TOKEN.");
      throw new CustomException(Constants.ExceptionClass.USER, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ACCESS TOKEN");
    }
    writePageService.writeReview((long) userId, writePageDTO);

    return new ResponseEntity(HttpStatus.CREATED);
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
