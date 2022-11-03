package utils;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RestTemplateUtil {

  public JSONObject GetRestTemplate(String url) {

    URI uri = UriComponentsBuilder
        .fromUriString(url)
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

    JSONObject parser = new JSONObject(requestEntity.getBody());

    return parser;
  }

}
