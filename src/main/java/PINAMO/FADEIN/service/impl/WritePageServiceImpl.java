package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ContentGenreEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;
import PINAMO.FADEIN.data.dto.movie.SearchPageDTO;
import PINAMO.FADEIN.data.dto.movie.WriteReviewDTO;
import PINAMO.FADEIN.data.object.WriteContentObject;
import PINAMO.FADEIN.data.object.WriteReviewObject;
import PINAMO.FADEIN.handler.*;
import PINAMO.FADEIN.service.WritePageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import PINAMO.FADEIN.utils.MovieUtil;

import java.util.ArrayList;
import java.util.Map;

@Service
public class WritePageServiceImpl implements WritePageService {

  MovieUtil movieUtil;
  UserDataHandler userDataHandler;
  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;
  ReviewDataHandler reviewDataHandler;

  @Autowired
  public WritePageServiceImpl(MovieUtil movieUtil, UserDataHandler userDataHandler, ReviewDataHandler reviewDataHandler, ContentDataHandler contentDataHandler, ContentGenreDataHandler contentGenreDataHandler) {
    this.movieUtil = movieUtil;
    this.userDataHandler = userDataHandler;
    this.reviewDataHandler = reviewDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
  }

  @Override
  public WriteContentObject getWriteContent(String path) {
    try {
      WriteContentObject writeContentObject = movieUtil.getWriteContent(path);

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
  public SearchPageDTO getWriteSearch(String keyword, int page) {

    String query = "&query=" + keyword;

    SearchPageDTO writeSearchDTO = new SearchPageDTO(movieUtil.getMovies("multi", "search", page, query));

    return writeSearchDTO;
  }

  @Override
  public int getWriteSearchLength(String keyword) {
    return movieUtil.getWriteSearchLength(keyword);
  }

  @Override
  public boolean writeReview(String reviewId, Long userId, WriteReviewDTO writeReviewDTO) {
      try {
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
      }
      catch (Exception e){
        return false;
      }
  }
}
