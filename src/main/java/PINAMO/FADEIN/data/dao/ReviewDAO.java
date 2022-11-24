package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;

import java.util.List;

public interface ReviewDAO {

  boolean isReview(String reviewId);

  ReviewEntity getReview(String reviewId);
  ReviewEntity getReviewByUserIdAndContentId(Long userId, Long contentId);

  ReviewEntity saveReview(ReviewEntity reviewEntity);

  int updateReview(String reviewId, String watched_date, String watched_time, String watched_in, String watched_with, float rating, String memo, String comment);
}