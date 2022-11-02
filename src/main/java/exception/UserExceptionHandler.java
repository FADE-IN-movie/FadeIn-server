package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler{

  private final Logger LOGGER = LoggerFactory.getLogger(UserExceptionHandler.class);

  @ExceptionHandler(value = UserException.class)
  public ResponseEntity<Map<String, String>> ExceptionHandler(UserException e) {
    HttpHeaders responseHeaders = new HttpHeaders();

    Map<String, String> map = new HashMap<>();
    map.put("type", e.getHttpStatusType());
    map.put("code", Integer.toString(e.getHttpStatusCode()));
    map.put("message", e.getMessage());
    System.out.println(map);
    return new ResponseEntity<>(map, responseHeaders, e.getHttpStatus());
  }

}
