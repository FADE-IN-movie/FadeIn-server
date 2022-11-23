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
  public ReviewEntity getReviewEntityByUserIdAndContentId(Long userId, Long contentId) {
    return reviewDAO.getReviewByUserIdAndContentId(userId, contentId);
  }

  @Override
  public ReviewEntity saveReviewEntity(ReviewEntity reviewEntity) {
    return reviewDAO.saveReview(reviewEntity);
  }

}
