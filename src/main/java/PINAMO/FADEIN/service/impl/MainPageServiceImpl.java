package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.handler.RecommendDataHandler;
import PINAMO.FADEIN.service.MainPageService;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MovieUtil;

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
    try {
      List<movieObject> result = movieUtil.getMovies(type, "popular", 1,"");
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<movieObject> getTopRate(String type) {
    try {
      List<movieObject> result = movieUtil.getMovies(type, "top_rated", 1,"");
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<movieObject> getNowPlaying(String type) {
    try {
      List<movieObject> result;
      if (type.equals("movie")) {
        result = movieUtil.getMovies(type, "now_playing", 1, "");
        return result;
      }
      else {
        result = movieUtil.getMovies(type, "on_the_air", 1,"");
        return result;
      }
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<movieObject> getPreference(String type) {
    return null;
  }

  @Override
  public List<movieObject> getRecommend(String type) {
    try {
      List<movieObject> result = movieUtil.getRecommendContent(type);
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public MainPageDTO getMainPage(List<movieObject> popular, List<movieObject> topRated, List<movieObject> nowPlaying, List<movieObject> preference, List<movieObject> recommend) {

    MainPageDTO mainPageDTO = new MainPageDTO(popular, topRated, nowPlaying, preference, recommend);
    return mainPageDTO;
  }
}
