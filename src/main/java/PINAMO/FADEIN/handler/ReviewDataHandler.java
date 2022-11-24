package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;

import java.util.List;

public interface ReviewDataHandler {

  boolean isReviewEntity(String reviewId);

  ReviewEntity getReviewEntity(String reviewId);
  ReviewEntity getReviewEntityByUserIdAndContentId(Long userId, Long contentId);

  ReviewEntity saveReviewEntity(ReviewEntity reviewEntity);

  int updateReviewEntity(String reviewId, String watched_date, String watched_time, String watched_in, String watched_with, float rating, String memo, String comment);
}
