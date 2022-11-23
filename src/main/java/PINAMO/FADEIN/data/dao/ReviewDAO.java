package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;

import java.util.List;

public interface ReviewDAO {

  ReviewEntity getReviewByUserIdAndContentId(Long userId, Long contentId);

  ReviewEntity saveReview(ReviewEntity reviewEntity);

}