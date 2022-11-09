package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.handler.RecommendDataHandler;
import PINAMO.FADEIN.service.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MovieUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    return movieUtil.getMovies(type, "popular", "");
  }

  @Override
  public List<movieObject> getTopRate(String type) {
    return movieUtil.getMovies(type, "top_rated", "");
  }

  @Override
  public List<movieObject> getNowPlaying(String type) {

    if (type.equals("movie")) return movieUtil.getMovies(type, "now_playing", "");
    else return movieUtil.getMovies(type, "on_the_air", "");

  }

  @Override
  public List<movieObject> getPreference(String type) {
    return null;
  }

  @Override
  public List<movieObject> getRecommend(String type) {
    return movieUtil.getRecommendContent(type);
  }

  @Override
  public MainPageDTO getMainPage(List<movieObject> popular, List<movieObject> topRated, List<movieObject> nowPlaying, List<movieObject> preference, List<movieObject> recommend) {
    MainPageDTO mainPageDTO = new MainPageDTO(popular, topRated, nowPlaying, preference, recommend);

    return mainPageDTO;
  }

}
