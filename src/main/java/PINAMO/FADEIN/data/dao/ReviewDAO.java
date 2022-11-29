package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;

import java.util.List;

public interface ReviewDAO {

  boolean isReview(String reviewId);
  boolean isReviewByUserId(Long userId);

  ReviewEntity getReview(String reviewId);
  ReviewEntity getReviewByUserIdAndContentId(Long userId, Long contentId);

  List<ReviewEntity> getReviewsByUserId(Long userId);

  ReviewEntity saveReview(ReviewEntity reviewEntity);

  void deleteReview(String reviewId);

  int updateReview(String reviewId, String watched_date, String watched_time, String watched_in, String watched_with, float rating, String memo, String comment);
}