package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.RecommendEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

public interface RecommendDAO {
  RecommendEntity saveRecommend(RecommendEntity recommendEntity);

  RecommendEntity getRecommend(Long movieId);
}