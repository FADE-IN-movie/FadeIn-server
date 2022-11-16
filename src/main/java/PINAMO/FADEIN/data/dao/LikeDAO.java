package PINAMO.FADEIN.data.dao;

import PINAMO.FADEIN.data.Entity.ContentEntity;
import PINAMO.FADEIN.data.Entity.LikeEntity;
import PINAMO.FADEIN.data.Entity.UserEntity;

public interface LikeDAO {
  LikeEntity saveLike(LikeEntity likeEntity);

  LikeEntity getLike(Long userId, int tmdbId);

  int deleteLike(LikeEntity likeEntity);

  Boolean isLikeByUserIdAndTmdbId(Long userId, int tmdbId);
}