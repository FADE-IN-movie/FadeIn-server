package PINAMO.FADEIN.service;

import PINAMO.FADEIN.data.dto.movie.Movie;
import PINAMO.FADEIN.data.dto.movie.mainPageDTO;

import java.util.List;

public interface MovieService {
  List<Movie> getPopular(String type);
  List<Movie> getTopRate(String type);
  List<Movie> getNowPlaying(String type);
  List<Movie> getPreference(String type);
  List<Movie> getRecommend(String type);

  mainPageDTO getMainPage(List<Movie> popular,List<Movie> topRated,List<Movie> dongjin,List<Movie> preference,List<Movie> recommend);
}
