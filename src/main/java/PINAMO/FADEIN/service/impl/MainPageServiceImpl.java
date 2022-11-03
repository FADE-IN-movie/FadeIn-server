package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.data.dto.movie.mainPageDTO;
import PINAMO.FADEIN.service.MainPageService;
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
public class MainPageServiceImpl implements MainPageService {

  MovieUtil movieUtil = new MovieUtil();

  @Override
  public List<movieObject> getPopular(String type) {
    return movieUtil.getMovies(type, "popular");
  }

  @Override
  public List<movieObject> getTopRate(String type) {
    return movieUtil.getMovies(type, "top_rated");
  }

  @Override
  public List<movieObject> getNowPlaying(String type) {

    if (type.equals("movie")) return movieUtil.getMovies(type, "now_playing");
    else return movieUtil.getMovies(type, "/on_the_air");

  }

  @Override
  public List<movieObject> getPreference(String type) {
    return null;
  }

  @Override
  public List<movieObject> getRecommend(String type) {
    return null;
  }

  @Override
  public mainPageDTO getMainPage(List<movieObject> popular, List<movieObject> topRated, List<movieObject> nowPlaying, List<movieObject> preference, List<movieObject> recommend) {
    mainPageDTO mainPageDTO = new mainPageDTO(popular, topRated, nowPlaying, preference, recommend);

    return mainPageDTO;
  }

}
