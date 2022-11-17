package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;

import java.util.List;

public interface MainPageService {

  List<ContentObject> getPopular(String type);
  List<ContentObject> getTopRate(String type);
  List<ContentObject> getNowPlaying(String type);
  List<ContentObject> getPreference(String type);
  List<ContentObject> getRecommend(String type);

  MainPageDTO getMainPage(List<ContentObject> popular, List<ContentObject> topRated, List<ContentObject> nowPlaying, List<ContentObject> preference, List<ContentObject> recommend);

  void saveRecommend(String type);
}
