package PINAMO.FADEIN.service.impl;

import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.dto.movie.ReviewPageDTO;
import PINAMO.FADEIN.data.object.ReviewObject;
import PINAMO.FADEIN.handler.ContentDataHandler;
import PINAMO.FADEIN.handler.ContentGenreDataHandler;
import PINAMO.FADEIN.handler.ReviewDataHandler;
import PINAMO.FADEIN.handler.UserDataHandler;
import PINAMO.FADEIN.service.ReviewPageService;
import PINAMO.FADEIN.utils.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewPageServiceImpl implements ReviewPageService {

  MovieUtil movieUtil;
  UserDataHandler userDataHandler;
  ContentDataHandler contentDataHandler;
  ContentGenreDataHandler contentGenreDataHandler;
  ReviewDataHandler reviewDataHandler;

  @Autowired
  public ReviewPageServiceImpl(MovieUtil movieUtil, UserDataHandler userDataHandler, ReviewDataHandler reviewDataHandler, ContentDataHandler contentDataHandler, ContentGenreDataHandler contentGenreDataHandler) {
    this.movieUtil = movieUtil;
    this.userDataHandler = userDataHandler;
    this.reviewDataHandler = reviewDataHandler;
    this.contentDataHandler = contentDataHandler;
    this.contentGenreDataHandler = contentGenreDataHandler;
  }

  @Override
  public ReviewPageDTO getReviewPage(Long userId, int year, int month) {

    List<ReviewObject> returnReviews = new ArrayList<>();

    if (reviewDataHandler.isReviewEntityByUserId(userId)) {
      List<ReviewEntity> reviewEntities = reviewDataHandler.getReviewEntitiesByUserIdAndDate(userId, year, month);
      for (int i=0; i<reviewEntities.size(); i++) {
        ReviewEntity reviewEntity = reviewEntities.get(i);

        String reviewId = reviewEntity.getId();
        int tmdbId = reviewEntity.getContentEntity().getTmdbId();
        String title = reviewEntity.getContentEntity().getTitle();
        String originalTitle = reviewEntity.getContentEntity().getOriginalTitle();
        String type = reviewEntity.getContentEntity().getType();
        String poster = reviewEntity.getContentEntity().getPoster();
        int runtime = reviewEntity.getContentEntity().getRuntime();
        String watchedDate = reviewEntity.getWatchedDate();
        String watchedTime = reviewEntity.getWatchedTime();
        String watchedIn = reviewEntity.getWatchedIn();
        String watchedWith = reviewEntity.getWatchedWith();
        float rating = reviewEntity.getRating();
        String memo = reviewEntity.getMemo();
        String comment = reviewEntity.getComment();

        ReviewObject reviewObject = new ReviewObject(reviewId, tmdbId, title, originalTitle, type, poster, runtime, watchedDate, watchedTime, watchedIn, watchedWith, rating, memo, comment);

        returnReviews.add(reviewObject);
      }
    }
    return new ReviewPageDTO(returnReviews);
  }

  @Override
  public int deleteReview(String reviewId) {
    try {
      if (reviewDataHandler.isReviewEntity(reviewId)){
        reviewDataHandler.deleteReviewEntity(reviewId);
        return 1;
      }
      else {
        return 2;
      }
    } catch (Exception e) {
      return 0;
    }
  }
}
