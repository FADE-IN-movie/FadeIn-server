package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.movie.RankingPageDTO;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.handler.*;
import PINAMO.FADEIN.service.MainPageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.*;


@Service
public class MainPageServiceImpl implements MainPageService {

  MovieUtil movieUtil = new MovieUtil();
  RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;
  UserDataHandler userDataHandler;
  LikeDataHandler likeDataHandler;

  @Autowired
  public MainPageServiceImpl(ContentDataHandler contentDataHandler, ContentGenreDataHandler contentGenreDataHandler, UserDataHandler userDataHandler, LikeDataHandler likeDataHandler) {
    this.likeDataHandler = likeDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
    this.userDataHandler = userDataHandler;
  }

  @Override
  public List<ContentObject> getPopular(String type) {
    try {
      List<ContentObject> result = movieUtil.getMovies(type, "popular", 1,"");
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<ContentObject> getTopRate(String type) {
    try {
      List<ContentObject> result = movieUtil.getMovies(type, "top_rated", 1,"");
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<ContentObject> getNowPlaying(String type) {
    try {
      List<ContentObject> result;
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
  public List<ContentObject> getPreference(Long userId, String type) {
    try {

      String genre = contentGenreDataHandler.getReferenceGenreByUserId(userId, type);

      String genreId;
      if (genre==null) genreId = movieUtil.getRandomGenre(type);
      else genreId = movieUtil.GenreReverseTransducer(genre);

      Random random = new Random();
      int randomNum = random.nextInt(4);

      String sortBy = new String();
      switch (randomNum){
        case 0:
          sortBy = "&sort_by=vote_average.desc";
          break;
        case 1:
          sortBy = "&sort_by=vote_count.desc";
          break;
        case 2:
          sortBy = "&sort_by=popularity.desc";
          break;
        case 3:
          sortBy = "&sort_by=revenue.desc";
          break;
      }

      return movieUtil.getMovies(type, "discover", 1, "&vote_count.gte=300" + sortBy + "&with_genres=" + genreId);
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<ContentObject> getRecommend(String type) {
    try {
      List<ContentObject> result = new ArrayList<>();

      boolean isContent = contentDataHandler.isContentEntityByType(type);

      if (!isContent) saveRecommend(type);

      List<ContentEntity> contentEntities = contentDataHandler.getContentEntitiesByType(type);

      Random random = new Random();
      Set<Integer> randomIndexSet = new HashSet<>();
      while (randomIndexSet.size() < 10) randomIndexSet.add(random.nextInt(contentEntities.size()));
      ArrayList<Integer> randomIndex = new ArrayList<>(randomIndexSet);

      for (int i=0; i<randomIndex.size(); i++) {
        ContentEntity randomContent = contentEntities.get(randomIndex.get(i));
        int tmdbId = randomContent.getTmdbId();
        String title = randomContent.getTitle();
        ArrayList<ContentGenreEntity> contentGenreEntities = contentGenreDataHandler.getContentGenreEntitiesByContentId(randomContent.getId());
        List<String> returnGenre = new ArrayList<>();
        for (int j=0; j<contentGenreEntities.size(); j++) returnGenre.add(contentGenreEntities.get(j).getGenre());
        String poster = contentEntities.get(randomIndex.get(i)).getPoster();
        String overview = contentEntities.get(randomIndex.get(i)).getOverview();

        ContentObject ContentObject = new ContentObject(tmdbId,type,title,returnGenre,poster,overview);

        result.add(ContentObject);
      }
      return result;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public MainPageDTO getMainPage(List<ContentObject> popular, List<ContentObject> topRated, List<ContentObject> nowPlaying, List<ContentObject> preference, List<ContentObject> recommend) {

    MainPageDTO mainPageDTO = new MainPageDTO(popular, topRated, nowPlaying, preference, recommend);
    return mainPageDTO;
  }

  @Override
  public void saveRecommend(String type) {
    int[] movie = {20342, 372058, 101299, 70160, 207703, 343668,5876, 122906, 198663, 294254, 807, 2832, 550 ,244786 ,155 ,603 ,157336 ,6977 ,165213 ,278, 49797, 423, 398978, 530385};
    int[] tv = {82237 ,67915 ,64840 ,117378 ,84327 ,70123 ,78648 ,80585 ,42009 ,86831 ,94796 ,97970 ,155226 ,20588 ,31505 ,90447 ,48462 ,112833 ,37722 ,1396 ,87739, 61459};

    if (type.equals("movie")) {
      for (int i = 0; i < movie.length; i++) {

        String path = "movie" + "/" + movie[i];

        Map<ContentEntity, ArrayList<String>> map = movieUtil.getContentByEntity(type, path, "yes");

        ContentEntity returnContentEntity = map.keySet().iterator().next();
        ArrayList<String> genre = map.get(returnContentEntity);

        ContentEntity contentEntity = contentDataHandler.saveContentEntity(returnContentEntity);

        for (int j=0; j<genre.size(); j++) contentGenreDataHandler.saveContentGenreEntity(new ContentGenreEntity(contentEntity, genre.get(j)));
      }
    }
    else {
      for (int i = 0; i < tv.length; i++) {
        String path = "tv" + "/" + tv[i];

        Map<ContentEntity, ArrayList<String>> map = movieUtil.getContentByEntity(type, path, "yes");

        ContentEntity returnContentEntity = map.keySet().iterator().next();
        ArrayList<String> genre = map.get(returnContentEntity);

        ContentEntity contentEntity = contentDataHandler.saveContentEntity(returnContentEntity);

        for (int j=0; j<genre.size(); j++) contentGenreDataHandler.saveContentGenreEntity(new ContentGenreEntity(contentEntity, genre.get(j)));
      }
    }
  }

}
