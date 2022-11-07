package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.data.dto.movie.mainPageDTO;
import PINAMO.FADEIN.handler.RecommendDataHandler;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.MainPageService;
import io.swagger.models.auth.In;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

  RecommendDataHandler recommendDataHandler;

  @Autowired
  public MainPageServiceImpl(RecommendDataHandler recommendDataHandler) {
    this.recommendDataHandler = recommendDataHandler;
  }

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
    else return movieUtil.getMovies(type, "on_the_air");

  }

  @Override
  public List<movieObject> getPreference(String type) {
    return null;
  }

  @Override
  public List<movieObject> getRecommend(String type) {
    return null;
  }

//  @Override
//  public void saveRecommend(){
//
//    int[] movie = {20342, 372058, 101299, 70160, 207703, 343668,5876, 122906, 198663, 294254, 807, 2832, 550 ,244786 ,155 ,603 ,157336 ,6977 ,165213 ,278, 49797, 423, 398978, 530385};
//    int[] tv = {82237 ,67915 ,64840 ,117378 ,84327 ,70123 ,78648 ,80585 ,42009 ,86831 ,94796 ,97970 ,155226 ,20588 ,31505 ,90447 ,48462 ,112833 ,37722 ,1396 ,87739, 61459};
//
//    for (int i=0; i<movie.length; i++){
//      RecommendEntity recommendEntity = movieUtil.saveRecommend("movie", movie[i]);
//
//      recommendDataHandler.saveRecommendEntity(recommendEntity.getId(), recommendEntity.getRank(), recommendEntity.getType(), recommendEntity.getTitle(), recommendEntity.getGenre(), recommendEntity.getPoster(), recommendEntity.getOverview());
//    }
//    for (int i=0; i<tv.length; i++){
//      RecommendEntity recommendEntity = movieUtil.saveRecommend("tv", tv[i]);
//
//      recommendDataHandler.saveRecommendEntity(recommendEntity.getId(), recommendEntity.getRank(), recommendEntity.getType(), recommendEntity.getTitle(), recommendEntity.getGenre(), recommendEntity.getPoster(), recommendEntity.getOverview());
//    }
//  }

  @Override
  public mainPageDTO getMainPage(List<movieObject> popular, List<movieObject> topRated, List<movieObject> nowPlaying, List<movieObject> preference, List<movieObject> recommend) {
    mainPageDTO mainPageDTO = new mainPageDTO(popular, topRated, nowPlaying, preference, recommend);

    return mainPageDTO;
  }

}
