package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.dto.movie.MainPageDTO;
import PINAMO.FADEIN.handler.ContentDataHandler;
import PINAMO.FADEIN.service.MainPageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.*;


@Service
public class MainPageServiceImpl implements MainPageService {

  MovieUtil movieUtil = new MovieUtil();
  RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

  ContentDataHandler contentDataHandler;

  @Autowired
  public MainPageServiceImpl(ContentDataHandler contentDataHandler) {
    this.contentDataHandler = contentDataHandler;
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
  public List<ContentObject> getPreference(String type) {
    return null;
  }

  @Override
  public List<ContentObject> getRecommend(String type) {
    try {
      List<ContentObject> result = new ArrayList<>();

      List<ContentEntity> contentEntities = contentDataHandler.getContentEntitiesByType(type);

      Random random = new Random();
      Set<Integer> randomIndexSet = new HashSet<>();
      while (randomIndexSet.size() < 10) randomIndexSet.add(random.nextInt(contentEntities.size()));
      ArrayList<Integer> randomIndex = new ArrayList<>(randomIndexSet);

      for (int i=0; i<randomIndex.size(); i++) {
        int tmdbId = contentEntities.get(randomIndex.get(i)).getTmdbId();
        String title = contentEntities.get(randomIndex.get(i)).getTitle();
        ArrayList<String> genre = new ArrayList<>(Arrays.asList(contentEntities.get(randomIndex.get(i)).getGenre().split(",")));
        String poster = contentEntities.get(randomIndex.get(i)).getPoster();
        String overview = contentEntities.get(randomIndex.get(i)).getOverview();

        ContentObject ContentObject = new ContentObject(tmdbId,type,title,genre,poster,overview);

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

        String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
        JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

        int tmdbId = parser.getInt("id");

        String title = parser.getString("title");

        ArrayList<String> genre = movieUtil.GenreTransducerByName(parser.getJSONArray("genres"));
        String return_genre = "";
        for (int j=0; j<genre.size(); j++) {
          return_genre = return_genre + genre.get(j) + ",";
        }

        String poster = movieUtil.posterTransducer(parser.get("poster_path"));
        String overview = parser.getString("overview");

        contentDataHandler.saveContentEntity(tmdbId, type, title, return_genre, poster, overview);
      }
    }
    else {
      for (int i = 0; i < tv.length; i++) {
        String path = "tv" + "/" + tv[i];

        String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
        JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

        int tmdbId = parser.getInt("id");

        String title = parser.getString("name");

        ArrayList<String> genre = movieUtil.GenreTransducerByName(parser.getJSONArray("genres"));
        String return_genre = "";
        for (int j=0; j<genre.size(); j++) {
          return_genre = return_genre + genre.get(j) + ",";
        }

        String poster = movieUtil.posterTransducer(parser.get("poster_path"));
        String overview = parser.getString("overview");

        contentDataHandler.saveContentEntity(tmdbId, type, title, return_genre, poster, overview);
      }
    }
  }

}
