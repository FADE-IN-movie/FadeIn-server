package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.object.movieObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;

import java.util.List;

public interface MainPageService {

  List<movieObject> getPopular(String type);
  List<movieObject> getTopRate(String type);
  List<movieObject> getNowPlaying(String type);
  List<movieObject> getPreference(String type);
  List<movieObject> getRecommend(String type);

//  void saveRecommend();

  MainPageDTO getMainPage(List<movieObject> popular, List<movieObject> topRated, List<movieObject> nowPlaying, List<movieObject> preference, List<movieObject> recommend);

}
