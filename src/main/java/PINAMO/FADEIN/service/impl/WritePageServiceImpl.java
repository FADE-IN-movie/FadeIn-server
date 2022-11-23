package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.movie.WritePageDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.handler.*;
import PINAMO.FADEIN.service.WritePageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MovieUtil;
import utils.RestTemplateUtil;

import java.util.HashMap;
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
  public WriteContentObject getWritePage(String path) {
    try {
      String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
      JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

      int tmdbId = parser.getInt("id");
      String title;
      String originalTitle;
      String poster = movieUtil.posterTransducer(parser.get("poster_path"));
      String backdrop = movieUtil.posterTransducer(parser.get("backdrop_path"));
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
  public Map<String, Long> writeReview(Long userId, WritePageDTO writePageDTO) {
//    try {
      String type = writePageDTO.getType();

      Boolean isContent = contentDataHandler.isContentEntityByTmdbIdAndType(writePageDTO.getContentId(), type);
      ContentEntity contentEntity;
      if (isContent) {
        contentEntity = contentDataHandler.getContentEntityByTmdbIdAndType(writePageDTO.getContentId(), type);
      }
      else {
        String path = type + "/" + writePageDTO.getContentId();

        String requestURL = String.format("https://api.themoviedb.org/3/%s?api_key=929a001736172a3578c0d6bf3b3cbbc5&language=ko", path);
        JSONObject parser = restTemplateUtil.GetRestTemplate(requestURL);

        int tmdbId = parser.getInt("id");

        String title;
        if (type.equals("movie")) title = parser.getString("title");
        else title = parser.getString("name");

        String poster = movieUtil.posterTransducer(parser.get("poster_path"));
        String overview = parser.getString("overview");

        contentEntity = contentDataHandler.saveContentEntity(new ContentEntity(tmdbId, type, title, poster,overview));
      }
      UserEntity userEntity = userDataHandler.getUserEntity(userId);
      ReviewEntity reviewEntity = reviewDataHandler.saveReviewEntity(new ReviewEntity(userEntity, contentEntity, writePageDTO.getWatchedAt(), writePageDTO.getWatchedIn(), writePageDTO.getWatchedWith(), writePageDTO.getRating(), writePageDTO.getMemo(), writePageDTO.getComment()));
      Map<String, Long> map = new HashMap<>();
      map.put("reviewId", reviewEntity.getId());
      return map;
//    }
//    catch (Exception e) {
//      return null;
//    }

  }
}
