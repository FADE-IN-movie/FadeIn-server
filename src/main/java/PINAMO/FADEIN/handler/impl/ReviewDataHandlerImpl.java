package PINAMO.FADEIN.handler.impl;

import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;
import PINAMO.FADEIN.data.dao.LikeDAO;
import PINAMO.FADEIN.data.dao.ReviewDAO;
import PINAMO.FADEIN.handler.LikeDataHandler;
import PINAMO.FADEIN.handler.ReviewDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReviewDataHandlerImpl implements ReviewDataHandler {

  ReviewDAO reviewDAO;

  @Autowired
  public ReviewDataHandlerImpl(ReviewDAO reviewDAO) {
    this.reviewDAO = reviewDAO;
  }

  @Override
  public boolean isReviewEntity(String reviewId) {
    return reviewDAO.isReview(reviewId);
  }

  @Override
  public boolean isReviewEntityByUserId(Long userId) {
    return reviewDAO.isReviewByUserId(userId);
  }

  @Override
  public ReviewEntity getReviewEntity(String reviewId) {
    return reviewDAO.getReview(reviewId);
  }

  @Override
  public ReviewEntity getReviewEntityByUserIdAndContentId(Long userId, Long contentId) {
    return reviewDAO.getReviewByUserIdAndContentId(userId, contentId);
  }

  @Override
  public List<ReviewEntity> getReviewEntitiesByUserId(Long userId) {
    return reviewDAO.getReviewsByUserId(userId);
  }

  @Override
  public ReviewEntity saveReviewEntity(ReviewEntity reviewEntity) {
    return reviewDAO.saveReview(reviewEntity);
  }

  @Override
  public void deleteReviewEntity(String reviewId) {
    reviewDAO.deleteReview(reviewId);
  }

  @Override
  public int updateReviewEntity(String reviewId, String watched_date, String watched_time, String watched_in, String watched_with, float rating, String memo, String comment) {
    return reviewDAO.updateReview(reviewId, watched_date, watched_time, watched_in, watched_with, rating, memo, comment);
  }

}
