package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.dto.movie.Movie;
import PINAMO.FADEIN.data.dto.movie.mainPageDTO;
import PINAMO.FADEIN.service.MovieService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import utils.MovieUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

  @Override
  public List<Movie> getPopular(String type) {

    String requestURL = String.format("https://api.themoviedb.org/3/%s/popular?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", type);

    URI uri = UriComponentsBuilder
        .fromUriString(requestURL)
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

    JSONObject parser = new JSONObject(requestEntity.getBody());

    JSONArray arrayList = parser.getJSONArray("results");

    List<Movie> return_movies = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      JSONObject detail = (JSONObject) arrayList.get(i);

      MovieUtil movieUtil = new MovieUtil();

      int movieId = detail.getInt("id");

      String title;

      if (type.equals("movie")) title = detail.getString("title");
      else title = detail.getString("name");

      String overview = detail.getString("overview");

//      if (overview.isEmpty()) {
//        String path = movieId+"/"+type;
//
//        String getOverViewURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=en-US", path);
//
//        URI getOverVieUri = UriComponentsBuilder
//            .fromUriString(getOverViewURL)
//            .encode()
//            .build()
//            .toUri();
//
//        ResponseEntity<String> getOverViewEntity = restTemplate.exchange(getOverVieUri, HttpMethod.GET, request, String.class);
//
//        JSONObject overViewParser = new JSONObject(getOverViewEntity.getBody());
//
//        overview = overViewParser.getString("overview");
//      }
      JSONArray genre_ids = detail.getJSONArray("genre_ids");

      ArrayList<Integer> genres = new ArrayList<>();

      for (int j = 0; j<genre_ids.length(); j++) {
        genres.add((int) genre_ids.get(j));
      }

      ArrayList<String> return_genres = movieUtil.GenreTransducer(genres);

      Movie popular = new Movie(movieId, title, return_genres, overview);

      return_movies.add(popular);
    }

    return return_movies;
    }

  @Override
  public List<Movie> getTopRate(String type) {

    String requestURL = String.format("https://api.themoviedb.org/3/%s/top_rated?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", type);

    URI uri = UriComponentsBuilder
        .fromUriString(requestURL)
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

    JSONObject parser = new JSONObject(requestEntity.getBody());

    JSONArray arrayList = parser.getJSONArray("results");

    List<Movie> return_movies = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      JSONObject detail = (JSONObject) arrayList.get(i);

      MovieUtil movieUtil = new MovieUtil();

      int movieId = detail.getInt("id");

      String title;

      if (type.equals("movie")) title = detail.getString("title");
      else title = detail.getString("name");

      String overview = detail.getString("overview");
      JSONArray genre_ids = detail.getJSONArray("genre_ids");

      ArrayList<Integer> genres = new ArrayList<>();

      for (int j = 0; j<genre_ids.length(); j++) {
        genres.add((int) genre_ids.get(j));
      }

      ArrayList<String> return_genres = movieUtil.GenreTransducer(genres);

      Movie popular = new Movie(movieId, title, return_genres, overview);

      return_movies.add(popular);
    }

    return return_movies;
  }

  @Override
  public List<Movie> getNowPlaying(String type) {

    String query;

    if (type.equals("movie")) query = type + "/now_playing";
    else query = type + "/on_the_air";

    String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko&page=1", query);

    URI uri = UriComponentsBuilder
        .fromUriString(requestURL)
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

    JSONObject parser = new JSONObject(requestEntity.getBody());

    JSONArray arrayList = parser.getJSONArray("results");

    List<Movie> return_movies = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      JSONObject detail = (JSONObject) arrayList.get(i);

      MovieUtil movieUtil = new MovieUtil();

      int movieId = detail.getInt("id");

      String title;

      if (type.equals("movie")) title = detail.getString("title");
      else title = detail.getString("name");

      String overview = detail.getString("overview");
      JSONArray genre_ids = detail.getJSONArray("genre_ids");

      ArrayList<Integer> genres = new ArrayList<>();

      for (int j = 0; j<genre_ids.length(); j++) {
        genres.add((int) genre_ids.get(j));
      }

      ArrayList<String> return_genres = movieUtil.GenreTransducer(genres);

      Movie popular = new Movie(movieId, title, return_genres, overview);

      return_movies.add(popular);
    }

    return return_movies;
  }

  @Override
  public List<Movie> getPreference(String type) {
    return null;
  }

  @Override
  public List<Movie> getRecommend(String type) {
    return null;
  }

  @Override
  public mainPageDTO getMainPage(List<Movie> popular, List<Movie> topRated, List<Movie> dongjin, List<Movie> preference, List<Movie> recommend) {
    mainPageDTO mainPageDTO = new mainPageDTO(popular, topRated, dongjin, preference, recommend);

    return mainPageDTO;
  }

}
