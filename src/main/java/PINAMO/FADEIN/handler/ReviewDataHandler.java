package PINAMO.FADEIN.handler;

import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.ReviewEntity;

import java.util.List;

public interface ReviewDataHandler {

  ReviewEntity getReviewEntityByUserIdAndContentId(Long userId, Long contentId);

  ReviewEntity saveReviewEntity(ReviewEntity reviewEntity);

}
