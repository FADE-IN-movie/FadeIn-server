package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.dto.movie.WriteSearchDTO;
import PINAMO.FADEIN.data.object.ContentObject;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;
import PINAMO.FADEIN.handler.*;
import PINAMO.FADEIN.service.WritePageService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WritePageServiceImpl implements WritePageService {

  RestTemplateUtil restTemplateUtil = new RestTemplateUtil();
  MovieUtil movieUtil = new MovieUtil();

  UserDataHandler userDataHandler;
  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;
  ReviewDataHandler reviewDataHandler;

  @Autowired
  public WritePageServiceImpl(UserDataHandler userDataHandler, ReviewDataHandler reviewDataHandler, ContentDataHandler contentDataHandler, ContentGenreDataHandler contentGenreDataHandler) {
    this.userDataHandler = userDataHandler;
    this.reviewDataHandler = reviewDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
  }

  @Override
  public WriteContentObject getWriteContent(String path) {
    try {
      String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
      JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

      int tmdbId = parser.getInt("id");
      String title;
      String originalTitle;
      String poster = movieUtil.posterTransducer(parser.get("poster_path"), "poster");
      String backdrop = movieUtil.posterTransducer(parser.get("backdrop_path"), "backdrop");
      String type = path.split("/")[0];
      if (type.equals("movie")) {
        title = parser.getString("title");
        originalTitle = parser.getString("original_title");
      }
      else {
        title = parser.getString("name");
        originalTitle = parser.getString("original_name");
      }
      WriteContentObject writeContentObject = new WriteContentObject(tmdbId, title, originalTitle, poster,backdrop);

      return writeContentObject;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public WriteReviewObject getWriteReview(String reviewId) {
    try {
      WriteReviewObject writeReviewObject;
      ReviewEntity reviewEntity;
      System.out.println(reviewId);
      if (reviewDataHandler.isReviewEntity(reviewId)) {
        reviewEntity = reviewDataHandler.getReviewEntity(reviewId);
        String watchedTime = new String();
        if (reviewEntity.getWatchedTime() == null) watchedTime = "";
        else watchedTime = reviewEntity.getWatchedTime();
        writeReviewObject = new WriteReviewObject(reviewId, reviewEntity.getWatchedDate(), watchedTime, reviewEntity.getWatchedIn(), reviewEntity.getWatchedWith(), reviewEntity.getRating(), reviewEntity.getMemo(), reviewEntity.getComment());
      }
      else {
        writeReviewObject = new WriteReviewObject("", "", "", "", "", (float) 0.0, "", "");
      }

      return writeReviewObject;
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public WriteSearchDTO getWriteSearch(String keyword, int page) {

    String requestURL = String.format("https://api.themoviedb.org/3/search/multi?api_key=929a001736172a3578c0d6bf3b3cbbc5&query=%s&language=ko&page=%d", keyword, page);
    JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

    JSONArray results = parser.getJSONArray("results");

    int total = parser.getInt("total_results");

    List<ContentObject> return_movies = new ArrayList<>();

    if (total > 0) {
      for (int i = 0; i < results.length(); i++) {
        JSONObject detail = (JSONObject) results.get(i);

        int tmdbId = detail.getInt("id");
        String type = detail.getString("media_type");
        if (type.equals("person")) continue;

        String title;
        if (type.equals("movie")) title = detail.getString("title");
        else title = detail.getString("name");

        String overview = detail.getString("overview");
        if (overview.equals("")) overview = movieUtil.overviewTransducer(type, tmdbId);

        ArrayList<String> return_genres = movieUtil.GenreTransducer(detail.getJSONArray("genre_ids"));

        String poster = movieUtil.posterTransducer(detail.get("poster_path"), "poster");

        ContentObject movie = new ContentObject(tmdbId, type, title, return_genres, poster, overview);

        return_movies.add(movie);
      }
    }

    WriteSearchDTO writeSearchDTO = new WriteSearchDTO(total, return_movies);

    return writeSearchDTO;
  }

  @Override
  public boolean writeReview(String reviewId, Long userId, WriteReviewDTO writeReviewDTO) {
//      try {
        String type = writeReviewDTO.getType();

        Boolean isContent = contentDataHandler.isContentEntityByTmdbIdAndType(writeReviewDTO.getTmdbId(), type);
        ContentEntity contentEntity;
        if (isContent) {
          contentEntity = contentDataHandler.getContentEntityByTmdbIdAndType(writeReviewDTO.getTmdbId(), type);
        }
        else {
          String path = type + "/" + writeReviewDTO.getTmdbId();

          Map<ContentEntity, ArrayList<String>> map = movieUtil.getContentByEntity(type, path, "no");

          ContentEntity returnContentEntity = map.keySet().iterator().next();
          ArrayList<String> genre = map.get(returnContentEntity);

          contentEntity = contentDataHandler.saveContentEntity(returnContentEntity);

          for (int j = 0; j < genre.size(); j++)
            contentGenreDataHandler.saveContentGenreEntity(new ContentGenreEntity(contentEntity, genre.get(j)));
        }

        String watchedDate = writeReviewDTO.getWatchedDate();
        String watchedTime = writeReviewDTO.getWatchedTime();

        if (watchedTime.length() < 1) watchedTime = null;

        UserEntity userEntity = userDataHandler.getUserEntity(userId);

        if (reviewDataHandler.isReviewEntity(reviewId)) {
          reviewDataHandler.updateReviewEntity(reviewId, watchedDate, watchedTime, writeReviewDTO.getWatchedIn(), writeReviewDTO.getWatchedWith(), writeReviewDTO.getRating(), writeReviewDTO.getMemo(), writeReviewDTO.getComment());
        }
        else {
          reviewDataHandler.saveReviewEntity(new ReviewEntity(reviewId, userEntity, contentEntity, watchedDate, watchedTime, writeReviewDTO.getWatchedIn(), writeReviewDTO.getWatchedWith(), writeReviewDTO.getRating(), writeReviewDTO.getMemo(), writeReviewDTO.getComment()));
        }
        return true;
//      }
//      catch (Exception e){
//        return false;
//      }
  }
}
